package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangePasswordRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangeUserFieldsRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.LoginRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.UserService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.AddressMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.UserMapper;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final UserValidator userValidator;

    UserController(UserService userService, AuthenticationManager authenticationManager, AuthService authService, UserValidator userValidator){
        this.userService=userService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.userValidator = userValidator;
    }

    @PostMapping ("/register")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody UserDTO user) throws Exception {
        UserValidator.emptyUserDTOFieldsValidator(user);
        user.setUserType(UserType.STANDARD);
        final User savedUser=userService.saveUser(
                UserMapper.toUser(user)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success:",
                        UserMapper.toDTO(savedUser)
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginAuthentication(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) throws Exception {
        GlobalValidator.checkIfTwoFieldsAreEmpty(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        final Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext); // saves security context in the session
        session.setAttribute("userId", authentication.getName());
        final User user=userValidator.returnUserById(authService.getAuthenticatedUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        UserMapper.toDTO(user)
                ));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // session logout
        return ResponseEntity.ok("success");
    }

    @PutMapping("/deactivate")
    public ResponseEntity <Map<String,String>> deactivateAccount(@RequestParam String userId){
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        userService.deactivateAccount(userId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success",userId));
    }

    @GetMapping("/addresses")
    public ResponseEntity<Map<String,AddressDTO[]>> getUserAddresses(@RequestParam String userId) throws Exception {
        GlobalValidator.checkIfAFieldIsEmpty(userId);
        authService.checkFraudulentRequest(userId);
        final List<Address> addresses=userService.getUserAddresses(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("addresses",
                        AddressMapper.addressListToAddressDTOArray(addresses)
                ));
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<Map<String,Object>> changeUserEmail(@RequestBody ChangeUserFieldsRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
        authService.checkFraudulentRequest(request.getUserId());
        userService.changeEmail(request.getUserId(), request.getNewField());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        "Email modification completed. User ID: '"+request.getUserId()+
                                "'. New email: '"+request.getNewField()+"'."));
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String,Object>> changeUserPassword(@RequestBody ChangePasswordRequestDTO request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        UserValidator.emptyChangePasswordFieldsValidator(request);
        authService.checkFraudulentRequest(request.getId());
        userService.changeUserPassword(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("success",
                        "Password modification completed. User ID: '"+request.getId()+"'."));
    }

}
