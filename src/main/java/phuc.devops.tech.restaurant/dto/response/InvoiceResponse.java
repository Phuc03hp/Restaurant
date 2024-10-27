package phuc.devops.tech.restaurant.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Entity.Order;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {
    String invoiceID;
    Order order;
    String customerName;
    String userName;
    String userID;
}
