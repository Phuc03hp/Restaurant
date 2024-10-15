package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;
import phuc.devops.tech.restaurant.Entity.Category;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.ExceptionHandling.AppException;
import phuc.devops.tech.restaurant.ExceptionHandling.ErrorCode;
import phuc.devops.tech.restaurant.Repository.CategoryRepository;
import phuc.devops.tech.restaurant.Repository.FoodRepository;
import phuc.devops.tech.restaurant.dto.request.UserCreateFood;
import phuc.devops.tech.restaurant.dto.request.UserUpdateFood;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Food createFood(UserCreateFood userCreateFood) {
        // Kiểm tra xem danh mục đã tồn tại hay chưa
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByName(userCreateFood.getNameCategory()));
        Category category;

        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();
        } else {
            // Nếu không tồn tại, tạo mới Category
            category = new Category();
            category.setName(userCreateFood.getNameCategory());
            category = categoryRepository.save(category);
        }

        // Tạo mới đối tượng Food
        Food food = new Food();
        food.setName(userCreateFood.getName());
        food.setPrice(userCreateFood.getPrice());
        food.setDescription(userCreateFood.getDescription());
        food.setCategory(category);

        // Lưu Food vào CSDL
        return foodRepository.save(food);
    }

    public Food updateFood(String foodId,UserUpdateFood request){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByName(request.getNameCategory()));
        Category category;

        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();
        } else {
            // Nếu không tồn tại, tạo mới Category
            category = new Category();
            category.setName(request.getNameCategory());
            category = categoryRepository.save(category);
        }

        Food food = foodRepository.getById(foodId);
        food.setName(request.getName());
        food.setPrice(request.getPrice());
        food.setDescription(request.getDescription());
        food.setCategory(category);

        return foodRepository.save(food);
    }

    public List<Food> getFood(){
        return foodRepository.findAll();
    }

    public Food getFoodByID(String foodId){
        return foodRepository.getById(foodId);
    }

    public Food getFoodByName (String foodName) {
        return foodRepository.findByName(foodName);
    }

    public void deleteFood(String foodId){
        foodRepository.deleteById(foodId);
    }
}
