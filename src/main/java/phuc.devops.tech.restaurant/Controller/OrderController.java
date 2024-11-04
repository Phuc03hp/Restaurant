package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

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

    @PostMapping("/{tableID}")
    public Order createOrder (@PathVariable Long tableID,@RequestBody UserCreateOrder request){
    return orderService.createOrder(tableID,request);
    //    return "Order has been created";
    }

    @GetMapping("/{tableId}/order")
    public ResponseEntity<OrderResponse> getOrderForTable(@PathVariable Long tableId) {
        // Lấy đơn hàng hiện tại cho bàn ăn
        Order order = orderService.getCurrentOrderForTable(tableId);

        if (order == null) {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không có đơn hàng
        }

        // Chuyển đổi sang DTO
        OrderResponse orderResponse = convertToDto(order);
        return ResponseEntity.ok(orderResponse);
    }

    // Phương thức chuyển đổi Order thành OrderResponse
    private OrderResponse convertToDto(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderID(order.getOrderID());
        orderResponse.setTableID(order.getDiningTable().getTableID());
        orderResponse.setTotal(order.getTotal());

        // Chuyển đổi danh sách món ăn thành DTO
        List<FoodResponse> foodResponses = order.getFoods().stream()
                .map(food -> {
                    FoodResponse foodResponse = new FoodResponse();
                    foodResponse.setFoodID(food.getFoodID());
                    foodResponse.setName(food.getName());
                    foodResponse.setPrice(food.getPrice());
                    foodResponse.setDescription(food.getDescription());
                    return foodResponse;
                })
                .collect(Collectors.toList());

        orderResponse.setFoods(foodResponses);

        return orderResponse;
    }

    @GetMapping("/{orderID}")
    public OrderResponse getOrder (@PathVariable String orderID){
        return orderService.getOrderById(orderID);
    }
}
