package Utils.JWTUtil;


import Utils.AlgorithmsUtil.Interfaces.CryptographyRSAInterface;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
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
                .withIssuer("judic-backend")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hora de expiração
                .withPayload(claims) // Adiciona as claims
                .sign(algorithm);
    }

    public DecodedJWT getSubjectsFromToken(String token) {
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) this.RSAlgorithm.loadPublicKey(), (RSAPrivateKey) this.RSAlgorithm.loadPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("judic-backend")
                    // reusable verifier instance
                    .build();
        return verifier.verify(token);
    }

    public String getRolesFromToken(String token) {
        DecodedJWT jwt = getSubjectsFromToken(token);

        // Extrai a claim 'roles' do token JWT
        Claim rolesClaim = jwt.getClaim("role");

        // Converte a claim para uma lista de strings
        return rolesClaim.toString();
    }

}
