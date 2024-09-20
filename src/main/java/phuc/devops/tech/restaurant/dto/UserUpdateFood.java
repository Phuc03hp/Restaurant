package phuc.devops.tech.restaurant.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateFood {
    private String type;
    private String name;
    private float price;
}
