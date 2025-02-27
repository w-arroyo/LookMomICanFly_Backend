package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.UserNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, Object>> saveAddress(@RequestBody Address address){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("address_id",addressService.saveAddress(address).getId()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","User Id not found."));
        } catch (EmptyFieldsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Empty fields are not allowed."));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message","Server error."));
        }
    }
}
