package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    private User checkIfUserIdExists(int id){
        return userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException("User not found")
        );
    }

    public Address saveAddress(AddressDTO addressDTO, int userId) {
        //check userId is not empty
        if(addressDTO.getFullName().trim().isEmpty() || addressDTO.getStreet().trim().isEmpty() || addressDTO.getZipCode().trim().isEmpty() || addressDTO.getCity().trim().isEmpty() || addressDTO.getCountry().trim().isEmpty()){
            throw new EmptyFieldsException("Empty fields are not allowed");
        }
        try {
            final Address address=AddressMapper.toEntity(addressDTO);
            address.setUser(checkIfUserIdExists(userId));
            return addressRepository.save(address);
        }
        catch (UserNotFoundException ex){
            throw new UserNotFoundException("User Id not found.");
        } catch (Exception e) {
            throw new RuntimeException("Server error");
        }
    }
}
