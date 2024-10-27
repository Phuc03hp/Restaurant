package phuc.devops.tech.restaurant.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private String orderID;
    private Long tableID;
    private Float total;
    private List<FoodResponse> foods;
}