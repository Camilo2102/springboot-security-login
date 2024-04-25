package cloud.webgen.web.starter.webgenauthv2.service;

import cloud.webgen.web.starter.exeptions.HttpException;
import cloud.webgen.web.starter.webgenauthv2.DTO.AuthResponseDTO;
import cloud.webgen.web.starter.webgenauthv2.DTO.RegisterRequestDTO;
import cloud.webgen.web.starter.webgenauthv2.DTO.VerifyRequestDTO;
import cloud.webgen.web.starter.webgenauthv2.DTO.LoginRequestDTO;
import cloud.webgen.web.starter.webgenauthv2.events.RegisterEvent;
import cloud.webgen.web.starter.webgenauthv2.model.User;
import cloud.webgen.web.starter.webgenauthv2.repository.UserRepository;
import cloud.webgen.web.starter.webgenauthv2.security.SecurityUser;
import cloud.webgen.web.starter.webgenauthv2.templates.MailTemplate;
import cloud.webgen.web.starter.webgenauthv2.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuthResponseDTO login(LoginRequestDTO loginData) throws HttpException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUserName(), loginData.getPassword()));

        User user = userRepository.findByUserNameOrMail(loginData.getUserName(), loginData.getUserName())
                .orElseThrow(() -> new HttpException("Not found", HttpStatus.NOT_FOUND));

        if (!user.getActive()) throw new HttpException("Inactive user", HttpStatus.BAD_REQUEST);

        return AuthResponseDTO.builder().token(jwtService.getToken(new SecurityUser(user))).userId(user.getId()).build();
    }

    public AuthResponseDTO register(RegisterRequestDTO registerData) throws HttpException {
        User user = User.builder()
                .mail(registerData.getMail())
                .userName(registerData.getUserName())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .active(false)
                .build();

        if (!isMailAvailable(registerData.getMail())) throw new HttpException("Email in use", HttpStatus.BAD_REQUEST);

        this.userRepository.save(user);

        String jwt = jwtService.getToken(new SecurityUser(user));


        UUID eventKey = UUID.randomUUID();

        this.kafkaTemplate.send("qufjdolb-register-topic", eventKey.toString(), JsonUtils.toJson(
                new RegisterEvent(user.getId(), user.getMail(), "Registro", MailTemplate.getRegisterTemplate(jwt, user.getId()))
        ));

        return AuthResponseDTO.builder().token(jwt).userId(user.getId()).build();
    }

    private boolean isMailAvailable(String mail) {
        return this.userRepository.findByMail(mail).isEmpty();
    }

    public boolean verify(VerifyRequestDTO verifyData) throws HttpException {
        User userFound = this.userRepository.findById(verifyData.getUserId())
                .orElseThrow(() -> new HttpException("Invalid user", HttpStatus.BAD_REQUEST));

        userFound.setActive(true);

        this.userRepository.save(userFound);

        return true;
    }
}
