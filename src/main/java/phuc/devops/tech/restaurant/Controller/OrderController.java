package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.DiningTable;
import phuc.devops.tech.restaurant.Service.DiningTableService;
import phuc.devops.tech.restaurant.dto.request.UserCreateTable;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private DiningTableService diningTableService;

    @PostMapping("/createTable")
    public DiningTable createTable(@RequestBody UserCreateTable request){
        return diningTableService.createTable(request);
    }
    @GetMapping
    public List<DiningTable> getAvailableTables() {
        return diningTableService.getTable();
    }


}
