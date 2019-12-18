package br.com.challenge.repository;

import br.com.challenge.entity.LogError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface LogErrorRepository extends JpaRepository<LogError, Long> {

    @Query(value = "SELECT * FROM LOG_ERROR WHERE id = ?1 AND users_id = ?2",
            nativeQuery = true)
    LogError findByIdUsersId(Long id, Long usersId);

    @Query(value = "SELECT environment, count(*) as count FROM LOG_ERROR WHERE users_id = ?1 GROUP BY environment",
            nativeQuery = true)
    List<Tuple> getEnvironmentCountLogError(Long usersId);

    @Query(value = "SELECT * FROM LOG_ERROR WHERE users_id = ?1 AND filed = ?2",
            countQuery = "SELECT count(*) FROM LOG_ERROR WHERE users_id = ?1 AND filed = ?2",
            nativeQuery = true)
    Page<LogError> findAllNonFiledUserLogError(Long usersId, boolean filed, Pageable pageable);

    @Query(value = "SELECT * FROM LOG_ERROR WHERE users_id = ?1 AND environment = ?2",
            countQuery = "SELECT count(*) FROM LOG_ERROR WHERE users_id = ?1 AND environment = ?2",
            nativeQuery = true)
    Page<LogError> findByUsersIdEnvironment(Long usersId, int environmentCod, Pageable pageable);

    @Query(value = "SELECT * FROM LOG_ERROR WHERE users_id = ?1 AND filed = ?2 AND (title LIKE %?3% OR details LIKE %?3% OR request_ip LIKE %?3%)",
            countQuery = "SELECT count(*) FROM LOG_ERROR WHERE users_id = ?1 AND filed = ?2 AND (title LIKE %?3% OR details LIKE %?3% OR request_ip LIKE %?3%)",
            nativeQuery = true)
    Page<LogError> findAllNonFiledUserLogErrorWithGenericFilter(Long usersId, boolean filed, String filter, Pageable pageable);

}
