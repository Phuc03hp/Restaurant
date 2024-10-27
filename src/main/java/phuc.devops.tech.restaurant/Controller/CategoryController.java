package phuc.devops.tech.restaurant.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Category;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/{categoryName}")
    public List<Food> getFoodOfCategory(@PathVariable String categoryName){
        return categoryService.getFoodOfCategory(categoryName);
    }

    @PutMapping("/{categoryName}")
    public String updateCategoryName(@PathVariable String categoryName){
        return categoryService.updateCategoryName(categoryName);
    }
}
