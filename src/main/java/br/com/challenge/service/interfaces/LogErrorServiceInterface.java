package br.com.challenge.service.interfaces;

import br.com.challenge.dto.LogErrorDTO;
import br.com.challenge.entity.LogError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LogErrorServiceInterface {

    LogError getLogError(Long id);

    Page<LogError> getLogErrors(Pageable pageable);

    Page<LogError> getLogErrors(String filter, Pageable pageable);

    LogError saveLogError(LogErrorDTO logError, String requestIp);

    String fileLogError(Long id);

    String deleteLogError(Long id);
}
