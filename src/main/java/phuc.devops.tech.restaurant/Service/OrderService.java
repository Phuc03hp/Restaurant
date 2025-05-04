package phuc.devops.tech.restaurant.Service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Entity.Order;
import phuc.devops.tech.restaurant.Repository.DiningTableRepository;
import phuc.devops.tech.restaurant.Repository.FoodRepository;
import phuc.devops.tech.restaurant.Repository.OrderRepository;
import phuc.devops.tech.restaurant.dto.request.TableStatus;
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
    public Order getOrCreateOrderForTable(Long tableID) {
        // Tìm bàn ăn (DiningTable) dựa trên tableID
        Optional<DiningTable> tableOptional = diningTableRepository.findById(tableID);
        if (tableOptional.isEmpty()) {
            throw new RuntimeException("Dining Table not found with ID: " + tableID);
        }

        DiningTable diningTable = tableOptional.get();

        // Lấy đơn hàng chưa thanh toán (isPaid = false) cho bàn này
        List<Order> orders = diningTable.getOrders().stream()
                .filter(order -> !order.getIsPaid()) // Chỉ lấy đơn hàng chưa thanh toán
                .toList();

        if (!orders.isEmpty()) {
            return orders.get(0); // Trả về đơn hàng đầu tiên chưa thanh toán
        }
        // Nếu không có đơn hàng chưa thanh toán, tạo mới một đơn hàng
        Order newOrder = new Order();
        newOrder.setDiningTable(diningTable);
        newOrder.setIsPaid(false); // Đánh dấu là chưa thanh toán
        newOrder.getDiningTable().setTableStatus(TableStatus.UNAVAILABLE);
        return orderRepository.save(newOrder);
    }

    @Transactional
    public Order addItemsToOrder(Long tableID, UserCreateOrder userCreateOrder) {
        // Lấy hoặc tạo đơn hàng chưa thanh toán cho bàn
        Order order = getOrCreateOrderForTable(tableID);
        Hibernate.initialize(order.getFoods());

        // Khởi tạo danh sách nếu chưa có
        List<Food> foodsInOrder = new ArrayList<>();
//        if (foodsInOrder == null) {
//            foodsInOrder = new ArrayList<>();
//        }

        List<Long> quantitiesInOrder = order.getQuantity();
        if (quantitiesInOrder == null) {
            quantitiesInOrder = new ArrayList<>();
        }

        // Tính tổng tiền ban đầu
        float total = 0.0f;

        // Tìm kiếm từng món ăn theo tên và thêm vào danh sách
        List<Long> newQuantities = userCreateOrder.getQuantities();
        List<String> foodNames = userCreateOrder.getFoodNames();

        for (int i = 0; i < foodNames.size(); i++) {
            String foodName = foodNames.get(i);
            Food food = foodRepository.findByName(foodName);
            foodsInOrder.add(food);
            total += newQuantities.get(i) * food.getPrice();

        }

        // Cập nhật danh sách món ăn và tổng tiền vào đơn hàng
        order.setFoods(foodsInOrder);
        order.setQuantity(newQuantities);
        order.setTotal(total);

        // Lưu đơn hàng cập nhật vào CSDL
        return orderRepository.save(order);
    }



    @Transactional
    public void payOrder(Long tableID) {
        Order order = getCurrentOrderForTable(tableID);
        order.setIsPaid(true);
        orderRepository.save(order);
    }

    public Order getCurrentOrderForTable(Long tableId) {
        return getOrCreateOrderForTable(tableId);
    }

    @Transactional
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
