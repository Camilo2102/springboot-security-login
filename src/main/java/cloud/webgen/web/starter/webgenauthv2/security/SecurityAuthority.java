package cloud.webgen.web.starter.webgenauthv2.security;

import cloud.webgen.web.starter.webgenauthv2.model.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final Authority authority;
    @Override
    public String getAuthority() {
        return authority.getName().toString();
    }
}
