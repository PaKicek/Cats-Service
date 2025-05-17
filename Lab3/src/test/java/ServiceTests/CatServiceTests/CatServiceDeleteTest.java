package ServiceTests.CatServiceTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pakicek.lab3.Repositories.CatRepository;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.pakicek.lab3.Services.CatService;
import org.pakicek.lab3.Services.PersonService;
import org.pakicek.lab3.Services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class CatServiceDeleteTest {
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
    public void deleteCatByIdTest () {
        verify(catRepository, times(0)).deleteCatById(anyLong());
        catService.deleteById(1);
        verify(catRepository, times(1)).deleteCatById(anyLong());
    }
    @Test
    public void deleteAllTest () {
        verify(catRepository, times(0)).deleteAll();
        catService.deleteAll();
        verify(catRepository, times(1)).deleteAll();
    }
}
