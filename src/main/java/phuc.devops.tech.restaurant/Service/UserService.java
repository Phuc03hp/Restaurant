package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.ExceptionHandling.AppException;
import phuc.devops.tech.restaurant.ExceptionHandling.ErrorCode;
import phuc.devops.tech.restaurant.Repository.UserRepository;
import phuc.devops.tech.restaurant.dto.request.AdminCreateAccountUser;
import phuc.devops.tech.restaurant.dto.request.AdminUpdateAccountUser;

import java.util.List;

@Service
public class UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    @Autowired
    private UserRepository userRepository ;
    public User createUser(AdminCreateAccountUser request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword())) ;
        return userRepository.save(user);
    }

    public User updateUser(String userID, AdminUpdateAccountUser request) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật name
        user.setName(request.getName());

        // Cập nhật username (nếu cần)
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        // Nếu password được gửi thì mới cập nhật
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(String userID){

        userRepository.deleteById(userID);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(String userID){
        return userRepository.findById(userID).orElseThrow();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }

}
