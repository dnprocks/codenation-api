package br.com.challenge.service.interfaces;

import br.com.challenge.dto.LogErrorCountDTO;
import br.com.challenge.dto.LogErrorDTO;
import br.com.challenge.entity.LogError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogErrorServiceInterface {

    LogError getLogError(Long id);

    Page<LogError> getLogErrors(String filter, Pageable pageable);

    Page<LogError> getLogErrorsByEnvironment(String environmentDescription, Pageable pageable);

    List<LogErrorCountDTO> getEnvironmentCountLogError();

    LogError saveLogError(LogErrorDTO logError, String requestIp);

    String fileLogError(Long id);

    String deleteLogError(Long id);
}
