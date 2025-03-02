package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.PasswordUtils;

import java.nio.charset.StandardCharsets;

public class UserMapper {

    public static UserDTO toDTO(User user) throws Exception {
        final UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUserType(user.getUserType());
        userDTO.setId(user.getId());
        userDTO.setName(AESEncryptionUtil.decrypt(new String(user.getName(), StandardCharsets.UTF_8)));
        return userDTO;
    }
    public static User toUser(UserDTO userDTO) throws Exception {
        final User user=new User();
        user.setActive(true);
        user.setEmail(userDTO.getEmail());
        user.setUserType(userDTO.getUserType());
        user.setPassword(PasswordUtils.hashPassword(userDTO.getPassword()));
        user.setName(AESEncryptionUtil.encrypt(userDTO.getName()).getBytes());
        return user;
    }
}
