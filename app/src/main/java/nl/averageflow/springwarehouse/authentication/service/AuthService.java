package nl.averageflow.springwarehouse.authentication.service;

import nl.averageflow.springwarehouse.user.enums.UserRole;
import nl.averageflow.springwarehouse.user.model.Role;
import nl.averageflow.springwarehouse.user.model.User;
import nl.averageflow.springwarehouse.user.repository.RoleRepository;
import nl.averageflow.springwarehouse.user.repository.UserRepository;
import nl.averageflow.springwarehouse.authentication.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> authenticateUser(final String email, final String password) {
        final Authentication authToken = new UsernamePasswordAuthenticationToken(email, password);

        try {
            final Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User authenticated successfully!", HttpStatus.OK);
        } catch (final Exception e) {
            return new ResponseEntity<>("User could not be authenticated!", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<String> registerUser(final RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        final User user = new User();

        user.setItemName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        final Optional<Role> role = this.roleRepository.findByItemName(UserRole.READ_ONLY);

        if (role.isEmpty()) {
            return new ResponseEntity<>("No suitable roles found for user! Check your database for roles!", HttpStatus.BAD_REQUEST);
        }

        user.setRole(role.get());

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
