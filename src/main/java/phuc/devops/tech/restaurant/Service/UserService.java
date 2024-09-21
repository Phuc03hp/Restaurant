package phuc.devops.tech.restaurant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.ExceptionHandling.AppException;
import phuc.devops.tech.restaurant.ExceptionHandling.ErrorCode;
import phuc.devops.tech.restaurant.Repository.UserRepository;
import phuc.devops.tech.restaurant.dto.request.UserCreateAccount;
import phuc.devops.tech.restaurant.dto.request.UserUpdateAccount;

import java.util.List;

@Service
public class UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    @Autowired
    private UserRepository userRepository ;

    public User createUser(UserCreateAccount request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setNumberPhone(request.getNumberPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(String userID, UserUpdateAccount request){
        User user = userRepository.findById(userID).orElseThrow(()->new RuntimeException("User not found"));
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setNumberPhone(request.getNumberPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return  userRepository.save(user);
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
}
