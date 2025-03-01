package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.DataNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public void saveAddress(AddressDTO addressDTO, String email) {
        if(addressDTO.getFullName().trim().isEmpty() || addressDTO.getStreet().trim().isEmpty() || addressDTO.getZipCode().trim().isEmpty() || addressDTO.getCity().trim().isEmpty() || addressDTO.getCountry().trim().isEmpty() || email.trim().isEmpty()){
            throw new EmptyFieldsException("Empty fields are not allowed");
        }
        try {
            addressRepository.save(AddressMapper.toEntity(addressDTO, userService.checkUserByEmail(email)));
        }
        catch (DataNotFoundException ex){
            throw new DataNotFoundException("User Id not found.");
        } catch (Exception e) {
            throw new RuntimeException("Server error");
        }
    }

    public void deactivateAddress(int id){

    }

}
