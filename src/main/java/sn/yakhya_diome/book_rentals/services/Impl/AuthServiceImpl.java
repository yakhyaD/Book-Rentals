package sn.yakhya_diome.book_rentals.services.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.yakhya_diome.book_rentals.models.ERole;
import sn.yakhya_diome.book_rentals.models.Role;
import sn.yakhya_diome.book_rentals.models.User;
import sn.yakhya_diome.book_rentals.payload.request.LoginRequest;
import sn.yakhya_diome.book_rentals.payload.request.RegisterRequest;
import sn.yakhya_diome.book_rentals.payload.response.JwtResponse;
import sn.yakhya_diome.book_rentals.repository.RoleRepository;
import sn.yakhya_diome.book_rentals.repository.UserRepository;
import sn.yakhya_diome.book_rentals.security.jwt.JwtUtils;
import sn.yakhya_diome.book_rentals.services.AuthService;
import sn.yakhya_diome.book_rentals.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest registerBody) {
        if(userRepository.existsByUsername(registerBody.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username is already taken!");
        }
        if(userRepository.existsByEmail(registerBody.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already exists!");
        }
        if(!registerBody.getPassword().equals(registerBody.getPasswordConfirm())){
            return ResponseEntity
                    .badRequest()
                    .body("Password does not match");
        }
        User user = User.builder()
                .username(registerBody.getUsername())
                .email(registerBody.getEmail())
                .location(registerBody.getLocation())
                .phoneNumber(registerBody.getPhoneNumber())
                .password(passwordEncoder().encode(registerBody.getPassword()))
                .build();

        Set<String> requestRoles = registerBody.getRoles();
        Set<Role> roles = new HashSet<>();

        if(requestRoles == null){
            Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                    () -> new IllegalStateException("Error: Role is not found.")
            );
            roles.add(role);
        }
        else{
            requestRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(
                                () -> new IllegalStateException("Error: Role is not found.")
                        );
                        roles.add(adminRole);
                    }
                    case "creator" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_CREATOR).orElseThrow(
                                () -> new IllegalStateException("Error: Role is not found.")
                        );
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(
                                () -> new IllegalStateException("Error: Role is not found.")
                        );
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginBody) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginBody.getUsername(),
                        loginBody.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwtToken)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build()
            );
    }
}
