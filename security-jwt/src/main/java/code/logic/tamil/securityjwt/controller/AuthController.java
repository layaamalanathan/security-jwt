package code.logic.tamil.securityjwt.controller;

import code.logic.tamil.securityjwt.dto.RegisterRequest;
import code.logic.tamil.securityjwt.entity.Role;
import code.logic.tamil.securityjwt.entity.User;
import code.logic.tamil.securityjwt.repository.RoleRepository;
import code.logic.tamil.securityjwt.repository.UserRepository;
import code.logic.tamil.securityjwt.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager1, JwtUtil jwtUtil1, UserRepository userRepository1, RoleRepository roleRepository1, PasswordEncoder passwordEncoder){

        this.authenticationManager = authenticationManager1;
        this.jwtUtil = jwtUtil1;
        this.userRepository = userRepository1;
        this.roleRepository = roleRepository1;
        this.passwordEncoder = passwordEncoder;
    }

    // Register user API
    @PostMapping("/register") //Register endpoint http://localhost:8080/auth/register
    public ResponseEntity<String> register (@RequestBody RegisterRequest registerRequest){

        // Check if username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());

        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());
        newUser.setPassword(encodePassword);
        System.out.println("EncodePassword:"+ encodePassword);

        // convert role names to role entities and assign to user

        Set<Role> roles = new HashSet<>();
        for(String roleName: registerRequest.getRoles()){
            Role role = roleRepository.findByName(roleName).orElseThrow(); new RuntimeException("Role not found: "+ roleName);
            roles.add(role);
        }

        newUser.setRoles(roles);
        userRepository.save(newUser);
        return ResponseEntity.ok("User Registered Successfully");
    }

    //Login API - //Login endpoint http://localhost:8080/auth/login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        }
        catch(Exception e){
            System.out.println("Exception:"+e);

        }
        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(token);
    }
}
