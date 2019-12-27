package net.jaggerwang.sbip.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import graphql.kickstart.tools.boot.GraphQLJavaToolsAutoConfiguration;
import net.jaggerwang.sbip.entity.UserEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oembedler.moon.graphql.boot.GraphQLWebAutoConfiguration;
import com.oembedler.moon.graphql.boot.GraphQLWebsocketAutoConfiguration;

@SpringBootTest(properties = {"graphql.servlet.enabled=false"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = {GraphQLWebAutoConfiguration.class,
        GraphQLJavaToolsAutoConfiguration.class, GraphQLWebsocketAutoConfiguration.class})
public class UserRestApiTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerTest() throws Exception {
        var userEntity = UserEntity.builder().username("jaggerwang").password("123456").build();
        mvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userEntity))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value("jaggerwang"));
    }

    @Test
    void loginTest() throws Exception {
        var userEntity = UserEntity.builder().username("jaggerwang").password("123456").build();
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userEntity))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value("jaggerwang"));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void logoutTest() throws Exception {
        mvc.perform(get("/user/logout")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value("jaggerwang"));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void loggedTest() throws Exception {
        mvc.perform(get("/user/logged")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.username").value("jaggerwang"));
    }

    @WithUserDetails("jaggerwang")
    @Test
    void infoTest() throws Exception {
        mvc.perform(get("/user/info").param("id", "1")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("ok"))
                .andExpect(jsonPath("$.data.user.id").value(1));
    }
}
