package code.logic.tamil.securityjwt.security;


import code.logic.tamil.securityjwt.entity.Role;
import code.logic.tamil.securityjwt.entity.User;
import code.logic.tamil.securityjwt.repository.UserRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    //secret key
    private static final SecretKey secretkey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // exception time
    private final int jwtExpirationMs = 86400000;
    private UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Generate Token
    public String generateToken(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Set<Role> roles = user.get().getRoles();

        //Add roles to the token
        return Jwts.builder().setSubject(username).claim("roles", roles.stream().map(role -> role.getName()).collect(Collectors.joining(",")
                .setIssuedAt(new Date()).setExpiration(new Date(new Date(new Date().getTime() + jwtExpirationMs)
                .setMonth(secretkey).compact());

    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(JwtUtil.secretkey).build().parseClaimsJws(token).getBody().getSubject();

    }

    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(token).getBody().get("roles", String.class);
        return Set.of(rolesString);
    }

    //Token Validation

    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secretkey).build().parseClaimsJws(token);
            return true;
        }catch (JwtException|IllegalArgumentException e){
            return false;
        }
    }
}
