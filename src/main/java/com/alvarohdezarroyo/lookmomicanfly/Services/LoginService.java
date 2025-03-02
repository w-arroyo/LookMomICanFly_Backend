package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.LoginUnsuccessfulException;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private final UserRepository userRepository;

    LoginService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void authenticateUser(String email, String password){
        if(!PasswordUtils.checkPassword(password,userRepository.findByEmail(email).orElseThrow(
                ()->new EntityNotFoundException("Email does not belong to any user account")
        ).getPassword())){
            throw new LoginUnsuccessfulException("Wrong credentials");
        }
    }
}
