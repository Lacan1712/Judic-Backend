package App.Controllers;

import App.DTO.UsuarioDTO;
import App.Services.Authentication.AuthUserService;
import App.Services.DTO.CredenciasLoginDTO.CredenciaisDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Database.Entities.Usuarios;
import Utils.AlgorithmsUtil.RSAKeyUtil;
import Utils.JWTUtil.JwtUtil;
import Utils.PasswordHashingUtil.PasswordHashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author rodrigo
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthUserService authUserService;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(RSAKeyUtil rsaKeyUtil) {
        this.jwtUtil = new JwtUtil(rsaKeyUtil); // Injetando a implementação de RSAKeyUtil no construtor de JwtUtil
    }

    @PostMapping("/login")
    public ResponseEntity<Object> getString(@RequestBody CredenciaisDTO credenciaisDTO) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        Optional<Usuarios> usuariosOptional = authUserService.logarUsuario(credenciaisDTO.username(), credenciaisDTO.password());

        if(usuariosOptional.isEmpty()){
            httpHeaders.add("WWW-Authenticate","Basic realm=\"Access to API\", charset=\"UTF-8\"");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .headers(httpHeaders)
                                 .body("Usuário não autorizado");
        }


        var usuarioLogado = usuariosOptional.get();
        claims.put("id",usuarioLogado.getId().toString());
        claims.put("nome",usuarioLogado.getNome());
        claims.put("email",usuarioLogado.getEmail());
        claims.put("role",usuarioLogado.getRole().getNome());

        var tokenJwt = jwtUtil.generateJWTWithClaims(claims);

        response.put("token", tokenJwt);
        response.put("status", 200);
        return ResponseEntity.accepted().body(response);
    }
}
