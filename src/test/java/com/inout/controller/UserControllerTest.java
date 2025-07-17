package com.inout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inout.dto.RegisterEmployeeDTO;
import com.inout.dto.UserDTO;
import com.inout.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void registerEmployee_shouldReturnUserDTO() throws Exception {
        RegisterEmployeeDTO input = new RegisterEmployeeDTO(
                "Alice", "alice@example.com", "pass1234", "IT", "Dev"
        );

        UserDTO response = new UserDTO(1L, "Alice", "alice@example.com", "EMPLOYEE");

        Mockito.when(userService.registerEmployee(any())).thenReturn(response);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"))
                .andExpect(jsonPath("$.role").value("EMPLOYEE"));
    }
}
