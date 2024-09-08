package Utils.JWTUtil;


import Utils.AlgorithmsUtil.Interfaces.CryptographyRSAInterface;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private final CryptographyRSAInterface<RSAPublicKey, RSAPrivateKey> RSAlgorithm;

    @Autowired
    public JwtUtil(CryptographyRSAInterface<RSAPublicKey, RSAPrivateKey> alghoritm) {
        this.RSAlgorithm = alghoritm;
    }

    public String generateJWTWithClaims(Map<String, Object> claims) {
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) this.RSAlgorithm.loadPublicKey(), (RSAPrivateKey) this.RSAlgorithm.loadPrivateKey());

        // Cria o token JWT
        return JWT.create()
                .withIssuer("judic-bakend")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hora de expiração
                .withPayload(claims) // Adiciona as claims
                .sign(algorithm);
    }

    public void getSubjectsFromToken(String token) {

    }

}
