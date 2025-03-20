package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Requests.AskRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PostService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/asks")
public class AskController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;

    public AskController(AuthService authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveAsk(@RequestBody AskRequest askRequest) throws Exception {
        GlobalValidator.checkIfRequestBodyIsEmpty(askRequest);
        PostValidator.checkIfPostFieldsAreEmpty(askRequest, askRequest.getSellingFeeId(), "selling fee");
        GlobalValidator.checkIfANumberIsGreaterThan(askRequest.getAmount(),1);
        authService.checkFraudulentRequest(askRequest.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.saveAskFromRequest(askRequest));
    }

}
