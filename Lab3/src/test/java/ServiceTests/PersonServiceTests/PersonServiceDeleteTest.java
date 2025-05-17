package ServiceTests.PersonServiceTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.pakicek.lab3.Services.PersonService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PersonServiceDeleteTest {
    @Mock
    private static PersonRepository personRepository = mock(PersonRepository.class);
    @Mock
    private static UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private static PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    @InjectMocks
    private static PersonService personService = new PersonService(personRepository, userRepository);
    @Test
    public void deleteCatByIdTest () {
        verify(personRepository, times(0)).deletePersonById(anyLong());
        personService.deleteById(1);
        verify(personRepository, times(1)).deletePersonById(anyLong());
    }
    @Test
    public void deleteAllTest () {
        verify(personRepository, times(0)).deleteAll();
        personService.deleteAll();
        verify(personRepository, times(1)).deleteAll();
    }
}
