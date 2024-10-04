package phuc.devops.tech.restaurant.dto.request;

import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.dto.response.FoodResponse;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<FoodResponse> items = new ArrayList<>();

    public void addItem(FoodResponse item) {
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setFoodId(item.getFoodId());
        foodResponse.setQuantity(item.getQuantity());
        items.add(foodResponse);
    }

    public List<FoodResponse> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
