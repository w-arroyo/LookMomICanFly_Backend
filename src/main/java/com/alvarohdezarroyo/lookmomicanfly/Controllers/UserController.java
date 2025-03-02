package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.*;
import com.alvarohdezarroyo.lookmomicanfly.Requests.LoginRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.LoginService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error",e.getEmptyFields()));
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
        if(loginRequest.getEmail().isBlank() || loginRequest.getPassword().isBlank())
            throw new EmptyFieldsException("Empty fields are not allowed.");
        try {
            loginService.authenticateUser(loginRequest.getEmail(),loginRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        }
        catch (LoginUnsuccessfulException | EntityNotFoundException ex){
            throw new LoginUnsuccessfulException(ex.getMessage());
        } catch (Exception ex){
           throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable String id){
        if(id==null)
            throw new EmptyFieldsException("User id must be stated.");
        try {
            userService.deactivateAccount(id);
            return ResponseEntity.status(HttpStatus.OK).body("User ID: '"+id+"' account successfully deactivated. ");
        } catch (EntityNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");
        } catch (Exception ex){
            return ResponseEntity.status(500).body("Server error.");
        }
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<AddressDTO[]> getUserAddresses(@PathVariable String id){
        if (id==null)
            throw new EmptyFieldsException("Request is empty.");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUserAddresses(id));
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundException(ex.getMessage());
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    @GetMapping("/changeEmail")
    public ResponseEntity<String> changeUserEmail(@RequestParam String ogEmail, @RequestParam String newEmail){
        if(ogEmail.isBlank() || newEmail.isBlank())
            throw new EmptyFieldsException("Email must be filled.");
        if(ogEmail.equalsIgnoreCase(newEmail)){
            throw new SameValuesException("Same email");
        }
        try{
            userService.changeEmail(ogEmail,newEmail);
            return ResponseEntity.status(HttpStatus.OK).body("Email modification completed. Former email: "+ogEmail+". New email:"+newEmail);
        } catch (EmailAlreadyInUseException ex){
            throw new EmailAlreadyInUseException(ex.getMessage());
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundException(ex.getMessage());
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
