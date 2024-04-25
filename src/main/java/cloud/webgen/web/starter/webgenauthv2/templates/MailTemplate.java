package cloud.webgen.web.starter.webgenauthv2.templates;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailTemplate {

    private static String origin;
    @Value("${cors.allowed-origin}")
    public void setOrigin(String origin) {
        MailTemplate.origin = origin;
    }

    public static String getRegisterTemplate(String jwt, String id) {
        return "Bienvenido, para continuar confirmar  tu registro ingresa a: " +
                "<a href=\"" + origin + "/auth/verification?token=" + jwt + "&id=" + id + "\">Confirmar Registro</a>";
    }
}
