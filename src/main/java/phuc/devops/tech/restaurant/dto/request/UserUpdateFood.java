package phuc.devops.tech.restaurant.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateFood {
    private String NameCategory;
    private String Name;
    private float Price;
    private String Description;
    private String FoodID;
}
