package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.Admin;
import phuc.devops.tech.restaurant.ExceptionHandling.AppException;
import phuc.devops.tech.restaurant.ExceptionHandling.ErrorCode;
import phuc.devops.tech.restaurant.Repository.AdminRepository;
import phuc.devops.tech.restaurant.dto.request.AdminCreateAccountAdmin;
import phuc.devops.tech.restaurant.dto.request.AdminUpdateAccountAdmin;

import java.util.List;

@Service
public class AdminService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    @Autowired
    private AdminRepository adminRepository;

    public Admin createAdmin(AdminCreateAccountAdmin request){
        if (adminRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setName(request.getName());
        admin.setPassword(passwordEncoder.encode(request.getPassword())) ;
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(String adminID, AdminUpdateAccountAdmin request){
        Admin admin = adminRepository.findById(adminID).orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setName(request.getName());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        return adminRepository.save(admin);
    }

    public void deleteAdmin(String adminID){
        adminRepository.deleteById(adminID);
    }

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public Admin getAdmin(String adminID){
        return adminRepository.findById(adminID).orElseThrow();
    }

    public Admin findByUsername(String username){
        return adminRepository.findByUsername(username).orElseThrow();
    }
}
