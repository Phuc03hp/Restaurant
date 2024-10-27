package phuc.devops.tech.restaurant.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateCustomer {
    String name;
    String phoneNumber;
    String email;
    String address;
}
