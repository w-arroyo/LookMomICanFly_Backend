package com.alvarohdezarroyo.lookmomicanfly.controllers;

import com.alvarohdezarroyo.lookmomicanfly.Controllers.UserController;
import com.alvarohdezarroyo.lookmomicanfly.DTO.EmailDetailsDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.RequestDTO.ChangeUserFieldsRequestDTO;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import com.alvarohdezarroyo.lookmomicanfly.test_configuration.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private UserValidator userValidator;
    @MockitoBean
    private PostService postService;
    @MockitoBean
    private AddressService addressService;
    @MockitoBean
    private BankAccountService bankAccountService;
    @MockitoBean
    private PhoneNumberService phoneNumberService;
    @MockitoBean
    private EmailSenderService emailSenderService;

    final String USER_ID = "userId";
    final String ENDPOINT_BASE = "/api/users/";

    @Test
    public void deactivateAccountIsSuccessful() throws Exception {
        final User mockedUser = new User();
        mockedUser.setId(USER_ID);
        when(userValidator.returnUserById(USER_ID))
                .thenReturn(mockedUser);
        final ResultActions resultActions = mockMvc.perform(put(ENDPOINT_BASE + "deactivate/")
                .param("userId", USER_ID)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Account successfully deactivated."));
        verify(userService).deactivateAccount(USER_ID);
        verify(postService).deactivateAllUserPosts(USER_ID);
        verify(addressService).deactivateAllUserAddresses(USER_ID);
        verify(bankAccountService).deactivateAllUserBankAccounts(USER_ID);
        verify(phoneNumberService).deactivateAllUserPhoneNumbers(USER_ID);
        verify(emailSenderService).sendEmailWithNoAttachment(any(EmailDetailsDTO.class));
    }

    /*
    @Test
    public void getAllUserAddressesIsSuccessful() throws Exception{
        final String userId= "userId";
        final String addressId="12345";
        final Address address=new Address();
        address.setId(addressId);
        final List<Address> addresses=List.of(address);
        final AddressDTO[] addressesDTO= AddressMapper.addressListToAddressDTOArray(addresses);
        when(userService.getUserAddresses(userId)).thenReturn(addresses);
        final ResultActions resultActions= mockMvc.perform(get("/api/users/addresses/")
                .param("userId",userId)
                .contentType(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].id").value(addressId));
        verify(authService).checkFraudulentRequest(userId);
        verify(userService).getUserAddresses(userId);
    }
    */

    @Test
    public void changeUserEmailWasSuccessful() throws Exception {
        final ChangeUserFieldsRequestDTO changeUserFieldsRequestDTO = new ChangeUserFieldsRequestDTO();
        changeUserFieldsRequestDTO.setUserId(USER_ID);
        changeUserFieldsRequestDTO.setNewField("newEmail");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestBodyAsJson = objectMapper.writeValueAsString(changeUserFieldsRequestDTO);
        final ResultActions resultActions = mockMvc.perform(put(ENDPOINT_BASE + "changeEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Email address updated successfully."));
        verify(authService).checkFraudulentRequest(USER_ID);
        verify(userService).changeEmail(changeUserFieldsRequestDTO.getUserId(), changeUserFieldsRequestDTO.getNewField());
        verify(emailSenderService).sendEmailWithNoAttachment(any(EmailDetailsDTO.class));
    }

    @Test
    public void changeUserEmailWithEmptyBody() throws Exception {
        final ResultActions resultActions = mockMvc.perform(put(ENDPOINT_BASE + "changeEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));
        resultActions.andExpect(status().isBadRequest());
        verify(authService, never()).checkFraudulentRequest(any());
        verify(userService, never()).changeEmail(any(), any());
        verify(emailSenderService, never()).sendEmailWithNoAttachment(any(EmailDetailsDTO.class));
    }

}
