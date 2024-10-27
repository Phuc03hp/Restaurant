package phuc.devops.tech.restaurant.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.Category;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Repository.CategoryRepository;
import phuc.devops.tech.restaurant.Repository.FoodRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;

    public List<Food> getFoodOfCategory(String categoryName){
        return categoryRepository.findByName(categoryName).getFoods();
    }

    public String updateCategoryName(String name){
        Category category = categoryRepository.findByName(name);
        category.setName(name);
        return "Name has been changed";
    }
}
