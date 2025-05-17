package ControllerTests.CatControllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.pakicek.lab3.Application;
import org.pakicek.lab3.Dtos.CatDto;
import org.pakicek.lab3.Dtos.PersonDto;
import org.pakicek.lab3.Enums.CatColor;
import org.pakicek.lab3.Services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CatControllerGetTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CatService catService;
    private List<PersonDto> listOfPersons = List.of(
            new PersonDto("Name", LocalDate.of(2001, 11, 11)),
            new PersonDto("Name2", LocalDate.of(2002, 11, 11)),
            new PersonDto("Name3", LocalDate.of(2003, 11, 11)),
            new PersonDto("Name4", LocalDate.of(2004, 11, 11)),
            new PersonDto("Name5", LocalDate.of(2005, 11, 11))
    );
    private List<CatDto> listOfCats = List.of(
            new CatDto("Name", LocalDate.of(2001, 11, 11), "Siamese", CatColor.AMBER, listOfPersons.get(0)),
            new CatDto("Name2", LocalDate.of(2002, 11, 11), "Siamese", CatColor.WHITE, listOfPersons.get(0)),
            new CatDto("Name3", LocalDate.of(2003, 11, 11), "Siamese", CatColor.BLACK, listOfPersons.get(1)),
            new CatDto("Name4", LocalDate.of(2004, 11, 11), "Siamese", CatColor.RED, listOfPersons.get(1)),
            new CatDto("Name5", LocalDate.of(2005, 11, 11), "Siamese", CatColor.SILVER, listOfPersons.get(1)),
            new CatDto("Name6", LocalDate.of(2006, 11, 11), "Siamese", CatColor.BLUE, listOfPersons.get(2))
    );
    @Test
    public void getAllCatsTest() throws Exception {
        Mockito.when(this.catService.getAll()).thenReturn(listOfCats);
        this.mockMvc.perform(get("/api/cats/getall").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Name"))
                .andExpect(jsonPath("$[1].name").value("Name2"))
                .andExpect(jsonPath("$[2].name").value("Name3"))
                .andExpect(jsonPath("$[3].name").value("Name4"))
                .andExpect(jsonPath("$[4].name").value("Name5"))
                .andExpect(jsonPath("$[5].name").value("Name6"));
        verify(catService, times(1)).getAll();
    }
    @Test
    public void getCatByIdTest() throws Exception {
        Mockito.when(this.catService.getById(3)).thenReturn(listOfCats.get(2));
        this.mockMvc.perform(get("/api/cats/get/3").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name3"));
        verify(catService, times(1)).getById(3);
    }
    @Test
    public void getByNameStartingWithTest() throws Exception {
        this.mockMvc.perform(get("/api/cats/get/name/Name").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getByNameStartingWith("Name");
    }
    @Test
    public void getByBirthdateBetweenTest() throws Exception {
        this.mockMvc.perform(get("/api/cats/get/birthdate/2000-11-11/2001-11-11").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getByBirthdateBetween(LocalDate.of(2000, 11, 11), LocalDate.of(2001, 11, 11));
    }
    @Test
    public void getByBreed() throws Exception {
        this.mockMvc.perform(get("/api/cats/get/breed/Breed").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getByBreed("Breed");
    }
    @Test
    public void getByCatColor() throws Exception {
        this.mockMvc.perform(get("/api/cats/get/color/BLACK").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getByCatColor(CatColor.BLACK);
    }
    @Test
    public void getByOwnerId() throws Exception {
        this.mockMvc.perform(get("/api/cats/get/owner/1").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getByOwnerId(1);
    }
    @Test
    public void getFirstSortedByName() throws Exception {
        this.mockMvc.perform(get("/api/cats/getall/sorted/name/5").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getFirstSortedByName(5);
    }
    @Test
    public void getFirstSortedByBirthDate() throws Exception {
        this.mockMvc.perform(get("/api/cats/getall/sorted/birthdate/5").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getFirstSortedByBirthDate(5);
    }
    @Test
    public void getLastSortedByBirthDate() throws Exception {
        this.mockMvc.perform(get("/api/cats/getall/lastsorted/birthdate/5").with(user("username").password("password").roles("USER", "ADMIN"))).andExpect(status().isOk());
        verify(catService, times(1)).getLastSortedByBirthDate(5);
    }
}
