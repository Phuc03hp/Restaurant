package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Service.FoodService;
import phuc.devops.tech.restaurant.dto.UserCreateFood;
import phuc.devops.tech.restaurant.dto.UserUpdateFood;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping
    Food createFood(@RequestBody UserCreateFood request){
        return foodService.createFood(request);
    }

    @PutMapping
    Food updateFood(@PathVariable String foodID ,UserUpdateFood request ){
        return foodService.updateFood(foodID,request);
    }

    @GetMapping
    List<Food> getFood(){
        return foodService.getFood();
    }

    @GetMapping("/{foodID}")
    Food getFoodById(@PathVariable String foodID){
        return foodService.getFoodByID(foodID);
    }

    @DeleteMapping("/{foodID}")
    String deleteFoodById(@PathVariable String foodID){
        foodService.deleteFood(foodID);
        return "This food has benn deleted";
    }
}
