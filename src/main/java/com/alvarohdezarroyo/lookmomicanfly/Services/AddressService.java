package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private final AddressRepository addressRepository;
    private final AuthService authService;

    public AddressService(AddressRepository addressRepository, AuthService authService) {
        this.addressRepository = addressRepository;
        this.authService = authService;
    }

    @Transactional
    public Address saveAddress(Address address){
        return addressRepository.save(address);
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

    public Address getAddressById(String id){
        return addressRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Address id does not exist.")
        );
    }

}
