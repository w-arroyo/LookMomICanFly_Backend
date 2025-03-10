package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.MusicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/get/")
    public ResponseEntity<MusicDTO> getMusicDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusicDTOById(id));
    }
}
