package fr.wildcodeschool.goodFather.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.wildcodeschool.goodFather.entities.User;
import fr.wildcodeschool.goodFather.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Value("${user.email}")
    private String email;

    @Value("${user.password}")
    private String password;

    @Value("${user.role}")
    private String role;

    @Value("${user.firstname}")
    private String firstName;

    @Value("${user.lastname}")
    private String lastName;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(userRepository.count() == 0) {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            User adminDefault = new User(email, encoder.encode(password), role, firstName, lastName);
            userRepository.save(adminDefault);
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
	}

}
