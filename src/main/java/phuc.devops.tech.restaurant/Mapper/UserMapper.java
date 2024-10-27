package phuc.devops.tech.restaurant.Mapper;

import org.mapstruct.Mapper;
import phuc.devops.tech.restaurant.Entity.User;
import phuc.devops.tech.restaurant.dto.request.UserCreateAccount;
import phuc.devops.tech.restaurant.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateAccount request);
    UserResponse toUserResponse(User user);

}
