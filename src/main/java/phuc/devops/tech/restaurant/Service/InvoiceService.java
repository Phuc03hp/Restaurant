package phuc.devops.tech.restaurant.Service;

import io.netty.util.internal.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.*;
import phuc.devops.tech.restaurant.Repository.*;
import phuc.devops.tech.restaurant.dto.request.Cart;
import phuc.devops.tech.restaurant.dto.request.CheckoutRequest;
import phuc.devops.tech.restaurant.dto.response.FoodResponse;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    private Cart cart = new Cart();

    public void addToCart(FoodResponse request){
        cart.addItem(request);
    }

    public List<FoodResponse> getToCart (){
        return cart.getItems();
    }

    public InvoiceResponse createInvoice(CheckoutRequest request) {
        Invoice invoice = new Invoice();

        Customer customer = this.customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DiningTable diningTable = this.diningTableRepository.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        List<FoodResponse> listFoodResponse = new ArrayList<>();

        List<Float> pricePerFood = new ArrayList<>();

        InvoiceResponse invoiceResponse = new InvoiceResponse();

        float total = 0.0f;

        for (FoodResponse item : cart.getItems()) {
            Food food = foodRepository.findById(item.getFoodId()).orElseThrow(()-> new RuntimeException("Food not existed"));
            listFoodResponse.add(item);
            float test = 0.0f;
            test = food.getPrice()* item.getQuantity();
            pricePerFood.add(test);
            total+=test;
        }
        invoiceResponse.setFoodAndQuantity(listFoodResponse);
        invoiceResponse.setPricePerFood(pricePerFood);
        invoiceResponse.setTotal(total);
        invoiceResponse.setTableID(request.getTableId());
        invoiceResponse.setCustomer_name(customer.getName());
        invoiceResponse.setUser_name(user.getName());
        invoiceResponse.setUserID(request.getUserId());

        cart.clear();

        return invoiceResponse;
    }


    public String deleteInvoice(String invoiceId){
        invoiceRepository.deleteById(invoiceId);
        return "Invoice has been deleted";
    }

//    public InvoiceResponse getInvoice(String invoiceId){
//        Invoice invoice= invoiceRepository.findById(invoiceId)
//                .orElseThrow(()-> new RuntimeException("Invoice unavailable"));
//
//        InvoiceResponse invoiceResponse = new InvoiceResponse();
//        invoiceResponse.setTableID(invoice.getDiningTable().getTableID());
//        invoiceResponse.setCustomer_name(invoice.getCustomer().getName());
//        invoiceResponse.setUser_name(invoice.getUser().getName());
//        invoiceResponse.setUserID(invoice.getUser().getUserID());
//        List<Food> foods = invoice.getFoods();
//        List<Long> quantities = invoice.getQuantity();
//        for(int i=0; i< foods.size();i++ ){
//            Map<Food, Long> test1 = new HashMap<>();
//            test1.put(foods.get(i),quantities.get(i));
//            invoiceResponse.getTest().add(test1);
//        }
//        return invoiceResponse;
//    }
}
