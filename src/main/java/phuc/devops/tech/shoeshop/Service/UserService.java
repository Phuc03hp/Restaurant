package phuc.devops.tech.shoeshop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phuc.devops.tech.shoeshop.Entity.User;
import phuc.devops.tech.shoeshop.Repository.UserRepository;
import phuc.devops.tech.shoeshop.dto.UserCreateAccount;
import phuc.devops.tech.shoeshop.dto.UserCreateModel;
import phuc.devops.tech.shoeshop.dto.UserUpdateAccount;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository ;

    public User createUser(UserCreateAccount request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public User updateUser(String userID, UserUpdateAccount request){
        User user = userRepository.getById(userID);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

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
