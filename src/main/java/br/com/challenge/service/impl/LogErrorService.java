package br.com.challenge.service.impl;

import br.com.challenge.dto.LogErrorCountDTO;
import br.com.challenge.dto.LogErrorDTO;
import br.com.challenge.entity.LogError;
import br.com.challenge.entity.Users;
import br.com.challenge.enums.Environment;
import br.com.challenge.repository.LogErrorRepository;
import br.com.challenge.repository.UsersRepository;
import br.com.challenge.service.interfaces.LogErrorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogErrorService implements LogErrorServiceInterface {

    @Autowired
    LogErrorRepository logErrorRepository;

    @Autowired
    UsersRepository usersRepository;

    private Long getAuthenticadedUserId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return usersRepository.findByEmail(userDetails.getUsername()).getId();
    }

    @Override
    public LogError getLogError(Long id) {

        return logErrorRepository.findByIdUsersId(id, getAuthenticadedUserId());
    }

    @Override
    public Page<LogError> getLogErrors(String genericFilter, Pageable pageable) {

        genericFilter = genericFilter.trim();
        if (genericFilter.isEmpty()){
            return logErrorRepository.findAllNonFiledUserLogError(getAuthenticadedUserId(), false, pageable);
        }
        else {
            return logErrorRepository.findAllNonFiledUserLogErrorWithGenericFilter(getAuthenticadedUserId(), false, genericFilter, pageable);
        }
    }

    @Override
    public Page<LogError> getLogErrorsByEnvironment(String environmentDescription, Pageable pageable) {

        if (environmentDescription == null || environmentDescription.isEmpty()) {
            throw new IllegalArgumentException("Descrição inválida");
        }

        int environment = Environment.toEnum(environmentDescription).getCod();

        return logErrorRepository.findByUsersIdEnvironment(getAuthenticadedUserId(), environment, pageable);
    }

    @Override
    public List<LogErrorCountDTO> getEnvironmentCountLogError(){

        List<Tuple> tupleList = logErrorRepository.getEnvironmentCountLogError(getAuthenticadedUserId());
        return LogErrorCountDTO.buildList(tupleList);
    }

    @Override
    public LogError saveLogError(LogErrorDTO logErrorModel, String requestIp) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        Users authenticatedUser = usersRepository.findByEmail(userDetails.getUsername());

        LogError logError = LogError.builder()
                                    .users(authenticatedUser)
                                    .level(logErrorModel.getErrorLevel())
                                    .requestIp(requestIp)
                                    .environment(logErrorModel.getEnvironment())
                                    .details(logErrorModel.getDetails())
                                    .title(logErrorModel.getTitle())
                                    .filed(false)
                                    .createdAt(LocalDateTime.now()).build();

        return logErrorRepository.save(logError);
    }

    @Override
    public String fileLogError(Long id) {

        LogError logError = logErrorRepository.findById(id).orElse(null);
        if (logError == null){
            return "Falha ao consultar de Log do Erro. Detalhes: Registro não encontrado.";
        }

        logError.setFiled(true);

        try{
            logErrorRepository.save(logError);
        }
        catch (Exception ex){
            return "Falha não esperada ao arquivar registro de Log do Erro. Detalhes: " + ex.getMessage();
        }
        return "Registro de Log do Erro arquivado com sucesso!";
    }

    @Override
    public String deleteLogError(Long id) {

        LogError logError = logErrorRepository.findById(id).orElse(null);
        if (logError == null){
            return "Falha ao consultar Log do Erro. Detalhes: Registro não encontrado.";
        }

        try{

            logErrorRepository.delete(logError);
        }
        catch (Exception ex){

            return "Falha não esperada ao excluir registro de Log do Erro. Detalhes: " + ex.getMessage();
        }

        return "Registro de Log do Erro excluído com sucesso!";
    }
}
