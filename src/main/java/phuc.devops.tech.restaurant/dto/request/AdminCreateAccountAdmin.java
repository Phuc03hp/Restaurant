package phuc.devops.tech.restaurant.dto.request;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCreateAccountAdmin {
    @Size(min = 5, message = "AdminName Invalid")
    String adminName;

    @Size(min=8, message = "Password Invalid")
    String password;

    String adminID;

    String name;


}
