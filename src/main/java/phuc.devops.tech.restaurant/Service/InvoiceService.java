package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private DiningTableService diningTableService;

    public Float getInvoiceTotal(String invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow();
        return invoice.getOrder().getTotal();
    }

    @Transactional
    public InvoiceResponse getInvoice(String orderID, UserCreateInvoice request) {
        // 1. Lấy Order và đánh dấu đã thanh toán
        Order order = orderRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("OrderID cannot be found"));
        order.setIsPaid(true);

        // 2. Tính giảm giá dựa trên lịch sử khách hàng
        Customer customer = customerRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found with phone number: " + request.getPhoneNumber()));
        float totalSpent = Optional.ofNullable(customer.getTotalSpent()).orElse(0f);
        float discount = calculateDiscount(totalSpent);
        float originalTotal = order.getTotal();
        float discountedTotal = originalTotal * (1 - discount);
        order.setTotal(discountedTotal);

        // 3. Cập nhật tổng chi tiêu của khách
        customer.setTotalSpent(totalSpent + discountedTotal);
        customerRepository.save(customer);

        // 4. Lưu Order để cập nhật trạng thái và tổng giá
        orderRepository.save(order);

        // 5. Tạo và lưu Invoice
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setCustomer(customer);

        User user = userRepository.findById(request.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserID()));
        invoice.setUser(user);
        invoice.setOrder(order);

        if (request.getRating() != null && request.getComment() != null) {
            invoice.setRating(request.getRating());
            invoice.setComment(request.getComment());
        }
        invoiceRepository.save(invoice);


        // 7. Chuẩn bị và trả về response
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setInvoiceID(invoice.getInvoiceID());
        invoiceResponse.setOrder(order);
        invoiceResponse.setUserID(request.getUserID());
        invoiceResponse.setUserName(user.getName());
        invoiceResponse.setCustomerName(customer.getName());
        return invoiceResponse;
    }

    private float calculateDiscount(float totalSpent) {
        if (totalSpent >= 15_000_000) return 0.20f;
        if (totalSpent >=  7_000_000) return 0.15f;
        if (totalSpent >=  3_000_000) return 0.10f;
        if (totalSpent >=    500_000) return 0.05f;
        return 0f;
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
