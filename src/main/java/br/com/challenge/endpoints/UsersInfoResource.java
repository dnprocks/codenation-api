package br.com.challenge.endpoints;

import br.com.challenge.entity.Users;
import br.com.challenge.service.impl.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("usersinfos")
public class UsersInfoResource {

    @Autowired
    private UsersInfoService usersInfoServices;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Users getUsers() {

        return usersInfoServices.getUsersInfo();
    }
}
