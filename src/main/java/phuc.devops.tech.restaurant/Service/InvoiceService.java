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
import phuc.devops.tech.restaurant.dto.request.ReviewStatus;
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

    public InvoiceResponse getInvoice(String orderID,UserCreateInvoice request){
        Order order = orderRepository.findById(orderID).orElseThrow(()-> new RuntimeException("OrderID cannot be found"));
        order.setIsPaid(true);
        Invoice invoice = new Invoice();
        invoice.setCreatedAt(LocalDateTime.now());
        
        Optional<Customer> customer = customerRepository.findByPhoneNumber(request.getPhoneNumber());
        invoice.setCustomer(customer.get());
        Optional<User> user = userRepository.findById(request.getUserID());
        if (request.getRating() != null && request.getComment() != null) {
            invoice.setRating(request.getRating());
            invoice.setComment(request.getComment());
            invoice.setReviewStatus(ReviewStatus.APPROVED);
        }

        invoice.setUser(user.get());
        invoice.setOrder(order);
        invoiceRepository.save(invoice);
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setInvoiceID(invoice.getInvoiceID());
        invoiceResponse.setOrder(order);
        invoiceResponse.setUserID(request.getUserID());
        invoiceResponse.setUserName(user.get().getName());
        invoiceResponse.setCustomerName(customer.get().getName());
        invoiceRepository.save(invoice);

        return invoiceResponse;
    }

    public InvoiceResponse CheckInvoiceAgain(String invoiceID){
        Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow(()->new RuntimeException("Cannot find invoice"));
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

    public void approveFeedback(String invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getRating() != null && invoice.getComment() != null) {
            invoice.setReviewStatus(ReviewStatus.APPROVED);
            invoiceRepository.save(invoice);
        } else {
            throw new RuntimeException("No feedback available to approve");
        }
    }

    public void rejectFeedback(String invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getRating() != null && invoice.getComment() != null) {
            invoice.setReviewStatus(ReviewStatus.REJECTED);
            invoiceRepository.save(invoice);
        } else {
            throw new RuntimeException("No feedback available to reject");
        }
    }






}
