package phuc.devops.tech.restaurant.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import phuc.devops.tech.restaurant.Repository.FoodRepository;

@Data

public class FoodResponse {
    private String foodID;
    private String name;
    private float price;
    private String description;
}
