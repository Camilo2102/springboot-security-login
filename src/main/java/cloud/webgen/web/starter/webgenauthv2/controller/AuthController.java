package cloud.webgen.web.starter.webgenauthv2.controller;

import cloud.webgen.web.starter.exeptions.HttpException;
import cloud.webgen.web.starter.webgenauthv2.DTO.AuthResponseDTO;
import cloud.webgen.web.starter.webgenauthv2.DTO.LoginRequestDTO;
import cloud.webgen.web.starter.webgenauthv2.DTO.RegisterRequestDTO;
import cloud.webgen.web.starter.webgenauthv2.DTO.VerifyRequestDTO;
import cloud.webgen.web.starter.webgenauthv2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginData) throws HttpException {
        return new ResponseEntity<>(this.authService.login(loginData), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerData) throws HttpException {
        return new ResponseEntity<>(this.authService.register(registerData), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequestDTO verifyData) throws HttpException {
        return new ResponseEntity<>(this.authService.verify(verifyData), HttpStatus.OK);
    }

    @GetMapping("/validate")
    public boolean validate (){
        return true;
    }
}
