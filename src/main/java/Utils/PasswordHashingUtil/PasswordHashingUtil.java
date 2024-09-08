package Utils.PasswordHashingUtil;

import org.apache.sshd.common.config.keys.loader.openssh.kdf.BCrypt;
import org.wildfly.security.password.interfaces.BCryptPassword;

public class PasswordHashingUtil {

    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static Boolean checkPassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
