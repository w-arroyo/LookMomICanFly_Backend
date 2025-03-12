package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.MusicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Models.Music;
import com.alvarohdezarroyo.lookmomicanfly.Services.MusicService;
import com.alvarohdezarroyo.lookmomicanfly.Services.ProductService;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
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
    private final ProductService productService;

    public MusicController(MusicService musicService, ProductService productService) {
        this.musicService = musicService;
        this.productService = productService;
    }

    @GetMapping("/get/")
    public ResponseEntity<MusicDTO> getMusicDTOById(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.OK).body(musicService.getMusicDTOById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> saveMusic(@RequestBody MusicDTO musicDTO){
        //remember to validate user is ADMIN to allow this request
        GlobalValidator.checkIfRequestBodyIsEmpty(musicDTO);
        ProductValidator.checkIfProductFieldsAreEmpty(musicDTO,musicDTO.getFormat(),"format");
        final Music music= ProductMapper.toMusic(musicDTO);
        ProductValidator.checkIfCategoryIsCorrect(music.getCategory(), ProductCategory.MUSIC);
        productService.fillManufacturerAndColors(music,musicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",musicService.saveMusic(music)));
    }

}
