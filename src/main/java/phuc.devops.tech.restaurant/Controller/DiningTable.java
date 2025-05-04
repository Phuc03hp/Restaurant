package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Food;
import phuc.devops.tech.restaurant.Service.DiningTableService;
import phuc.devops.tech.restaurant.Service.FoodService;
import phuc.devops.tech.restaurant.dto.request.UserCreateFood;
import phuc.devops.tech.restaurant.dto.request.UserCreateTable;
import phuc.devops.tech.restaurant.dto.request.UserUpdateFood;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class DiningTable {

    @Autowired
    private DiningTableService diningTableService;

    @PostMapping("/createTable")
    public phuc.devops.tech.restaurant.Entity.DiningTable createTable(@RequestBody UserCreateTable request){
        return diningTableService.createTable(request);
    }

    @GetMapping("/tables")
    public List<phuc.devops.tech.restaurant.Entity.DiningTable> getTables() {
        return diningTableService.getTable();
    }

    @PutMapping("/updateTable/{id}")
    public String updateTable(@PathVariable Long id, @RequestBody UserCreateTable request) {
        diningTableService.updateTable(id,request);
        return "This table has been updated";
    }

    @DeleteMapping("/deleteTable/{id}")
    public String deleteTable(@PathVariable Long id) {
        diningTableService.deleteTable(id);
        return "This table has been deleted";
    }

}
