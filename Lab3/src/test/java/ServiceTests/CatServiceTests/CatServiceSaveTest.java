package ServiceTests.CatServiceTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pakicek.lab3.Dtos.Requests.CatRequest;
import org.pakicek.lab3.Entities.Cat;
import org.pakicek.lab3.Entities.Person;
import org.pakicek.lab3.Enums.CatColor;
import org.pakicek.lab3.Repositories.CatRepository;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.pakicek.lab3.Services.CatService;
import org.pakicek.lab3.Services.PersonService;
import org.pakicek.lab3.Services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class CatServiceSaveTest {
    @Mock
    private static CatRepository catRepository = mock(CatRepository.class);
    @Mock
    private static PersonRepository personRepository = mock(PersonRepository.class);
    @Mock
    private static UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private static UserService userService = mock(UserService.class);
    @Mock
    private static PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    @InjectMocks
    private static CatService catService = new CatService(catRepository, personRepository, userRepository, userService);
    @InjectMocks
    private static PersonService personService = new PersonService(personRepository, userRepository);
    @Test
    public void saveCatTest () {
        CatRequest catRequest = new CatRequest();
        Person person = new Person("name", LocalDate.of(1111, 11, 11));
        Cat cat = new Cat("name", LocalDate.of(1111, 11, 11), "breed", CatColor.WHITE, person);
        when(catRepository.findCatById(anyLong())).thenReturn(cat);
        when(personRepository.findPersonById(anyLong())).thenReturn(person);
        when(catRepository.save(any(Cat.class))).thenReturn(cat);
        verify(catRepository, times(0)).save(any(Cat.class));
        catService.save(catRequest);
        verify(catRepository, times(1)).save(any(Cat.class));
    }
}
