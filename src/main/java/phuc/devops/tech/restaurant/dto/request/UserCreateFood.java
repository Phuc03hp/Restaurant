package phuc.devops.tech.restaurant.dto.request;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateFood {
    private String Category;
    private String FoodName;
    private float Price;
    private String Description;
    private String FoodID;
}
