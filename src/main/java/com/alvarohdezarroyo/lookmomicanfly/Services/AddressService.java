package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.UserRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.AESEncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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

    private void encryptAddressFields(Address address) throws Exception {
        address.setFullName(AESEncryptionUtil.encrypt(address.getFullNameAsString()).getBytes());
        address.setStreet(AESEncryptionUtil.encrypt(address.getStreetAsString()).getBytes());
        address.setZipCode(AESEncryptionUtil.encrypt(address.getZipCodeAsString()).getBytes());
        address.setCity(AESEncryptionUtil.encrypt(address.getCityAsString()).getBytes());
        address.setCountry(AESEncryptionUtil.encrypt(address.getCountryAsString()).getBytes());
    }

    public Address saveAddress(Address address) {
        if(address.getFullNameAsString().trim().isEmpty() || address.getStreetAsString().trim().isEmpty() || address.getZipCodeAsString().trim().isEmpty() || address.getCityAsString().trim().isEmpty() || address.getCountryAsString().trim().isEmpty() ||address.getUserId()==null){
            throw new EmptyFieldsException("Empty fields are not allowed");
        }
        try {
            address.setUser(checkIfUserIdExists(address.getUserId()));
            encryptAddressFields(address);
            return addressRepository.save(address);
        }
        catch (UserNotFoundException ex){
            throw new UserNotFoundException("User Id not found.");
        } catch (Exception e) {
            throw new RuntimeException("Server error");
        }
    }
    public AddressDTO decryptAllFieldsOfAnAddress(Address address) throws Exception {
        AddressDTO addressDTO=new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setFullName(AESEncryptionUtil.decrypt(new String(address.getFullName(), StandardCharsets.UTF_8)));
        addressDTO.setStreet(AESEncryptionUtil.decrypt(new String(address.getStreet(), StandardCharsets.UTF_8)));
        addressDTO.setCity(AESEncryptionUtil.decrypt(new String(address.getCity(), StandardCharsets.UTF_8)));
        addressDTO.setCountry(AESEncryptionUtil.decrypt(new String(address.getCountry(), StandardCharsets.UTF_8)));
        addressDTO.setZipCode(AESEncryptionUtil.decrypt(new String(address.getZipCode(), StandardCharsets.UTF_8)));
        return addressDTO;
    }
}
