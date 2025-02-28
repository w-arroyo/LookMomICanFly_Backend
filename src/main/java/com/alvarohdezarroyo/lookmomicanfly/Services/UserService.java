package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmailAlreadyInUseException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserTypeNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
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
    private final AddressService addressService;

    UserService(UserRepository userRepository, UserTypeRepository userTypeRepository, AddressService addressService){
        this.userRepository=userRepository;
        this.userTypeRepository = userTypeRepository;
        this.addressService = addressService;
    }

    protected List<User> findAllUsers(){
        return userRepository.findAll();
    }

    private UserType returnUserTypeById(int idUserType){
        return userTypeRepository.findById(idUserType).orElseThrow(()->new UserTypeNotFoundException("USERTYPE WITH ID "+idUserType+" DOES NOT EXIST"));
    }

    private boolean checkUserByEmail(String email){
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public User saveUser(User user) {
        if(user.getEmail().trim().isBlank() || user.getNameAsString().trim().isBlank() || user.getPassword().trim().isBlank() || user.getUserTypeId()==null){
            throw new EmptyFieldsException("Empty fields are not allowed");
        }
        if(!checkUserByEmail(user.getEmail())){
            throw new EmailAlreadyInUseException("Email already in use.");
        }
        try {
            user.setUserType(returnUserTypeById(user.getUserTypeId()));
            user.setName(AESEncryptionUtil.encrypt(user.getNameAsString()).getBytes());
            user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
            return userRepository.save(user);
        }
        catch (UserTypeNotFoundException ex){
            throw new UserTypeNotFoundException("USERTYPE PK DOES NOT EXIST");
        }
        catch (Exception ex){
            throw new RuntimeException("Server error at UserService.saveUser");
        }
    }
    @Transactional
    public void deactivateAccount(String email){
        final User user=userRepository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("Email is not associated with any user account")
        );
        user.setActive(false);
        try {
            userRepository.save(user);
        }
        catch (Exception e){
            throw new RuntimeException("Server error when updating the user");
        }
    }
    @Transactional
    public AddressDTO[] getUserAddresses(int id) throws Exception {
        final List<Address> addresses = userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("Email is not associated with any user account in the DB.")
        ).getAddresses();
        // meter excepcion o algo pa controlar que no tenga direcciones? que sea generica para usar mas veces
        AddressDTO [] dtoAddresses = new AddressDTO[addresses.size()];
        for (int address = 0; address < addresses.size(); address++) {
            dtoAddresses[address]=addressService.decryptAllFieldsOfAnAddress(addresses.get(address));
        }
        return dtoAddresses;
    }
}
