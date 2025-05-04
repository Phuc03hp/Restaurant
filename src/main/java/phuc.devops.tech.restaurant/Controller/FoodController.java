package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Service.FoodService;
import phuc.devops.tech.restaurant.dto.request.UserCreateFood;
import phuc.devops.tech.restaurant.dto.request.UserUpdateFood;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping
    public Food createFood(@RequestBody UserCreateFood request){
        return foodService.createFood(request);
    }

    @PutMapping("/{foodID}")
    public Food updateFood(@PathVariable String foodID , @RequestBody UserUpdateFood request ){
        return foodService.updateFood(foodID,request);
    }

    @GetMapping

    public List<Food> getFood() {

    }
    @GetMapping("/{name}")
    public Food getFoodByName(@PathVariable String name) {return foodService.getFoodByName(name);}

    @GetMapping("/id/{foodID}")
    public Food getFoodById(@PathVariable String foodID){
        return foodService.getFoodByID(foodID);
    }

    @DeleteMapping("/{foodID}")
    public String deleteFoodById(@PathVariable String foodID){
        foodService.deleteFood(foodID);
        return "This food has benn deleted";
    }
}
