package ControllerTests.PersonControllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PersonControllerGetTests {
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
    public void getAllPersonsTest() throws Exception {
        Mockito.when(this.personService.getAll()).thenReturn(listOfPersons);
        this.mockMvc.perform(get("/api/persons/getall").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name"))
                .andExpect(jsonPath("$[1].name").value("Name2"))
                .andExpect(jsonPath("$[2].name").value("Name3"))
                .andExpect(jsonPath("$[3].name").value("Name4"))
                .andExpect(jsonPath("$[4].name").value("Name5"));
    }
    @Test
    public void getPersonByIdTest() throws Exception {
        Mockito.when(this.personService.getById(3)).thenReturn(listOfPersons.get(2));
        this.mockMvc.perform(get("/api/persons/get/3").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name3"));
    }
    @Test
    public void getByNameStartingWithTest() throws Exception {
        this.mockMvc.perform(get("/api/persons/get/name/Name").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).getByNameStartingWith("Name");
    }
    @Test
    public void getByBirthdateBetweenTest() throws Exception {
        this.mockMvc.perform(get("/api/persons/get/birthdate/2000-11-11/2001-11-11").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).getByBirthdateBetween(LocalDate.of(2000, 11, 11), LocalDate.of(2001, 11, 11));
    }
    @Test
    public void getFirstSortedByName() throws Exception {
        this.mockMvc.perform(get("/api/persons/getall/sorted/name/5").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).getFirstSortedByName(5);
    }
    @Test
    public void getFirstSortedByBirthDate() throws Exception {
        this.mockMvc.perform(get("/api/persons/getall/sorted/birthdate/5").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).getFirstSortedByBirthDate(5);
    }
    @Test
    public void getLastSortedByBirthDate() throws Exception {
        this.mockMvc.perform(get("/api/persons/getall/lastsorted/birthdate/5").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(personService, times(1)).getLastSortedByBirthDate(5);
    }
}
