package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserTypeNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
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
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody User user){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("userId",userService.saveUser(user).getId()));
        }
        catch (UserTypeNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","User type ID not found"));
        }
        catch (EmptyFieldsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Empty fields"));
        }
        catch (EmailAlreadyInUseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Email is already in use"));
        }
        catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message","Server error when saving new user"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAuthentication(@RequestBody User user){
        try {
            if(loginService.authenticateUser(user).equals("SUCCESS")){
                return ResponseEntity.status(HttpStatus.OK).body("Login successful");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials.");
        }
        catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist.");
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
        catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");
        }
        catch (Exception ex){
            return ResponseEntity.status(500).body("Server error.");
        }
    }

    @GetMapping("/addresses/{userId}")
    public ResponseEntity<Map<String, Object>> getUserAddresses(@PathVariable int userId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    Map.of("addresses", userService.getUserAddresses(userId))
            );
        }
        catch (UserNotFoundException ex){
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
