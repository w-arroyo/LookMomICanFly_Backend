package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;

import java.nio.charset.StandardCharsets;

public class AddressMapper {

    public static AddressDTO toDTO(Address address) throws Exception {
        final AddressDTO addressDTO=new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setFullName(AESEncryptionUtil.decrypt(new String(address.getFullName(), StandardCharsets.UTF_8)));
        addressDTO.setStreet(AESEncryptionUtil.decrypt(new String(address.getStreet(), StandardCharsets.UTF_8)));
        addressDTO.setCity(AESEncryptionUtil.decrypt(new String(address.getCity(), StandardCharsets.UTF_8)));
        addressDTO.setCountry(AESEncryptionUtil.decrypt(new String(address.getCountry(), StandardCharsets.UTF_8)));
        addressDTO.setZipCode(AESEncryptionUtil.decrypt(new String(address.getZipCode(), StandardCharsets.UTF_8)));
        return addressDTO;
    }

    public static Address toEntity(AddressDTO addressDTO, User user) throws Exception {
        final Address address=new Address();
        address.setUser(user);
        address.setActive(true);
        address.setFullName(AESEncryptionUtil.encrypt(addressDTO.getFullName()).getBytes());
        address.setStreet(AESEncryptionUtil.encrypt(addressDTO.getStreet()).getBytes());
        address.setZipCode(AESEncryptionUtil.encrypt(addressDTO.getZipCode()).getBytes());
        address.setCity(AESEncryptionUtil.encrypt(addressDTO.getCity()).getBytes());
        address.setCountry(AESEncryptionUtil.encrypt(addressDTO.getCountry()).getBytes());
        return address;
    }

}
