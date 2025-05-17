package ControllerTests.PersonControllerTests;

import org.junit.jupiter.api.Test;
import org.pakicek.lab3.Application;
import org.pakicek.lab3.Dtos.PersonDto;
import org.pakicek.lab3.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PersonControllerDeleteTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PersonService personService;
    private List<PersonDto> listOfPersons = List.of(
            new PersonDto("Name", LocalDate.of(2001, 11, 11)),
            new PersonDto("Name2", LocalDate.of(2002, 11, 11)),
            new PersonDto("Name3", LocalDate.of(2003, 11, 11)),
            new PersonDto("Name4", LocalDate.of(2004, 11, 11)),
            new PersonDto("Name5", LocalDate.of(2005, 11, 11))
    );
    @Test
    public void deletePersonByIdTest() throws Exception {
        this.mockMvc.perform(delete("/api/persons/delete/3").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).deleteById(3);
    }
    @Test
    public void deleteAllPersonsTest() throws Exception {
        this.mockMvc.perform(delete("/api/persons/deleteall").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).deleteAll();
    }
}
