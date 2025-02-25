package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;

    UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping ("/register")
    public ResponseEntity<String> createUser(@RequestBody User user){
        try {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Creado");
        }
        catch (IllegalArgumentException ex){
            System.out.println("Error al registrar");
            return ResponseEntity.status(500).body("Error al registrar");
        }
    }
}
