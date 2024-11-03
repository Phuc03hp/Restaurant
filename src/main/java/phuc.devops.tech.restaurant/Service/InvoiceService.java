package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.Customer;
import phuc.devops.tech.restaurant.Entity.Invoice;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Repository.*;
import phuc.devops.tech.restaurant.dto.request.CheckoutRequest;
import phuc.devops.tech.restaurant.dto.request.UserCreateInvoice;
import phuc.devops.tech.restaurant.dto.response.FoodResponse;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;

import java.util.List;
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
        Optional<Customer> customer = customerRepository.findById(request.getCustomerID());
        invoice.setCustomer(customer.get());
        Optional<User> user = userRepository.findById(request.getUserID());
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
}
