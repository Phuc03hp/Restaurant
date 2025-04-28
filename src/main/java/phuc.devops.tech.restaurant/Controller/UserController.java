package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Service.UserService;
import phuc.devops.tech.restaurant.dto.request.AdminCreateAccountUser;
import phuc.devops.tech.restaurant.dto.request.AdminUpdateAccountUser;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup/user")
    User createUser(@RequestBody AdminCreateAccountUser request){
        return userService.createUser(request);
    }
    @GetMapping("/signup/user")
    List<User> getUsers(){
        return userService.getUsers();
    }
    @PutMapping("/signup/user/{userID}")
    User updateUser(@PathVariable String userID, AdminUpdateAccountUser request){
        return userService.updateUser(userID,request);
    }
    @GetMapping("/signup/user/{userID}")
    User getUser(@PathVariable String userID){
        return userService.getUser(userID);
    }
    @DeleteMapping("/signup/user/{userID}")
    String deleteUser(@PathVariable String userID){
        userService.deleteUser(userID);
        return "User has been deleted";
    }
    @GetMapping("/user/{username}")
    User findByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/users")
    List<User> getListUser(){
        return userService.getUsers();
    }
}
