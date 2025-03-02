package com.alvarohdezarroyo.lookmomicanfly.Validators;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {

    @Autowired
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void emptyFieldsValidator(UserDTO user){
        final List<String> errorsList=new ArrayList<>();
        if(user.getEmail().isBlank())
            errorsList.add("email");
        if(user.getName().isBlank())
            errorsList.add("name");
        if (user.getPassword().isBlank())
            errorsList.add("password");
        if(!errorsList.isEmpty())
            throw new EmptyFieldsException(errorsList);
    }

    public User returnUserById(String id){
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Email does not belong to any user account")
        );
    }

    public boolean checkUserByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
