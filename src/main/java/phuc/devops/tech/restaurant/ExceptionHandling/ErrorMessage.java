package phuc.devops.tech.restaurant.ExceptionHandling;

import lombok.*;


@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private String message;
}

