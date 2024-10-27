package phuc.devops.tech.restaurant.dto.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import phuc.devops.tech.restaurant.Entity.Invoice;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateCustomer {
    String name;
    String phoneNumber;
    String email;
    String address;
}
