package com.epam.company.util.secure;

import com.epam.company.model.dao.User;
import com.epam.company.service.IUserService;
import com.epam.company.util.resolvers.SecureResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by @belrbeZ
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService service;

    @Autowired
    public UserDetailsServiceImpl(IUserService service) {
        this.service = service;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        if(email.contains("@"))
            user = service.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user present with email: " + email));
        else {
            user = service.getBySsoId(email).orElseThrow(() -> new UsernameNotFoundException("No user present with login: " + email));
        }
        return new UserCredentials(user, SecureResolver.convert(user.getUserProfileType()));
    }
}
