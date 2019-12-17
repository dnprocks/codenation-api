package br.com.challenge.service.interfaces;

import br.com.challenge.entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface UsersInfoServiceInterface {

    Users getUsersInfo();
}
