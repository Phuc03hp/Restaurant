package phuc.devops.tech.restaurant.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import phuc.devops.tech.restaurant.Entity.Food;

@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodOrderItem {
    private Food food;
    private Long quantity;

    public FoodOrderItem(Food food, Long quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public Long getQuantity() {
        return quantity;
    }

}
