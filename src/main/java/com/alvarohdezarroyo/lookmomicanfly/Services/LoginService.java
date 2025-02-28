package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private final UserRepository userRepository;

    LoginService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public String authenticateUser(User user){
        if(user.getEmail().trim().isBlank() || user.getPassword().trim().isBlank()){
            throw new EmptyFieldsException("EMPTY FIELDS ARE NOT ALLOWED");
        }
        final User foundUser=userRepository.findByEmail(user.getEmail()).orElseThrow(
                ()-> new UserNotFoundException("Email does not belong to any user account.")
        );
        if(!foundUser.getActive()){
            throw new UserNotFoundException("Your account is deactivated.");
        }
        if(PasswordUtils.checkPassword(user.getPassword(), foundUser.getPassword())){
            return "SUCCESS";
        }
        return "INVALID CREDENTIALS";
    }
}
