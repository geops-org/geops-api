package com.geopslabs.geops.api.core.integration.tests;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.TestPropertySource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSignUpSuccess() throws Exception {

        String requestBody = """
    {
        "name":"Natalia",
        "email":"natalia@test.com",
        "phone":"999999999",
        "password":"123456",
        "role":"USER",
        "plan":"FREE"
    }
    """;

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testSignUpWithoutName() throws Exception {

        String requestBody = """
    {
        "email":"natalia@test.com",
        "phone":"999999999",
        "password":"123456",
        "role":"USER",
        "plan":"FREE"
    }
    """;

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSignUpWithoutPassword() throws Exception {

        String requestBody = """
    {
        "name":"Natalia",
        "email":"natalia@test.com",
        "phone":"999999999",
        "role":"USER",
        "plan":"FREE"
    }
    """;

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSignUpWithEmptyBody() throws Exception {

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
