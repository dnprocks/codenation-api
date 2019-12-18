package br.com.challenge.endpoints;

import br.com.challenge.dto.LogErrorCountDTO;
import br.com.challenge.dto.LogErrorDTO;
import br.com.challenge.entity.LogError;
import br.com.challenge.service.impl.LogErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("logerrors")
public class LogErrorResource {

    @Autowired
    LogErrorService logErrorService;

    @Autowired
    private HttpServletRequest request;

    /**
     * A consulta por Id considera o id do usuário logado na pesquisa
     * **/
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LogError getLogErrors(@PathVariable Long id) {

        return logErrorService.getLogError(id);
    }

    /**
     * A consulta sem filtro considera o id do usuário logado na pesquisa
     * **/
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<LogError> getLogErrors(Pageable pageable) {

        return logErrorService.getLogErrors("", pageable);
    }

    /**
     * A consulta com filtro considera o id do usuário logado na pesquisa
     * **/
    @GetMapping(params = { "filter" })
    @ResponseStatus(HttpStatus.OK)
    public Page<LogError> getLogErrors(@RequestParam("filter") String filter, Pageable pageable) {

        return logErrorService.getLogErrors(filter, pageable);
    }

    /**
     * A consulta por ambiente considera o id do usuário logado na pesquisa
     * **/
    @GetMapping(params = { "environment" })
    @ResponseStatus(HttpStatus.OK)
    public Page<LogError> getLogErrorsByEnvironment(@RequestParam("environment") String environment, Pageable pageable) {

        return logErrorService.getLogErrorsByEnvironment(environment, pageable);
    }

    /**
     * A consulta da quantidade de erros por ambiente considera o id do usuário logado na pesquisa
     * **/
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public List<LogErrorCountDTO> getCountLogErrorByEnvironment() {

        return logErrorService.getEnvironmentCountLogError();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LogError saveLogError(@RequestBody LogErrorDTO logError) {

        String requestIp = request.getRemoteAddr();

        return logErrorService.saveLogError(logError, requestIp);
    }

    @PutMapping("/file/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String fileLogError(@PathVariable Long id) {

        return logErrorService.fileLogError(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteLogError(@PathVariable Long id) {

        return logErrorService.deleteLogError(id);
    }
}
