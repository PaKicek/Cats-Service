package org.pakicek.webgateway.Services;

import org.pakicek.webgateway.Dtos.AdminDto;
import org.pakicek.webgateway.Dtos.PersonDto;
import org.pakicek.webgateway.Dtos.Requests.AdminRequest;
import org.pakicek.webgateway.Dtos.Requests.UserRequest;
import org.pakicek.webgateway.Dtos.UserDto;
import org.pakicek.webgateway.Entities.Admin;
import org.pakicek.webgateway.Entities.User;
import org.pakicek.webgateway.Repositories.AdminRepository;
import org.pakicek.webgateway.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = bCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, AdminRepository adminRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public UserDetails register(UserRequest userRequest) {
        User founduser = userRepository.findUserByUsername(userRequest.getUsername());
        if (founduser != null) {throw new UsernameNotFoundException("User with username " + userRequest.getUsername() + " already exists");}
        PersonDto person = new PersonDto(userRequest.getName(), userRequest.getBirthdate());
        kafkaTemplate.send("person-save-topic", person);
        User user = new User(userRequest.getUsername(), bCryptPasswordEncoder.encode(userRequest.getPassword()), person);
        return userRepository.save(user);
    }
    public UserDetails register(AdminRequest adminRequest) {
        Admin foundadmin = adminRepository.findAdminByUsername(adminRequest.getUsername());
        if (foundadmin != null) {throw new UsernameNotFoundException("Admin with username " + adminRequest.getUsername() + " already exists");}
        Admin admin = new Admin(adminRequest.getUsername(), bCryptPasswordEncoder.encode(adminRequest.getPassword()));
        return adminRepository.save(admin);
    }
    public List<UserDto> getAllUsers() {
        Iterable<User> list = userRepository.findAll();
        List<UserDto> dtolist = new ArrayList<>();
        for (User user : list) {
            dtolist.add(new UserDto(user));
        }
        return dtolist;
    }
    public List<AdminDto> getAllAdmins() {
        Iterable<Admin> list = adminRepository.findAll();
        List<AdminDto> dtolist = new ArrayList<>();
        for (Admin admin : list) {
            dtolist.add(new AdminDto(admin));
        }
        return dtolist;
    }
    @Transactional
    public void deleteUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        Person person = user.getOwner();
        personRepository.delete(person);
        userRepository.delete(user);
    }
    @Transactional
    public void deleteAdminByUsername(String username) {
        Admin admin = adminRepository.findAdminByUsername(username);
        adminRepository.delete(admin);
    }
    public boolean isOwnerIdOwnedByUser(String username, long userId) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {return false;}
        return user.getId() == userId;
    }
    public boolean isCatOwnedByUser(String username, long catId) {
        User user = userRepository.findUserByUsername(username);
        Cat cat = catRepository.findCatById(catId);
        if (cat == null || user == null) {return false;}
        System.out.println(cat.getOwner().getId() + " " + user.getId());
        return cat.getOwner().getId() == user.getOwner().getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails admin = adminRepository.findAdminByUsername(username);
        UserDetails user = userRepository.findUserByUsername(username);
        if (admin == null && user == null) {throw new UsernameNotFoundException("Admin with username " + username + " does not exist");}
        if (admin == null) return user;
        return admin;
    }
}
