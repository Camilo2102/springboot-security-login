package cloud.webgen.web.starter.webgenauthv2.events;

public record RegisterEvent(String userId, String mail, String subject, String template){
}
