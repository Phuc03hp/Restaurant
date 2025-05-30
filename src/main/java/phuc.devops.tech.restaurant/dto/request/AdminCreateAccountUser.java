package phuc.devops.tech.restaurant.dto.request;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCreateAccountUser {
    @Size(min = 5, message = "Username Invalid")
    String username;

    @Size(min=8, message = "Password Invalid")
    String password;

    String userID;

    String name;


}
