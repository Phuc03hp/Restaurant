package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Repository.DiningTableRepository;
import phuc.devops.tech.restaurant.Repository.FoodRepository;
import phuc.devops.tech.restaurant.Repository.OrderRepository;
import phuc.devops.tech.restaurant.dto.request.UserCreateOrder;
import phuc.devops.tech.restaurant.dto.response.FoodResponse;
import phuc.devops.tech.restaurant.dto.response.OrderResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DiningTableRepository diningTableRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Transactional
    public Order createOrder(UserCreateOrder userCreateOrder) {
        // Tìm bàn ăn (DiningTable) dựa trên tableID
        Optional<DiningTable> tableOptional = diningTableRepository.findById(userCreateOrder.getTableID());

        if (!tableOptional.isPresent()) {
            throw new RuntimeException("Dining Table not found with ID: " + userCreateOrder.getTableID());
        }

        DiningTable diningTable = tableOptional.get();

        // Danh sách để lưu trữ món ăn
        List<Food> foods = new ArrayList<>();

        // Tìm kiếm từng món ăn theo tên và thêm vào danh sách
        for (String foodName : userCreateOrder.getFoodNames()) {
            Food food = foodRepository.findByName(foodName);
            foods.add(food);
        }

        // Tính toán tổng tiền
        float total = 0.0f;
        List<Long> quantities = userCreateOrder.getQuantities();

        for (int i = 0; i < quantities.size(); i++) {
            total += quantities.get(i) * foods.get(i).getPrice();
        }

        // Tạo mới đối tượng Order
        Order order = new Order();
        order.setDiningTable(diningTable);
        order.setTotal(total);
        order.setQuantity(quantities);

        // Thiết lập mối quan hệ hai chiều giữa Order và Food
        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            food.getOrders().add(order);
            order.getFoods().add(food);
        }

        // Lưu Order vào CSDL
        return orderRepository.save(order);
    }


    public OrderResponse getOrderById(String orderID) {
        // Tìm kiếm đơn hàng theo orderID
        Optional<Order> orderOptional = orderRepository.findById(orderID);

        if (!orderOptional.isPresent()) {
            throw new RuntimeException("Order not found with ID: " + orderID);
        }

        Order order = orderOptional.get();

        // Chuyển đổi đơn hàng thành DTO
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

}
