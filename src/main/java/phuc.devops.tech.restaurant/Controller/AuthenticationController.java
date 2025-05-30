package phuc.devops.tech.restaurant.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phuc.devops.tech.restaurant.Service.AuthenticationService;
import phuc.devops.tech.restaurant.dto.request.ApiResponse;
import phuc.devops.tech.restaurant.dto.request.AuthenticationRequest;
import phuc.devops.tech.restaurant.dto.response.AuthenticationResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login/user")
    ApiResponse<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request){
        boolean result = authenticationService.authenticateUser(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
    @PostMapping("/login/admin")
    ApiResponse<AuthenticationResponse> authenticateAdmin(@RequestBody AuthenticationRequest request){
        boolean result = authenticationService.authenticateAdmin(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
    @PostMapping("/logout")
    public ApiResponse<String> logout(){
        authenticationService.logout();
        return ApiResponse.<String>builder()
                .result("Đăng xuất thành công")
                .build();
    }
}