package phuc.devops.tech.restaurant.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.ExceptionHandling.AppException;
import phuc.devops.tech.restaurant.ExceptionHandling.ErrorCode;
import phuc.devops.tech.restaurant.Repository.AdminRepository;
import phuc.devops.tech.restaurant.Repository.UserRepository;
import phuc.devops.tech.restaurant.dto.request.AuthenticationRequest;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    AdminRepository adminRepository;
    public boolean authenticateUser(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    public boolean authenticateAdmin(AuthenticationRequest request){
        var admin = adminRepository.findByAdminName(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(request.getPassword(), admin.getPassword());
    }
    public void logout(){
        System.out.println("You has logout");
    }
}