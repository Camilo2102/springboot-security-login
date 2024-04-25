package cloud.webgen.web.starter.webgenauthv2.repository;

import cloud.webgen.web.starter.sql.repository.AuditSqlRepository;
import cloud.webgen.web.starter.webgenauthv2.enums.AuthorityName;
import cloud.webgen.web.starter.webgenauthv2.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends AuditSqlRepository<Authority> {
    Optional<Authority> findByName(AuthorityName name);
}
