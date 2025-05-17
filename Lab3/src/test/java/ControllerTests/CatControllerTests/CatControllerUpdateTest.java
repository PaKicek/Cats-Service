package ControllerTests.CatControllerTests;

import org.junit.jupiter.api.Test;
import org.pakicek.lab3.Application;
import org.pakicek.lab3.Dtos.CatDto;
import org.pakicek.lab3.Dtos.Requests.CatRequest;
import org.pakicek.lab3.Dtos.PersonDto;
import org.pakicek.lab3.Enums.CatColor;
import org.pakicek.lab3.Services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.integration.json.SimpleJsonSerializer.toJson;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CatControllerUpdateTest {
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
    public void updateCatTest() throws Exception {
        CatRequest catRequest = new CatRequest();
        catRequest.setName("Name6");
        catRequest.setBirthdate(LocalDate.of(2001, 11, 11));
        catRequest.setBreed("Siamese");
        catRequest.setColor(CatColor.BLUE);
        catRequest.setOwnerId(1);
        this.mockMvc.perform(put("/api/cats/put/3").with(user("username").password("password").roles("USER", "ADMIN")).contentType(MediaType.APPLICATION_JSON).content(toJson(catRequest))).andExpect(status().isOk());
        verify(catService, times(1)).update(any(CatRequest.class), eq(3L));
    }
}
