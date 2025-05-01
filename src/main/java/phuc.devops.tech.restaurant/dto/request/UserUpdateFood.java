package phuc.devops.tech.restaurant.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateFood {
    private String nameCategory;
    private String name;
    private float price;
    private String description;
    private String foodID;
}
