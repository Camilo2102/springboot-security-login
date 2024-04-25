package cloud.webgen.web.starter.webgenauthv2.model;

import cloud.webgen.web.starter.sql.model.AuditSqlModel;
import cloud.webgen.web.starter.webgenauthv2.enums.AuthorityName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "authorities")
public class Authority extends AuditSqlModel {

    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    public Authority(AuthorityName name) {
        super();
        this.name = name;
    }
}
