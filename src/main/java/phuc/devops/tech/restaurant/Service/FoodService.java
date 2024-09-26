package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Repository.FoodRepository;
import phuc.devops.tech.restaurant.dto.request.UserCreateFood;
import phuc.devops.tech.restaurant.dto.request.UserUpdateFood;

import java.util.List;
import java.util.Set;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public Food createFood(UserCreateFood request){
        Food food = new Food();
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        food.setDescription(request.getDescription());
        food.setFoodID(request.getFoodID());
        return foodRepository.save(food);
    }

    public Food updateFood(String foodId,UserUpdateFood request){
        Food food = foodRepository.getById(foodId);
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        food.setDescription(request.getDescription());
        return foodRepository.save(food);
    }

    public List<Food> getFood(){
        return foodRepository.findAll();
    }

    public Food getFoodByID(String foodId){
        return foodRepository.getById(foodId);
    }

    public List<Food> getFoodByName (String foodName) {
        return foodRepository.findByName(foodName);
    }

    public void deleteFood(String foodId){
        foodRepository.deleteById(foodId);
    }
}
