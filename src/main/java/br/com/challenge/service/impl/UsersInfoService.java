package br.com.challenge.service.impl;

import br.com.challenge.entity.Users;
import br.com.challenge.repository.UsersRepository;
import br.com.challenge.service.interfaces.UsersInfoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsersInfoService implements UsersInfoServiceInterface {

    @Autowired
    UsersRepository usersRepository;

    private Users getAuthenticadedUserId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return usersRepository.findByEmail(userDetails.getUsername());
    }

    @Override
    public Users getUsersInfo() {

        return getAuthenticadedUserId();
    }
}
