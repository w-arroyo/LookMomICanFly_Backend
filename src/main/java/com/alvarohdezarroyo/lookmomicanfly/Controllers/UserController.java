package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.DataNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.LoginUnsuccessfulException;
import com.alvarohdezarroyo.lookmomicanfly.Requests.LoginRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.LoginService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id:",userService.saveUser(user).getId()));
        }
        catch (DataNotFoundException | EmptyFieldsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",e.getMessage()));
        }
        catch (EmailAlreadyInUseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Email is already in use"));
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Server error when saving new user"));
        }
    }

    @PostMapping("/login")
    //needs multiple improvements like better exception handling
    public ResponseEntity<String> loginAuthentication(@RequestBody LoginRequest loginRequest){
        try {
            loginService.authenticateUser(loginRequest.getEmail(),loginRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        }
        catch (LoginUnsuccessfulException | DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials");
        }
        catch (EmptyFieldsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Empty fields are not allowed.");
        }
        catch (Exception ex){
            return ResponseEntity.status(500).body("Server error. Try again later.");
        }
    }

    @GetMapping("/deactivate/{email}")
    public ResponseEntity<String> deactivateAccount(@PathVariable String email){
        try {
            userService.deactivateAccount(email);
            return ResponseEntity.status(HttpStatus.OK).body("ACCOUNT DEACTIVATED");
        }
        catch (DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");
        }
        catch (Exception ex){
            return ResponseEntity.status(500).body("Server error.");
        }
    }

    @GetMapping("/addresses/{email}")
    public ResponseEntity<Map<String, Object>> getUserAddresses(@PathVariable String email){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of("addresses", userService.getUserAddresses(email))
            );
        }
        catch (DataNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "User was not found")
            );
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Server error")
            );
        }
    }
}
