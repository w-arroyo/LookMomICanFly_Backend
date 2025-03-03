package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.*;
import com.alvarohdezarroyo.lookmomicanfly.Requests.ChangeUserFieldsRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.LoginRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.LoginService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    private final LoginService loginService;

    UserController(UserService userService, LoginService loginService){
        this.userService=userService;
        this.loginService = loginService;
    }

    @PostMapping ("/register")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody UserDTO user){
        try {
            UserValidator.emptyFieldsValidator(user);
            user.setUserType(UserType.STANDARD);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("user:",userService.saveUser(user)));
        }
        catch (EmptyFieldsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errors",e.getEmptyFields()));
        }
        catch (EmailAlreadyInUseException e){
            throw new EmailAlreadyInUseException(e.getMessage());
        }
        catch (RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAuthentication(@RequestBody LoginRequest loginRequest){
        try {
            GlobalValidator.checkIfTwoFieldsAreEmpty(loginRequest.getEmail(), loginRequest.getPassword());
            loginService.authenticateUser(loginRequest.getEmail(),loginRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        }
        catch (EmptyFieldsException | LoginUnsuccessfulException | EntityNotFoundException ex){
            throw new LoginUnsuccessfulException(ex.getMessage());
        } catch (Exception ex){
           throw new RuntimeException(ex.getMessage());
        }
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateAccount(@RequestParam String userId){
        try {
            if(userId==null || userId.isBlank())
                throw new EmptyFieldsException("Empty fields are not allowed.");
            userService.deactivateAccount(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User ID: '"+userId+"' account successfully deactivated. ");
        } catch (EmptyFieldsException | EntityNotFoundException | FraudulentRequestException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");
        } catch (Exception ex){
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @GetMapping("/addresses")
    public ResponseEntity<AddressDTO[]> getUserAddresses(@RequestParam String userId){
        try {
            if(userId==null || userId.isBlank())
                throw new EmptyFieldsException("Empty fields are not allowed.");
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserAddresses(userId));
        } catch (EntityNotFoundException | FraudulentRequestException ex){
            throw new IllegalArgumentException(ex.getMessage());
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    @PutMapping("/changeEmail")
    public ResponseEntity<Map<String,Object>> changeUserEmail(@RequestBody ChangeUserFieldsRequest request){
        try{
            GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
            userService.changeEmail(request.getUserId(), request.getNewField());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("success","Email modification completed. User ID: '"+request.getUserId()+"'. New email: '"+request.getNewField()+"'."));
        } catch (EmptyFieldsException | EntityNotFoundException | EmailAlreadyInUseException | FraudulentRequestException ex){
            throw new IllegalArgumentException(ex.getMessage());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
