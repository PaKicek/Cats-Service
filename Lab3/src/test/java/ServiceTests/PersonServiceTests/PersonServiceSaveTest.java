package ServiceTests.PersonServiceTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pakicek.lab3.Dtos.Requests.PersonRequest;
import org.pakicek.lab3.Entities.Person;
import org.pakicek.lab3.Repositories.PersonRepository;
import org.pakicek.lab3.Repositories.UserRepository;
import org.pakicek.lab3.Services.PersonService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PersonServiceSaveTest {
    @Mock
    private static PersonRepository personRepository = mock(PersonRepository.class);
    @Mock
    private static UserRepository userRepository = mock(UserRepository.class);
    @Mock
    private static PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    @InjectMocks
    private static PersonService personService = new PersonService(personRepository, userRepository);
    @Test
    public void savePersonTest () {
        PersonRequest personRequest = new PersonRequest();
        Person person = new Person("name", LocalDate.of(1111, 11, 11));
        when(personRepository.save(any(Person.class))).thenReturn(person);
        verify(personRepository, times(0)).save(any(Person.class));
        personService.save(personRequest);
        verify(personRepository, times(1)).save(any(Person.class));
    }
}
