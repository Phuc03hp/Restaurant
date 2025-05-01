package phuc.devops.tech.restaurant.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.Customer;
import phuc.devops.tech.restaurant.Repository.CustomerRepository;
import phuc.devops.tech.restaurant.dto.request.UserCreateCustomer;
import phuc.devops.tech.restaurant.dto.request.UserUpdateCustomer;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(UserCreateCustomer request){
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String customerID,UserUpdateCustomer request){
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(()->new RuntimeException("Customer not found"));;
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());

        return customerRepository.save(customer);
    }

    public String deleteCustomer(String customerID){
        customerRepository.deleteById(customerID);
        return "Customer hase been deleted";
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
}
