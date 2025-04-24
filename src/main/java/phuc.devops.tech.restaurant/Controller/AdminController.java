package phuc.devops.tech.restaurant.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phuc.devops.tech.restaurant.Entity.Admin;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.Service.AdminService;
import phuc.devops.tech.restaurant.dto.request.AdminCreateAccountAdmin;
import phuc.devops.tech.restaurant.dto.request.AdminCreateAccountUser;
import phuc.devops.tech.restaurant.dto.request.AdminUpdateAccountAdmin;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/signup/admin")
    Admin createAdmin(@RequestBody AdminCreateAccountAdmin request){
        return adminService.createAdmin(request);
    }
    @GetMapping("/signup/admin")
    List<Admin> getAdmins(){
        return adminService.getAdmins();
    }
    @PutMapping("/signup/admin/{adminID}")
    Admin updateUser(@PathVariable String adminID, AdminUpdateAccountAdmin request){
        return adminService.updateAdmin(adminID,request);
    }
    @GetMapping("/signup/admin/{adminID}")
    Admin getAdmin(@PathVariable String adminID){
        return adminService.getAdmin(adminID);
    }
    @DeleteMapping("/signup/admin/{userID}")
    String deleteAdmin(@PathVariable String userID){
        adminService.deleteAdmin(userID);
        return "User has been deleted";
    }
    @GetMapping("/admin/{adminName}")
    Admin findByAdminName(@PathVariable String username){
        return adminService.findByUsername(username);
    }
}
