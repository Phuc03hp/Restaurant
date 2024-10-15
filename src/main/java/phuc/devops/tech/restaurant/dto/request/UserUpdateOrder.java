package phuc.devops.tech.restaurant.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateOrder {
    private String tableID;            // ID của bàn ăn, có thể thay đổi bàn ăn
    private List<String> foodIDs;      // Danh sách ID các món ăn
    private List<Long> quantities;     // Số lượng tương ứng cho từng món ăn
    private String condition;          // Trạng thái đơn hàng
    private Float total;               // Tổng tiền đơn hàng
}
