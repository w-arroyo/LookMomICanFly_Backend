package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUse;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserTypeNotFound;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Models.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserTypeRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.AESEncryptionUtil;
import com.alvarohdezarroyo.lookmomicanfly.Utils.PasswordUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    UserService(UserRepository userRepository, UserTypeRepository userTypeRepository){
        this.userRepository=userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    protected List<User> findAllUsers(){
        return userRepository.findAll();
    }

    private UserType returnUserTypeById(int idUserType){
        return userTypeRepository.findById(idUserType).orElseThrow(()->new UserTypeNotFound("USERTYPE WITH ID "+idUserType+" DOES NOT EXIST"));
    }

    private boolean checkUserByEmail(String email){
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public User saveUser(User user) {
        if(user.getEmail().trim().isBlank() || user.getNameAsString().trim().isBlank() || user.getPassword().trim().isBlank() || user.getUserTypeId()==null){
            throw new EmptyFieldsException("Empty fields are not allowed");
        }
        if(checkUserByEmail(user.getEmail())){
            throw new EmailAlreadyInUse("Email already in use.");
        }
        try {
            user.setUserType(returnUserTypeById(user.getUserTypeId()));
            user.setName(AESEncryptionUtil.encrypt(user.getNameAsString()).getBytes());
            user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
            return userRepository.save(user);
        }
        catch (UserTypeNotFound ex){
            throw new UserNotFoundException("USERTYPE PK DOES NOT EXIST");
        }
        catch (Exception ex){
            throw new RuntimeException("Server error at UserService.saveUser");
        }
    }
    @Transactional
    public User deactivateAccount(String email){
        final User user=userRepository.findByEmail(email);
        if(user!=null){

            user.setActive(false);

            return userRepository.save(user);
        }
        throw new UserNotFoundException("USER NOT FOUND");
    }
}
