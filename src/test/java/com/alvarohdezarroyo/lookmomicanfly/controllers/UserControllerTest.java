package com.alvarohdezarroyo.lookmomicanfly.controllers;

import com.alvarohdezarroyo.lookmomicanfly.Controllers.UserController;
import com.alvarohdezarroyo.lookmomicanfly.DTO.EmailDetailsDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Services.*;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import com.alvarohdezarroyo.lookmomicanfly.test_configuration.TestSecurityConfig;
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

    @Test
    public void deactivateAccountIsSuccessful() throws Exception {
        final String userId = "developingMyFirstTest";
        final User mockedUser = new User();
        mockedUser.setId(userId);
        when(userValidator.returnUserById(userId))
                .thenReturn(mockedUser);
        final ResultActions resultActions = mockMvc.perform(put("/api/users/deactivate/")
                .param("userId", userId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Account successfully deactivated."));
        verify(userService).deactivateAccount(userId);
        verify(postService).deactivateAllUserPosts(userId);
        verify(addressService).deactivateAllUserAddresses(userId);
        verify(bankAccountService).deactivateAllUserBankAccounts(userId);
        verify(phoneNumberService).deactivateAllUserPhoneNumbers(userId);
        verify(emailSenderService).sendEmailWithNoAttachment(any(EmailDetailsDTO.class));
    }

}
