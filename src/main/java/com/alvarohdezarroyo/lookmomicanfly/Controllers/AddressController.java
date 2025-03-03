package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.FraudulentRequestException;
import com.alvarohdezarroyo.lookmomicanfly.Requests.DeactivateAddressRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.AddressValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity <Map<String,Object>> saveAddress(@RequestBody AddressDTO address){
        try {
            AddressValidator.checkIfFieldsAreEmpty(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", "Address with id: '"+addressService.saveAddress(address).getId()+"' saved successfully"));
        } catch (EmptyFieldsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getEmptyFields()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateAddress(@RequestBody DeactivateAddressRequest request){
        try {
            GlobalValidator.checkIfTwoFieldsAreEmpty(request.getId(), request.getUserId());
            addressService.deactivateAddress(request.getId(), request.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body("Address with ID: '"+request.getId()+"' deactivated");
        } catch (EmptyFieldsException | EntityNotFoundException | FraudulentRequestException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Server error. Try again later.");
        }
    }

}
