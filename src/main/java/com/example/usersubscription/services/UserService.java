package com.example.usersubscription.services;

import com.example.usersubscription.entities.Subscription;
import com.example.usersubscription.entities.User;
import com.example.usersubscription.entities.UserPrincipal;
import com.example.usersubscription.repositories.SubscriptionRepo;
import com.example.usersubscription.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User login(User user)
    {
        try {
            User u;
            u = userRepo.findByEmail(user.getEmail());

            if (u!= null)
            {

                if (bCryptPasswordEncoder.matches(user.getPassword(),u.getPassword()))
                {

                    return u;
                }
                else throw new UsernameNotFoundException("User not found");
            }
            else throw new UsernameNotFoundException("User not found");

        }
        catch (UsernameNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        return null;

    }


    public User register(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            User u = userRepo.save(user);
            System.out.println(u);
            return u;
        }catch (DataIntegrityViolationException e)
        {
            System.out.println("Email already taken");
            throw new DuplicateKeyException("Email already taken");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);

        if (user == null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }


}
