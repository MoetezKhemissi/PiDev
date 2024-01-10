package com.example.Project.controllers;

import com.example.Project.entities.User;
import com.example.Project.entities.UserCode;
import com.example.Project.entities.VerificationToken;
import com.example.Project.Repositories.UserRepository;
import com.example.Project.services.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    JavaMailSender mailSender;


    SmsService smsService;
    @Autowired
    UserCodeService codeService;
    @Autowired
     UserService userService;

    @Autowired
    ImplEmailService emailService;
    @Autowired
    UserRepository userRepository;

     @Autowired
     VerificationTokenService verificationTokenService;


//    @GetMapping("/profile")
//    public User viewProfile(Authentication authentication) {
//
//        return (User) authentication.getPrincipal();
//    }



//// Connected User Profile ////////
//    @GetMapping("/profile")
//    public User getConnectedUser(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String username = userDetails.getUsername();
//        User user = userService.retrieveUserByUsername(username);
//        return user;
//    }
    @GetMapping("/profile/{username}")
    public User getConnectedUser(@PathVariable String username) {
        User user = userService.retrieveUserByUsername(username);
        return user;
    }

    @PutMapping("/update-profile/{username}")
    public User updateUser(@PathVariable String username,@RequestBody User updatedUser) {
        return userService.updateUserProfile(username, updatedUser);
    }

/////// Sign-UP ///////////
    @PostMapping({"/register"})
    public User registerNewUser(@RequestBody User user) {
        User NewUser= userService.registerUser(user);


        return NewUser;
    }


    @GetMapping("/verify/{verificationToken}")
    public String activateAccount(@PathVariable String verificationToken) throws javax.mail.MessagingException {
        User user = userService.VerifyUser(verificationToken);
        if (user != null) {
            String to = user.getEmail();
            String subject = "Account Created";
            try {
                emailService.sendEmail(to, subject);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return ("Congratulations " + user.getUsername() + " Your account has been activated successfully");
        } else {
            return ("there was an error verifying your account, please make sure you have entered the right token and that the token hasn't expried");
        }
    }



///////Reset Password Sms////////
    @PostMapping({"/SendSMS/{Phone}"})
    public User SmsSender(@PathVariable Long Phone) {
        User NewUser=userService.retrieveUserByPhone(Phone);
        UserCode userCode = codeService.createVerificationCode(NewUser); // création du jeton de vérification
        codeService.saveVerificationCode(userCode);
         NewUser= userService.updateUser(NewUser);

        return NewUser;
    }


    @PutMapping("/reset-password/{verificationCode}")
    public void activateAccount(@PathVariable String verificationCode,@RequestParam String newPassword ,@RequestParam String confirmPassword) throws javax.mail.MessagingException {
        User user = userService.retrieveByVerificationCode(verificationCode);
        if (user != null) {
            user=userService.ResetPasswordSms(verificationCode,newPassword,confirmPassword);
            String to = user.getEmail();
            String subject = "  Account Updated";
            String text = "Congratulations " + user.getUsername() + " Your password account has been updated successfully";
            try {
                emailService.SendResetMail(to,subject,text);
                user.setVerificationCode(null);
                userService.updateUser(user);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
//            return ("Congratulations " + user.getUsername() + " Your password account has been updated successfully");
//        } else {
//            return ("there was an error verifying your account, please make sure you have entered the right token and that the token hasn't expried");
//        }
    }



///// Assign Role To User ////////

    @PutMapping  ({"/addRole/{roleName}/{Username}"})
      public void addRoleToUser(@PathVariable String roleName,@PathVariable Long idUser) {
        userService.addRoleToUser(roleName,idUser);
    }



        @GetMapping("/retrieve-all-Users")
        public List<User> getUsers() {
            List<User> listUsers = userService.retrieveAllUsers();
            return listUsers;

        }


        @GetMapping("/retrieve-user/{iduser}")
        public User retrieveUserById(@PathVariable("iduser") Long IdUser) {
            return userService.retrieveUser(IdUser);
        }

      @GetMapping("/retrieve/{username}")
       public User getUserByUsername(@PathVariable("username") String username) {
         return userService.retrieveUserByUsername(username);
    }



       @PostMapping("/add-User")
        public User addUser(@RequestBody User u) {
            User  user = userService.addUser(u);
            return user;
        }

        @DeleteMapping("/remove-user/{iduser}")
        public void removeUser(@PathVariable("iduser") Long IdUser) {
            userService.deleteUser(IdUser);
        }


        @PutMapping("/update-User")
        public User updateUser(@RequestBody User  u) {
            User user = userService.updateUser(u);
            return user;
        }
}



//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
//    }



//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody Authentication authenticationRequest)
//            throws AuthenticationException {
//
//        // Authenticate the user
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
//
//        // Generate a JWT for the user
//        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        final String jwt = jwtTokenUtil.generateToken(userDetails);
//
//        // Send the JWT as a response to the client
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(authenticationRequest.getUsername());
//
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }







//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "registration";
//    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
//        userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        userService.save(userForm);
//
//        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
//
//        return "redirect:/welcome";
//    }


//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//
//        return "signup_form";
//    }
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(Model model, String error, String logout) {
//        if (error != null)
//            model.addAttribute("error", "Your username and password is invalid.");
//
//        if (logout != null)
//            model.addAttribute("message", "You have been logged out successfully.");
//
//        return "login Succeed";
//    }