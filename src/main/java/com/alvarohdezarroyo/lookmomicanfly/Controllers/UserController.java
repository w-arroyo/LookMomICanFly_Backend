package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Requests.ChangePasswordRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.ChangeUserFieldsRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.LoginRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    UserController(UserService userService, AuthenticationManager authenticationManager, AuthService authService){
        this.userService=userService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping ("/register")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody UserDTO user){
        UserValidator.emptyUserDTOFieldsValidator(user);
        user.setUserType(UserType.STANDARD);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id:",userService.saveUser(user).getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginAuthentication(@RequestBody LoginRequest loginRequest, HttpSession session) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(loginRequest.getEmail(), loginRequest.getPassword());
        final Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext); // saves security context in the session
        session.setAttribute("userId", authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("user",userService.returnUserDTOByUserId(authService.getAuthenticatedUserId())));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // session logout
        return ResponseEntity.ok("Successful logout.");
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateAccount(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        GlobalValidator.checkFraudulentRequest(userId, authService.getAuthenticatedUserId());
        userService.deactivateAccount(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User ID: '"+userId+"' account successfully deactivated. ");
    }

    @GetMapping("/addresses")
    public ResponseEntity<AddressDTO[]> getUserAddresses(@RequestParam String userId) throws Exception {
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        GlobalValidator.checkFraudulentRequest(userId, authService.getAuthenticatedUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserAddresses(userId));
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<Map<String,Object>> changeUserEmail(@RequestBody ChangeUserFieldsRequest request){
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
        GlobalValidator.checkFraudulentRequest(request.getUserId(), authService.getAuthenticatedUserId());
        userService.changeEmail(request.getUserId(), request.getNewField());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success","Email modification completed. User ID: '"+request.getUserId()+"'. New email: '"+request.getNewField()+"'."));
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String,Object>> changeUserPassword(@RequestBody ChangePasswordRequest request){
        UserValidator.emptyChangePasswordFieldsValidator(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getNewPassword(), request.getOldPassword());
        GlobalValidator.checkFraudulentRequest(request.getId(), authService.getAuthenticatedUserId());
        userService.changeUserPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success","Password modification completed. User ID: '"+request.getId()+"'."));
    }

}
