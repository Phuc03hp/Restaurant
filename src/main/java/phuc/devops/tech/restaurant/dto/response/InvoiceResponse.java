package phuc.devops.tech.restaurant.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;
import phuc.devops.tech.restaurant.Entity.Food;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {
    private Long tableID;
    private String user_name;
    private String customer_name;
    private String userID;
    private List<FoodResponse> foodAndQuantity;
    private List<Float> pricePerFood;
    private Float total;
}
