package phuc.devops.tech.restaurant.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ReviewStatus {
        PENDING,
        APPROVED,
        REJECTED;
}

