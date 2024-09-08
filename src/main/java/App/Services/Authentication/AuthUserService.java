package App.Services.Authentication;

import Database.Entities.Usuarios;
import Database.Services.UsuarioService;
import Utils.PasswordHashingUtil.PasswordHashingUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private UsuarioService usuarioService;


    private Boolean validarEmailNoBanco(String email){
        return usuarioService.existsByEmail(email);
    }

    private Boolean validarSenhaNoBanco(String plainPassword, String hashedPassword){
        return PasswordHashingUtil.checkPassword(plainPassword, hashedPassword);
    }

    public Optional<Usuarios> logarUsuario(String email, String plainPassword){
        if(!this.validarEmailNoBanco(email)){
            return Optional.empty();
        };

        Optional<Usuarios> optionalusuario = usuarioService.findByEmail(email);



        if(!this.validarSenhaNoBanco(plainPassword, optionalusuario.get().getSenha())) {
            return Optional.empty();
        }

        return Optional.of(optionalusuario.get());

    }
}
