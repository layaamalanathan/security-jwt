package code.logic.tamil.securityjwt.service;

import code.logic.tamil.securityjwt.entity.User;
import code.logic.tamil.securityjwt.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
private final UserRepository userRepository;

public CustomUserDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found:"+username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), (String) user.getPassword(),user.getRoles().stream().map
                (role -> new SimpleGrantedAuthority((String) role.getName())).collect(Collectors.toList()));
    }
}
