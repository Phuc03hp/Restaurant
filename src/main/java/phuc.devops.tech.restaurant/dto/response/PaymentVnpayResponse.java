package phuc.devops.tech.restaurant.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentVnpayResponse {
    private String code;
    private String message;
    private String data;
}
