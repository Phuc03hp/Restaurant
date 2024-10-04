package phuc.devops.tech.restaurant.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import phuc.devops.tech.restaurant.Repository.FoodRepository;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodResponse {
    String foodId;
    Long quantity;
}
