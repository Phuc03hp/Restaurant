package phuc.devops.tech.restaurant.dto.request;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<FoodOrderItem> items = new ArrayList<>();

    public void addItem(FoodOrderItem item) {
        items.add(item);
    }

    public List<FoodOrderItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
