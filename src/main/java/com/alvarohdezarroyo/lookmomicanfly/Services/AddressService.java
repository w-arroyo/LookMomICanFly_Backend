package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    private final UserValidator userValidator;
    private final AuthService authService;

    public AddressService(AddressRepository addressRepository, UserValidator userValidator, AuthService authService) {
        this.addressRepository = addressRepository;
        this.userValidator = userValidator;
        this.authService = authService;
    }

    @Transactional
    public Address saveAddress(AddressDTO addressDTO) {
        try {
            return addressRepository.save(AddressMapper.toEntity(addressDTO, userValidator.returnUserById(addressDTO.getUserId())));
        }
        catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("User Id not found.");
        } catch (Exception e) {
            throw new RuntimeException("Server error. Please Try again later.");
        }
    }

    @Transactional
    public void deactivateAddress(String id, String userId){
        try{
            authService.checkFraudulentRequest(addressRepository.findById(id).orElseThrow(
                    ()->new EntityNotFoundException("Address id not found")).getUserId().getId());
            if(addressRepository.deactivateAddress(id)<1)
                throw new RuntimeException("Server error. Please Try again later.");
        } catch (FraudulentRequestException e) {
            throw new FraudulentRequestException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public AddressDTO getAddressDTOById(String id) throws Exception {
        return AddressMapper.toDTO(getAddressById(id));
    }

    public Address getAddressById(String id){
        return addressRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Address id does not exist.")
        );
    }

}
