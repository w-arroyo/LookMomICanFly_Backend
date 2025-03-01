package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.DataNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Requests.AddressRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity <String> saveAddress(@RequestBody AddressRequest address){
        try {
            addressService.saveAddress(address.getAddress(), address.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESSFUL");
        } catch (DataNotFoundException | EmptyFieldsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error.");
        }
    }
    /*
    @GetMapping("/deactivate")
    public ResponseEntity<String> deactivateAddress(@PathVariable int id){

    }
    */
}
