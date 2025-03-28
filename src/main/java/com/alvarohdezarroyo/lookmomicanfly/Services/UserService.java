package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.SameValuesException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangePasswordRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.PasswordUtils;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PostService postService;

    UserService(UserValidator userValidator, UserRepository userRepository, PostService postService){
        this.userValidator = userValidator;
        this.userRepository=userRepository;
        this.postService = postService;
    }

    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void deactivateAccount(String id){
        try {
            if(!userValidator.checkUserById(id))
                throw new EntityNotFoundException("ID does not belong to any user account.");
            if(userRepository.deactivateUserAccount(id)<1)
               throw new EntityNotFoundException("Server error. Try again later.");
            postService.deactivateAllUserPosts(id);
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Transactional
    public void changeEmail(String id, String newEmail){
        try{
            final User user=userRepository.findById(id)
                    .orElseThrow(()->new EntityNotFoundException("User ID not found."));
            UserValidator.checkIfBothEmailsAreTheSame(user.getEmail(),newEmail);
            if(userValidator.checkUserByEmail(newEmail))
                throw new EmailAlreadyInUseException("Email is already in use.");
            if(userRepository.changeUserEmail(id,newEmail)<1)
                throw new EntityNotFoundException("Server error.");
        } catch (SameValuesException ex){
            throw new SameValuesException(ex.getMessage());
        } catch (FraudulentRequestException ex){
            throw new FraudulentRequestException(ex.getMessage());
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void changeUserPassword(ChangePasswordRequestDTO request){
        try {
            final User user=userValidator.returnUserById(request.getId());
            if(!PasswordUtils.checkPassword(request.getOldPassword(),user.getPassword()))
                throw new IllegalArgumentException("Wrong password.");
            if(PasswordUtils.checkPassword(request.getNewPassword(),user.getPassword()))
                throw new SameValuesException("New password can't be the same as the former one.");
            if(userRepository.changeUserPassword(request.getId(),PasswordUtils.hashPassword(request.getNewPassword()))<1)
                throw new EntityNotFoundException("Server error. Try again later.");
        } catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Address> getUserAddresses(String id){
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Id is not associated with any user account in the DB.")
        ).getAddresses();
    }
}
