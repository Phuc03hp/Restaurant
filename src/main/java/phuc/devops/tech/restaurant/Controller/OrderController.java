package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Service.DiningTableService;
import phuc.devops.tech.restaurant.Service.OrderService;
import phuc.devops.tech.restaurant.dto.request.CheckoutRequest;
import phuc.devops.tech.restaurant.dto.request.UserCreateOrder;
import phuc.devops.tech.restaurant.dto.request.UserCreateTable;
import phuc.devops.tech.restaurant.dto.response.FoodResponse;
import phuc.devops.tech.restaurant.dto.response.InvoiceResponse;
import phuc.devops.tech.restaurant.dto.response.OrderResponse;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private DiningTableService diningTableService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/createTable")
    public DiningTable createTable(@RequestBody UserCreateTable request){
        return diningTableService.createTable(request);
    }

    @GetMapping("/tables")
    public List<DiningTable> getTables() {
        return diningTableService.getTable();
    }

    @PostMapping
    public Order createOrder (@RequestBody UserCreateOrder request){
    return orderService.createOrder(request);
    //    return "Order has been created";
    }

    @GetMapping("/{orderID}")
    public OrderResponse getOrder (@PathVariable String orderID){
        return orderService.getOrderById(orderID);
    }
}
