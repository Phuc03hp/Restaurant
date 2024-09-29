package phuc.devops.tech.restaurant.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Customer;
import phuc.devops.tech.restaurant.Service.CustomerService;
import phuc.devops.tech.restaurant.dto.request.UserCreateCustomer;
import phuc.devops.tech.restaurant.dto.request.UserUpdateCustomer;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody UserCreateCustomer request){
        return customerService.createCustomer(request);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @DeleteMapping("/{customerID}")
    public String deleteCustomer(@PathVariable String customerId){
        return customerService.deleteCustomer(customerId);
    }

    @PutMapping("/{customerID}")
    public Customer updateCustomer(@PathVariable String customerId, @RequestBody UserUpdateCustomer request){
        return customerService.updateCustomer(customerId,request);
    }


}