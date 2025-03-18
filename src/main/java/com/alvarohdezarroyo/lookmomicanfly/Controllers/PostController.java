package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.HighestLowestPostDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.PostSummaryDTO;
import com.alvarohdezarroyo.lookmomicanfly.Requests.ChangeUserFieldsRequest;
import com.alvarohdezarroyo.lookmomicanfly.Requests.GetAllSizesPostRequest;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.PostService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.PostValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private final AuthService authService;
    private final PostService postService;

    public PostController(AuthService authService, PostService postService) {
        this.authService = authService;
        this.postService = postService;
    }

    @PutMapping("/deactivate")
    public ResponseEntity<String> deactivatePost(@RequestBody ChangeUserFieldsRequest request){
        checkIfBodyIsEmpty(request);
        authService.checkFraudulentRequest(request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(postService.deactivatePost(request.getNewField(), request.getUserId()));
    }

    @PostMapping("/get-all-summary")
    public ResponseEntity<Map<String, PostSummaryDTO[]>> getAllUserBidsSummaryDTO(@RequestBody ChangeUserFieldsRequest request){
        checkIfBodyIsEmpty(request);
        PostValidator.checkIfEntityExists(request.getNewField());
        authService.checkFraudulentRequest(request.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("posts", postService.getAllUserActivePosts(request.getUserId(), request.getNewField())));
    }

    @PostMapping("/sizes")
    public ResponseEntity<Map<String, HighestLowestPostDTO[]>> getAllSizesPosts(@RequestBody GetAllSizesPostRequest request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        PostValidator.checkIfAllGetSizesRequestAreEmpty(request);
        PostValidator.checkIfEntityExists(request.getEntity());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("posts",postService.getAllHighestLowestPost(request.getProductId(),ProductValidator.checkIfProductCategoryExists(request.getCategory()), request.getEntity())));
    }

    private void checkIfBodyIsEmpty(ChangeUserFieldsRequest request){
        GlobalValidator.checkIfRequestBodyIsEmpty(request);
        GlobalValidator.checkIfTwoFieldsAreEmpty(request.getUserId(), request.getNewField());
    }

}
