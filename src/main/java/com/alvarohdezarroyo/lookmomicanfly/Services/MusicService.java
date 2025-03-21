package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.DTO.MusicDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.ProductCategory;
import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.Music;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.MusicRepository;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers.ProductMapper;
import com.alvarohdezarroyo.lookmomicanfly.Validators.ProductValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    @Autowired
    private final MusicRepository musicRepository;
    private final ProductService productService;

    public MusicService(MusicRepository musicRepository, ProductService productService) {
        this.musicRepository = musicRepository;
        this.productService = productService;
    }

    public MusicDTO getMusicDTOById(String id){
        return ProductMapper.toMusicDTO(musicRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Music product id does not exist.")
        ));
    }

    @Transactional
    public Music saveMusic(MusicDTO musicDTO){
        final Music music= ProductMapper.toMusic(musicDTO);
        ProductValidator.checkIfCategoryIsCorrect(music.getCategory(), ProductCategory.MUSIC);
        productService.fillManufacturerAndColors(music,musicDTO);
        music.setId(musicRepository.save(music).getId());
        productService.saveProductColors(music);
        return music;
    }

}
