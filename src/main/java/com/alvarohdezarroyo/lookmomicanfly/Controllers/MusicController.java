package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.MusicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.AuthService;
import com.alvarohdezarroyo.lookmomicanfly.Services.MusicService;
import com.alvarohdezarroyo.lookmomicanfly.Validators.GlobalValidator;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products/music")
public class MusicController {

    private final MusicService musicService;
    private final AuthService authService;

    public MusicController(MusicService musicService, AuthService authService) {
        this.musicService = musicService;
        this.authService = authService;
    }

    @GetMapping("/get/")
    public ResponseEntity<MusicDTO> getMusicDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusicDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveMusic(@RequestBody MusicDTO musicDTO){
        authService.checkIfAUserIsAdmin();
        GlobalValidator.checkIfRequestBodyIsEmpty(musicDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(musicDTO,musicDTO.getFormat(),"format");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",musicService.saveMusic(musicDTO)));
    }

}
