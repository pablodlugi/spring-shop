package com.pablito.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablito.shop.domain.dao.User;
import com.pablito.shop.domain.dto.UserDto;
import com.pablito.shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //uruchamia testy ze springiem
@AutoConfigureMockMvc //daje beana o nazwie mockMVC dzięki któremu możemy wysyłać requesty na controllery
@ActiveProfiles("test") //uruchamia springa w profilu test
@TestPropertySource(locations = "classpath:application-test.yml")
//do testów zostanie zciągniety application-test.yml z folderu resources z testow
@Transactional //po kązdym teście robi rollback na bazie danych
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; //mapuje JSONA do obiektu javowego i odwrotnie

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserDto.builder()
                                .firstName("PABLO")
                                .lastName("JAMES")
                                .username("FISH")
                                .password("abcd")
                                .confirmedPassword("abcd")
                                .email("johan@onet.pl")
                                .build()
                        )))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("PABLO"))
                .andExpect(jsonPath("$.lastName").value("JAMES"))
                .andExpect(jsonPath("$.username").value("FISH"))
                .andExpect(jsonPath("$.email").value("johan@onet.pl"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.confirmedPassword").doesNotExist());
    }

    @Test
    void shouldNotSaveUserWithInvalidateBody() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserDto())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", containsInAnyOrder("username", "lastName", "firstName", "email")))
                .andExpect(jsonPath("$[*].message", containsInAnyOrder("Pole USERNAME nie może być puste"
                        , "must not be blank", "must not be blank", "must not be blank")));
    }

    @Test
    void shouldNotSaveUserWithNotEqualPassword() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserDto.builder()
                                .firstName("PABLO")
                                .lastName("JAMES")
                                .username("FISH")
                                .password("abcd")
                                .confirmedPassword("dupa")
                                .email("johan@onet.pl")
                                .build()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("saveUser.user: Passwords should be the same"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
        //zmockowany użytkownik z rolą admina
    void shouldGetAllUsers() throws Exception {

        User user1 = userRepository.save(User.builder()
                .firstName("PABLO")
                .lastName("JAMES")
                .username("FISH")
                .password("abcd")
                .email("johan@onet.pl")
                .build());

        User user2 = userRepository.save(User.builder()
                .firstName("TOMEK")
                .lastName("SOLNIK")
                .username("ROSÓŁ")
                .password("abcd")
                .email("grgamel@onet.pl")
                .build());

        mockMvc.perform(get("/api/v1/users")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[*].id", containsInAnyOrder(user1.getId().intValue(), user2.getId().intValue())))
                .andExpect(jsonPath("$.content[*].firstName", containsInAnyOrder("PABLO", "TOMEK")))
                .andExpect(jsonPath("$.content[*].lastName", containsInAnyOrder("JAMES", "SOLNIK")))
                .andExpect(jsonPath("$.content[*].username", containsInAnyOrder("FISH", "ROSÓŁ")))
                .andExpect(jsonPath("$.content[*].email", containsInAnyOrder("johan@onet.pl", "grgamel@onet.pl")))
                .andExpect(jsonPath("$.content[*].password").doesNotExist());
    }

    @Test
    void shouldNotGetUsersWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/api/v1/users")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUsersById() throws Exception {

        User user1 = userRepository.save(User.builder()
                .firstName("PABLO")
                .lastName("JAMES")
                .username("FISH")
                .password("abcd")
                .email("johan@onet.pl")
                .build());

        mockMvc.perform(get("/api/v1/users/" + user1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("PABLO"))
                .andExpect(jsonPath("$.lastName").value("JAMES"))
                .andExpect(jsonPath("$.username").value("FISH"))
                .andExpect(jsonPath("$.email").value("johan@onet.pl"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.confirmedPassword").doesNotExist());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotFindUserById() throws Exception {

        mockMvc.perform(get("/api/v1/users/13"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldDoesntAccessUserWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/api/v1/users/13"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser(username = "abcd@onet.pl")
    void shouldGetItself() throws Exception {

        User user1 = userRepository.save(User.builder()
                .firstName("PABLO")
                .lastName("JAMES")
                .username("FISH")
                .password("abcd")
                .email("abcd@onet.pl")
                .build());

        mockMvc.perform(get("/api/v1/users/" + user1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("PABLO"))
                .andExpect(jsonPath("$.lastName").value("JAMES"))
                .andExpect(jsonPath("$.username").value("FISH"))
                .andExpect(jsonPath("$.email").value("abcd@onet.pl"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.confirmedPassword").doesNotExist());
    }
}
