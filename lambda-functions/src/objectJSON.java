import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;

public class objectJSON {

    public static void main(String[] args) throws Exception {
        JsonReader reader = Json.createReader(new FileReader("input.json"));
        JsonStructure jsonst = reader.read();
    }
}
