package phuc.devops.tech.restaurant.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentCreate {
    private long amount;
    private String invoiceId;
    private String bankCode;
    private String language;

}
