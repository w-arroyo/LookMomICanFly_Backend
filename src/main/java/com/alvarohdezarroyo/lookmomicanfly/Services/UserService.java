package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.AESEncryptionUtil;
import com.alvarohdezarroyo.lookmomicanfly.Utils.PasswordUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    protected User checkUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    protected List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        if(user.getEmail().trim().isBlank() || user.getNameAsString().trim().isBlank() || user.getPassword().trim().isBlank()){
            throw new IllegalArgumentException("Los campos no pueden estar vacíos");
        }
        try {
            user.setName(AESEncryptionUtil.encrypt(user.getNameAsString()).getBytes());
            user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
            return userRepository.save(user);
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Error del servidor.");
        }
    }

}
