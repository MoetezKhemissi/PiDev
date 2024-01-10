package com.example.Project.Services;

import com.example.Project.Config.JwtUtil;
import com.example.Project.DTO.JwtRequest;
import com.example.Project.DTO.JwtResponse;
import com.example.Project.entities.User;
import com.example.Project.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service

public class JwtService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;


    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtUtil jwtUtil;




    public String createJwtToken(JwtRequest jwtRequest) throws Exception {
        String Username = jwtRequest.getUsername();
        String Password = jwtRequest.getPassword();
        authenticate(Username, Password);

        UserDetails userDetails = loadUserByUsername(Username);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(Username);
        JwtResponse jwtResponse = new JwtResponse(user, newGeneratedToken);
        if (user != null && user.getIsVerified() == 1) {
            return (jwtResponse.getUser().getUsername() + " Connected  with role " +
                    jwtResponse.getUser().getUserJob() + " and ID: " +
                    jwtResponse.getUser().getIdUser() + " \n" + jwtResponse.getJwtToken());
        }
        else if (user.getIsVerified() == 0) {
            throw new UserNotVerifiedException("User is not verified");
        }
        else return ("Incorrect username or password");

        }



    @Override
        public UserDetails loadUserByUsername(String Username) throws
        UsernameNotFoundException {
            User user = userRepository.findByUsername(Username);

            if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        getAuthority(user)
                );
            } else {
                throw new UsernameNotFoundException("User not found with username: " + Username);
            }
        }

        private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }


    private void authenticate(String Username, String Password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(Username, Password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    public class UserNotVerifiedException extends RuntimeException {
        public UserNotVerifiedException(String message) {
            super(message);
        }
    }

}

