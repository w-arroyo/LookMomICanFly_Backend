package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.UserMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    UserService(UserValidator userValidator, UserRepository userRepository){
        this.userValidator = userValidator;
        this.userRepository=userRepository;
    }

    @Transactional
    public User saveUser(UserDTO user) {
        try {
            if(userValidator.checkUserByEmail(user.getEmail()))
                throw new EmailAlreadyInUseException("Email is already in use");
            return userRepository.save(UserMapper.toUser(user));
        }
        catch (Exception ex){
            throw new RuntimeException("Server error. Try again later.");
        }
    }
    @Transactional
    public void deactivateAccount(String id){
        try {
            if(userRepository.deactivateUserAccount(id)<1){
               throw new EntityNotFoundException("Email does not belong to any user account.");
            }
        } catch (Exception e){
            throw new RuntimeException("Server error when updating the user");
        }
    }

    @Transactional
    public void changeEmail(String ogEmail, String newEmail){
        try{
            if(userValidator.checkUserByEmail(newEmail)){
                throw new EmailAlreadyInUseException("Email is already in use.");
            }
            if(userRepository.changeUserEmail(ogEmail,newEmail)<1){
                throw new EntityNotFoundException("Former email does not belong to any user account.");
            }
        } catch (RuntimeException ex){
            throw new RuntimeException("Server error when updating the user");
        }
    }

    // change user password method left

    public AddressDTO[] getUserAddresses(String id) throws Exception {
        final List<Address> addresses = userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Email is not associated with any user account in the DB.")
        ).getAddresses();
        if(addresses.isEmpty())
            return null;
        final AddressDTO [] dtoAddresses = new AddressDTO[addresses.size()];
        for (int address = 0; address < addresses.size(); address++) {
            dtoAddresses[address]= AddressMapper.toDTO(addresses.get(address));
        }
        return dtoAddresses;
    }
}
