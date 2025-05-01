package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.Customer;
import phuc.devops.tech.restaurant.Entity.Invoice;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Repository.CustomerRepository;
import phuc.devops.tech.restaurant.Repository.InvoiceRepository;
import phuc.devops.tech.restaurant.Repository.OrderRepository;
import phuc.devops.tech.restaurant.Repository.UserRepository;
import phuc.devops.tech.restaurant.dto.request.UserCreateInvoice;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public InvoiceResponse getInvoice(String orderID, UserCreateInvoice request) {
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("OrderID cannot be found"));
        order.setIsPaid(true);

        // Lấy thông tin khách hàng theo số điện thoại
        Optional<Customer> customerOpt = customerRepository.findByPhoneNumber(request.getPhoneNumber());
        if (customerOpt.isEmpty()) {
            throw new RuntimeException("Customer not found with phone number: " + request.getPhoneNumber());
        }
        Customer customer = customerOpt.get();

        // Tính giảm giá theo tổng chi tiêu
        float totalSpent = customer.getTotalSpent() == null ? 0f : customer.getTotalSpent();
        float discount = 0f;

        if (totalSpent >= 15000000) {
            discount = 0.20f;
        } else if (totalSpent >= 7000000) {
            discount = 0.15f;
        } else if (totalSpent >= 3000000) {
            discount = 0.10f;
        } else if (totalSpent >= 500000) {
            discount = 0.05f;
        }

        float originalTotal = order.getTotal();
        float discountedTotal = originalTotal * (1 - discount);
        order.setTotal(discountedTotal);

        // Cập nhật tổng chi tiêu
        customer.setTotalSpent(totalSpent + discountedTotal);
        customerRepository.save(customer);

        // Tạo hóa đơn
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setCustomer(customer);

        Optional<User> user = userRepository.findById(request.getUserID());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + request.getUserID());
        }

        invoice.setUser(user.get());
        invoice.setOrder(order);

        if (request.getRating() != null && request.getComment() != null) {
            invoice.setRating(request.getRating());
            invoice.setComment(request.getComment());
        }

        invoiceRepository.save(invoice);

        // Trả về kết quả
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setInvoiceID(invoice.getInvoiceID());
        invoiceResponse.setOrder(order);
        invoiceResponse.setUserID(request.getUserID());
        invoiceResponse.setUserName(user.get().getName());
        invoiceResponse.setCustomerName(customer.getName());

        return invoiceResponse;
    }

    public InvoiceResponse CheckInvoiceAgain(String invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID)
                .orElseThrow(() -> new RuntimeException("Cannot find invoice"));

        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setInvoiceID(invoiceID);
        invoiceResponse.setOrder(invoice.getOrder());
        invoiceResponse.setUserID(invoice.getUser().getUserID());
        invoiceResponse.setUserName(invoice.getUser().getName());
        invoiceResponse.setCustomerName(invoice.getCustomer().getName());

        return invoiceResponse;
    }

    public Float getRevenueByDate(LocalDate date) {
        return Optional.ofNullable(invoiceRepository.getRevenueByDate(date)).orElse(0f);
    }

    public Float getRevenueByMonth(int month, int year) {
        return Optional.ofNullable(invoiceRepository.getRevenueByMonth(month, year)).orElse(0f);
    }

    public Float getRevenueByYear(int year) {
        return Optional.ofNullable(invoiceRepository.getRevenueByYear(year)).orElse(0f);
    }
}
