package phuc.devops.tech.restaurant.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRefund {
    private String tranType;
    private String orderId;
    private long amount;
    private String transDate;
    private String user;
    private String transType;
}
