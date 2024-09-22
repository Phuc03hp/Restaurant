package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Service.UserService;
import phuc.devops.tech.restaurant.dto.request.UserCreateAccount;
import phuc.devops.tech.restaurant.dto.request.UserUpdateAccount;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    User createUser(@RequestBody UserCreateAccount request){
        return userService.createUser(request);
    }
    @GetMapping("/signup")
    List<User> getUsers(){
        return userService.getUsers();
    }
    @PutMapping("/signup")
    User updateUser(@PathVariable String userID, UserUpdateAccount request){
        return userService.updateUser(userID,request);
    }
    @GetMapping("/signup/{userID}")
    User getUser(@PathVariable String userID){
        return userService.getUser(userID);
    }
    @DeleteMapping("/signup/{userID}")
    String deleteUser(@PathVariable String userID){
        userService.deleteUser(userID);
        return "User has been deleted";
    }
}
