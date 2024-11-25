package code.logic.tamil.securityjwt.controller;


import code.logic.tamil.securityjwt.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.user}")
    private String roleUser;

    //Endpoint to access user protected resources
    @GetMapping("/protected-data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token){
        if (token != null && token.startsWith("Bearer")){
            String jwtToken = token.substring(7);

            try{
                if (jwtUtil.isTokenValid(jwtToken)){
                    String username = jwtUtil.extractUsername(jwtToken); //extract username from the jwtToken

                    //extract roles from the jwt Token
                    Set<String> roles = jwtUtil.extractRoles(jwtToken);

                    if(roles.contains(roleAdmin)){
                        return ResponseEntity.ok("Welcome"+ username+"Here is the" + roles+"-specific data.");
                    } else if(roles.contains(roleUser)) {
                        return ResponseEntity.ok("Welcome" + username + "Here is the" + roles + "-specific data.");

                    }
                    else {
                        return ResponseEntity.status(403).body("Access Denied: You dont have the necessory role");
                    }
                }
            } catch(Exception ex){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing or invalid");
    }


}
