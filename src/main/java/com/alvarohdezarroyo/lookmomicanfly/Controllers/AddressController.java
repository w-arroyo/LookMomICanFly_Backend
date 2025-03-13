package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Requests.DeactivateAddressRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AddressService;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
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
    private final AuthService authService;

    public AddressController(AddressService addressService, AuthService authService) {
        this.addressService = addressService;
        this.authService = authService;
    }

    @PostMapping("/save")
    public ResponseEntity <Map<String,Object>> saveAddress(@RequestBody AddressDTO address){
        GlobalValidator.checkIfRequestBodyIsEmpty(address);
        AddressValidator.checkIfFieldsAreEmpty(address);
        authService.checkIfAUserIsLoggedIn();
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", "Address with id: '"+addressService.saveAddress(address).getId()+"' saved successfully"));
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivateAddress(@RequestBody DeactivateAddressRequest request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getId(), request.getUserId());
        GlobalValidator.checkFraudulentRequest(request.getUserId(), authService.getAuthenticatedUserId());
        addressService.deactivateAddress(request.getId(), request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("Address with ID: '"+request.getId()+"' deactivated");
    }

}
