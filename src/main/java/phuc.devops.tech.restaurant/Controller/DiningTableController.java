package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Service.DiningTableService;
import phuc.devops.tech.restaurant.dto.request.UserCreateTable;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class DiningTableController {

    @Autowired
    private DiningTableService diningTableService;

    @PostMapping("/createTable")
    public DiningTable createTable(@RequestBody UserCreateTable request){
        return diningTableService.createTable(request);
    }

    @GetMapping
    public List<DiningTable> getTables() {
        return diningTableService.getTable();
    }

    @PutMapping("/updateTable/{tableID}")
    public String updateTable(@PathVariable Long id, @RequestBody UserCreateTable request) {
        diningTableService.updateTable(id,request);
        return "This table has been updated";
    }

    @DeleteMapping("/deleteTable/{tableID}")
    public String deleteTable(@PathVariable Long id) {
        diningTableService.deleteTable(id);
        return "This table has been deleted";
    }

}
