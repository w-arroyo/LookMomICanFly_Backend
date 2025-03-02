package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Models.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.PasswordUtils;

import java.nio.charset.StandardCharsets;

public class UserMapper {

    public static UserDTO toDTO(User user) throws Exception {
        final UserDTO userDTO=new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(AESEncryptionUtil.decrypt(new String(user.getName(), StandardCharsets.UTF_8)));
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static User toUser(UserDTO userDTO, UserType userType) throws Exception {
        final User user=new User();
        user.setActive(true);
        user.setEmail(userDTO.getEmail());
        user.setName(AESEncryptionUtil.encrypt(userDTO.getName()).getBytes());
        user.setUserType(userType);
        user.setPassword(PasswordUtils.hashPassword(userDTO.getPassword()));
        return user;
    }

}
