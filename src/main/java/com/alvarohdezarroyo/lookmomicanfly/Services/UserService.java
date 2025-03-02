package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.SameValuesException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Models.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserTypeRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.UserMapper;
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

    public User checkIfUserIdExists(int id){
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("User not found")
        );
    }

    private UserType returnUserTypeById(int idUserType){
        return userTypeRepository.findById(idUserType).orElseThrow(()->new EntityNotFoundException("USERTYPE WITH ID "+idUserType+" DOES NOT EXIST"));
    }

    public User checkUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("Email does not belong to any user account")
        );
    }

    @Transactional
    public User saveUser(UserDTO user) {
        if(user.getEmail().trim().isBlank() || user.getName().trim().isBlank() || user.getPassword().trim().isBlank() || user.getUserTypeId()==null){
            throw new EmptyFieldsException("Empty fields are not allowed");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailAlreadyInUseException("Email already in use.");
        }
        try {
            return userRepository.save(UserMapper.toUser(user,returnUserTypeById(user.getUserTypeId())));
        }
        catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("USERTYPE PK DOES NOT EXIST");
        }
        catch (Exception ex){
            throw new RuntimeException("Server error at UserService.saveUser");
        }
    }
    @Transactional
    public void deactivateAccount(String email){
        try {
            if(userRepository.deactivateUserAccount(email)<1){
               throw new EntityNotFoundException("Email does not belong to any user account");
            }
        } catch (Exception e){
            throw new RuntimeException("Server error when updating the user");
        }
    }

    @Transactional
    public void changeEmail(String ogEmail, String newEmail){
        try{
            if(ogEmail.equalsIgnoreCase(newEmail)){
                throw new SameValuesException("Same email");
            }
            if(userRepository.findByEmail(newEmail).isPresent()){
                throw new EmailAlreadyInUseException("Email already in use");
            }
            if(userRepository.changeUserEmail(ogEmail,newEmail)<1){
                throw new EntityNotFoundException("Email does not belong to any user account");
            }
        } catch (RuntimeException ex){
            throw new RuntimeException("Server error when updating the user");
        }
    }

    // change user password method left

    public AddressDTO[] getUserAddresses(String email) throws Exception {
        final List<Address> addresses = userRepository.findByEmail(email).orElseThrow(
                ()-> new EntityNotFoundException("Email is not associated with any user account in the DB.")
        ).getAddresses();
        if(addresses.isEmpty()){
            return null;
        }
        final AddressDTO [] dtoAddresses = new AddressDTO[addresses.size()];
        for (int address = 0; address < addresses.size(); address++) {
            dtoAddresses[address]= AddressMapper.toDTO(addresses.get(address));
        }
        return dtoAddresses;
    }
}
