package com.server.mock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mock.Service.impl.UserServiceImpl;
import com.server.mock.dto.LoginBody;
import com.server.mock.dto.LoginResponse;
import com.server.mock.dto.RegistrationBody;
import com.server.mock.exception.UserAlreadyExistsException;
import com.server.mock.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserServiceImpl userService;

    private RegistrationBody registrationBody;

    private LoginBody loginBody;

    private LoginResponse loginResponse;
    private User user;

    @BeforeEach
    void setUp() {
        registrationBody = new RegistrationBody("name", "email@gmail.com", "123xyz", "fName", "lName", "9299292929", true, "str");
        user = new User(1L, "name", "fName", "lName", "email.com", "1818181818189", true, "xyz", "123xyz", null, null);
        loginBody = new LoginBody("userName", "pass123");
        loginResponse = new LoginResponse("token");
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void registerUser() throws Exception {
//        doReturn(user).when(userService).registerUser(any());
        when(userService.registerUser(registrationBody)).thenReturn(user);
        String content = new ObjectMapper().writeValueAsString(registrationBody);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.userName", Matchers.is(registrationBody.getUserName())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testRegisterUser_UserAlreadyExistsException() throws Exception {

        String content = new ObjectMapper().writeValueAsString(registrationBody);

        when(userService.registerUser(registrationBody))
                .thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isConflict());
    }

    @Test
    void testLoginUser_JwtNotNull() throws Exception {
        when(userService.logIn(loginBody)).thenReturn("token");
        String content = new ObjectMapper().writeValueAsString(loginBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.jwt", Matchers.is(loginResponse.getJwt())))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testLoginUser_JwtNull() throws Exception {

        String content = new ObjectMapper().writeValueAsString(loginBody);

        when(userService.logIn(loginBody)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCurrentUser() throws Exception {

        Principal principal = new UsernamePasswordAuthenticationToken(user.getUserName(), null);
        when(userService.getName(user.getUserName())).thenReturn(user);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/current_user").principal(principal))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
//                .andExpect(MockMvcResultMatchers.jsonPath("$userName").value(user.getUserName()));
    }
}