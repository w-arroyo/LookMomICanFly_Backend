package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUse;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserTypeNotFound;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Services.LoginService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createUser(@RequestBody User user){
        try {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created correctly.");
        }
        catch (UserTypeNotFound e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong or empty fields.");
        }
        catch (EmptyFieldsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong or empty fields.");
        }
        catch (EmailAlreadyInUse e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already in use.");
        }
        catch (IllegalArgumentException ex){
            return ResponseEntity.status(500).body("Server error when saving new user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAuthentication(@RequestBody User user){
        try {
            if(loginService.authenticateUser(user).equals("SUCCESS")){
                return ResponseEntity.status(HttpStatus.OK).body("Login successful.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong credentials.");
        }
        catch (UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USER NOT FOUND");
        }
        catch (EmptyFieldsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMPTY FIELDS");
        }
        catch (Exception ex){
            return ResponseEntity.status(500).body("Server error.");
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
}
