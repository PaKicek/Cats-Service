package ServiceTests.PersonServiceTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pakicek.lab3.Entities.Person;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.pakicek.lab3.Services.PersonService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PersonServiceGetTest {
    @Mock
    private static PersonRepository personRepository = mock(PersonRepository.class);
    @Mock
    private static UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private static PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    @InjectMocks
    private static PersonService personService = new PersonService(personRepository, userRepository);
    @Test
    public void getPersonByIdTest () {
        Person person = new Person("name", LocalDate.of(1111, 11, 11));
        when(personRepository.findPersonById(anyLong())).thenReturn(person);
        verify(personRepository, times(0)).findPersonById(anyLong());
        personService.getById(1);
        verify(personRepository, times(1)).findPersonById(anyLong());
    }
}
