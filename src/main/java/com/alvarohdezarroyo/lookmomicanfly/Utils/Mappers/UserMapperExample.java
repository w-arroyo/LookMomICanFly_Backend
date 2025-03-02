package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.nio.charset.StandardCharsets;

@Mapper(componentModel = "spring")
public interface UserMapperExample {
/*
    UserMapperExample INSTANCE = Mappers.getMapper(UserMapperExample.class);

    @Mapping(target = "name", qualifiedByName = "encryptName")
    User toEntity(UserDTO userDTO);

    @Mapping(target = "name", qualifiedByName = "decryptName")
    UserDTO toDTO(User user);

    @Named("encryptName")
    default byte[] encryptName(String name) {
        try {
            String encrypted = AESEncryptionUtil.encrypt(name);
            return encrypted.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al cifrar el nombre", e);
        }
    }

    @Named("decryptName")
    default String decryptName(byte[] name) {
        try {
            String encryptedString = new String(name, StandardCharsets.UTF_8);
            return AESEncryptionUtil.decrypt(encryptedString);
        } catch (Exception e) {
            throw new RuntimeException("Error al descifrar el nombre", e);
        }
    }

 */
}
