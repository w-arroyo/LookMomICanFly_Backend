package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Requests.ChangeUserFieldsRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.LoginRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    UserController(UserService userService, AuthenticationManager authenticationManager){
        this.userService=userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping ("/register")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody UserDTO user){
        UserValidator.emptyFieldsValidator(user);
        user.setUserType(UserType.STANDARD);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("user:",userService.saveUser(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginAuthentication(@RequestBody LoginRequest loginRequest){
        GlobalValidator.checkIfTwoFieldsAreEmpty(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("userId",userService.returnUserIdByEmail(loginRequest.getEmail())));
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateAccount(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        userService.deactivateAccount(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User ID: '"+userId+"' account successfully deactivated. ");
    }

    @GetMapping("/addresses")
    public ResponseEntity<AddressDTO[]> getUserAddresses(@RequestParam String userId) throws Exception {
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserAddresses(userId));
    }
    @PutMapping("/changeEmail")
    public ResponseEntity<Map<String,Object>> changeUserEmail(@RequestBody ChangeUserFieldsRequest request){
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
        userService.changeEmail(request.getUserId(), request.getNewField());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success","Email modification completed. User ID: '"+request.getUserId()+"'. New email: '"+request.getNewField()+"'."));
    }

}
