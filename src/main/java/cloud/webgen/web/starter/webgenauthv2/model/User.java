package cloud.webgen.web.starter.webgenauthv2.model;


import cloud.webgen.web.starter.sql.model.AuditSqlModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "users")
public class User extends AuditSqlModel {

    private Boolean active;

    @Column(unique = true)
    private String mail;

    @Column(unique = true)
    private String userName;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public User(Boolean active, String mail, String userName, String password, List<Authority> authorities) {
        super();
        this.active = active;
        this.mail = mail;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }
}
