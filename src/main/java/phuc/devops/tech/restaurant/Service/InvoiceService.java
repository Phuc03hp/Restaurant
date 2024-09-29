package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.*;
import phuc.devops.tech.restaurant.Repository.*;
import phuc.devops.tech.restaurant.dto.request.Cart;
import phuc.devops.tech.restaurant.dto.request.CheckoutRequest;
import phuc.devops.tech.restaurant.dto.request.FoodOrderItem;
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

    private static Cart cart = new Cart();

    public void addToCart(FoodOrderItem request){
        cart.addItem(request);
    }

    public InvoiceResponse createInvoice(CheckoutRequest request) {
        Invoice invoice = new Invoice();

        Customer customer = this.customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DiningTable diningTable = this.diningTableRepository.findById(request.getTableId())
                .orElseThrow(() -> new RuntimeException("Table not found"));

        invoice.setCustomer(customer);
        invoice.setDiningTable(diningTable);
        invoice.setUser(user);

        float totalAmount = 0f;
        List<Food> listFood = new ArrayList<>();
        List<Long> listQuantity = new ArrayList<>();

        for (FoodOrderItem item : cart.getItems()) {
            Food food = item.getFood();
            food.setInvoice(invoice);  // Liên kết món ăn với hóa đơn hiện tại
            listFood.add(food);
            listQuantity.add(item.getQuantity());
            totalAmount += food.getPrice() * item.getQuantity();
        }

        invoice.setFoods(listFood);
        invoice.setTotal(totalAmount);
        invoice.setQuantity(listQuantity);

        // Lưu hóa đơn và cập nhật response
        invoiceRepository.saveAndFlush(invoice);

        // Chuẩn bị phản hồi
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setTableID(request.getTableId());
        invoiceResponse.setCustomer_name(customer.getName());
        invoiceResponse.setUser_name(user.getName());
        invoiceResponse.setUserID(request.getUserId());

        List<Map<Food, Long>> foodDetails = new ArrayList<>();
        for (int i = 0; i < listFood.size(); i++) {
            Map<Food, Long> foodDetail = new HashMap<>();
            foodDetail.put(listFood.get(i), listQuantity.get(i));
            foodDetails.add(foodDetail);
        }

        invoiceResponse.setTest(foodDetails);
        invoiceResponse.setTotal(totalAmount);

        cart.clear();  // Xóa giỏ hàng sau khi tạo hóa đơn

        return invoiceResponse;
    }


    public String deleteInvoice(String invoiceId){
        invoiceRepository.deleteById(invoiceId);
        return "Invoice has been deleted";
    }

    public InvoiceResponse getInvoice(String invoiceId){
        Invoice invoice= invoiceRepository.findById(invoiceId)
                .orElseThrow(()-> new RuntimeException("Invoice unavailable"));

        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setTableID(invoice.getDiningTable().getTableID());
        invoiceResponse.setCustomer_name(invoice.getCustomer().getName());
        invoiceResponse.setUser_name(invoice.getUser().getName());
        invoiceResponse.setUserID(invoice.getUser().getUserID());
        List<Food> foods = invoice.getFoods();
        List<Long> quantities = invoice.getQuantity();
        for(int i=0; i< foods.size();i++ ){
            Map<Food, Long> test1 = new HashMap<>();
            test1.put(foods.get(i),quantities.get(i));
            invoiceResponse.getTest().add(test1);
        }
        return invoiceResponse;
    }
}
