package Utils.EnviromentUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvUtil {

    private String FILE_PATH = "src/main/java/.env";

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public Map<String, String> EnvUtil() throws IOException {
        Map<String, String> envVariables = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(this.getFILE_PATH()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignora linhas em branco e comentários
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Divide as variáveis no formato chave=valor
                String[] keyValue = line.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    envVariables.put(key, value);
                }
            }
        }

        return envVariables;
    }
}
