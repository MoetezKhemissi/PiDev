package com.example.Project.Services;

import com.example.Project.entities.*;
import com.example.Project.Interfaces.IUserService;
import com.example.Project.Repositories.RoleRepository;
import com.example.Project.Repositories.UserCodeRepository;
import com.example.Project.Repositories.UserRepository;
import com.example.Project.Repositories.WalletRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;


@Service
@AllArgsConstructor
@NoArgsConstructor

    public class UserService implements IUserService {


    @Autowired
    ImplEmailService emailService;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserCodeRepository userCodeRepository;
    @Autowired
    WalletService walletService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Value("${stripe.apikey}")
    String stripeKey;


    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        Date dateNaissance = user.getBirthDate();
        LocalDate localDateNaissance = dateNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateSysteme = LocalDate.now();
        int age = Period.between(localDateNaissance, dateSysteme).getYears();
        user.setAge(age);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public User updateUserProfile(String username, User updatedUser) {
        User existingUser = retrieveUserByUsername(username);

        if (existingUser != null) {
            // Add other fields to update as needed
            existingUser.setCin(updatedUser.getCin());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAddres(updatedUser.getAddres());

            return userRepository.save(existingUser); // Save the updated user to the database
        } else {
            // You might want to handle the case where the user with the specified username is not found
            return null;
        }
    }
    @Override
    public User retrieveUser(Long IdUser) {
        return userRepository.findById(IdUser).get();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User retrieveUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User retrieveByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

    ////////SIGN-UP/////////
    @Override
    public User registerUser(User user) {
        Wallet wallet=new Wallet();
//        wallet.setBalance((double) 300);

//        Role role = roleRepository.findRoleByRoleName("Trader");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(role);
//        user.setRoles(userRoles);
        user.setIsVerified(0);
        user.setUserJob("User");
        user.setPassword(getEncodedPassword(user.getPassword()));
        Date dateNaissance = user.getBirthDate();
        LocalDate localDateNaissance = dateNaissance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateSysteme = LocalDate.now();
        int age = Period.between(localDateNaissance, dateSysteme).getYears();
        user.setAge(age);
        Stripe.apiKey= stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("name","ahmed");

        int balance = (int) (wallet.getBalance() * 100);
        params.put("balance",balance);
        //params.put("currency",w.getCurrency());

        Customer customer = null;
        try {
            customer = Customer.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        wallet.setBalance(500);
        wallet.setWallet_id(customer.getId());
        wallet.setCurrency("USD");

        try {
//            user.setWallet(wallet);
            userRepository.save(user);
            walletService.addWallet(wallet, user.getIdUser());
            wallet.setUser(user);

            VerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
            verificationTokenService.saveVerificationToken(verificationToken);
            String Token = verificationToken.getToken();
            emailService.sendVerificationEmail(user,Token);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User retrieveUserByPhone(Long Phone) {
        return userRepository.findByPhone(Phone);
    }


    //////User Verification //////
    public User VerifyUser(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user != null) {
            user.setIsVerified(1);
            user.setVerificationToken(null);
            userRepository.save(user);
        }
        return user;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    ///// AssignRole ///
    public void addRoleToUser(String roleName, Long idUser) {
        Role r = roleRepository.findRoleByRoleName(roleName);
        User u = userRepository.findById(idUser).orElse(null);
        Set<Role> userRoles = u.getRoles();
        userRoles.add(r);
        u.setRoles(userRoles);
        userRepository.save(u);
    }

    /// Reset Password feature /////
//    public boolean EmailExists(String Email) {
//        return userRepository.existsByEmail(Email);
//    }
// public User resetPassword(Long Phone) {
//     User user = userRepository.findUserByPhone(Phone);
//     if (user != null) {
//
//     }
//     return null;
// }

    public User ResetPasswordSms(String code, String newPassword, String confirmPassword) {
        User user = userRepository.findByVerificationCode(code);
        UserCode userCode = userCodeRepository.findByCode(code);
        if (user != null) {
            if (user.getVerificationCode().equals(userCode.getCode())) {
                if (newPassword.equals(confirmPassword)) {
                    user.setPassword(passwordEncoder.encode(newPassword));

                    userRepository.save(user);
                } else {
                    throw new IllegalArgumentException("Passwords do not match");
                }
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } else {
            throw new IllegalArgumentException("Verification code is invalid");
        }
        return user;


//    public User forgetPassword(String Email) {
//
//
//        if (EmailExists(Email)) {
//            User u = userRepository.findUserByEmail(Email);
//
//
//        }
//        return null;
//    }


    }
}





