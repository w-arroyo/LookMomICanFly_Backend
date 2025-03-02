package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EmptyFieldsException;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.AddressValidator;
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
        /*
        assertDePermisos (mismo user o admin)
        validacionDeLosAtritbutos
        Mappear de Dto a Model
        Guardar model
        Mappear de Model a Dto
         */
        try {
            AddressValidator.checkIfFieldsAreEmpty(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", "Address with id: '"+addressService.saveAddress(address).getId()+"' saved successfully"));
        } catch (EmptyFieldsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getEmptyFields()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    /*
    @GetMapping("/deactivate")
    public ResponseEntity<String> deactivateAddress(@PathVariable int id){

    }
    */
}
