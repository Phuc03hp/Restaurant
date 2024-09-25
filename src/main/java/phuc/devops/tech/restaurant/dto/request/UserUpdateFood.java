package phuc.devops.tech.restaurant.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateFood {
    private String Category;
    private String FoodName;
    private float Price;
    private String Description;
    private String FoodID;
}
