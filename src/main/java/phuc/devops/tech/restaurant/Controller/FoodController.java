package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Service.FoodService;
import phuc.devops.tech.restaurant.dto.request.UserCreateFood;
import phuc.devops.tech.restaurant.dto.request.UserUpdateFood;

import java.util.List;
import java.util.Set;

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
    Food updateFood(@PathVariable String foodID , @RequestBody UserUpdateFood request ){
        return foodService.updateFood(foodID,request);
    }

    @GetMapping
    List<Food> getFood(){
        return foodService.getFood();
    }

    @GetMapping("/{name}")
    Food getFoodByName(@PathVariable String name) {return foodService.getFoodByName(name);}

    @DeleteMapping("/{foodID}")
    String deleteFoodById(@PathVariable String foodID){
        foodService.deleteFood(foodID);
        return "This food has benn deleted";
    }
}
