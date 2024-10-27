package phuc.devops.tech.restaurant.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private String id;
    private String username;
    private String password;
    private String name;
    private Long numberPhone;
    private String email;
    private String address;
}
