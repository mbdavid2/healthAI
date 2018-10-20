package testJSON;

import java.io.FileReader;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;

public class createObjectJSONpp {

    public static void main(String[] args) throws Exception {
        JsonReader reader = Json.createReader(new FileReader("/home/mbdavid2/Documents/GitKraken/voidMistery/lambda-functions/src/testJSON/input.json"));
        JsonStructure jsonst = reader.read();
        navigatePrintTree(jsonst, "A.rar");
    }

    /*public static void navigateTreeCreateObjects(JsonValue tree, String key) {



        if (key != null)
            System.out.print("Key " + key + ": ");
        switch(tree.getValueType()) {
            case OBJECT:
                System.out.println("OBJECT");
                JsonObject object = (JsonObject) tree;
                for (String name : object.keySet())
                    navigatePrintTree(object.get(name), name);
                break;
            case ARRAY:
                System.out.println("ARRAY");
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array)
                    navigatePrintTree(val, null);
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                System.out.println("STRING " + st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                System.out.println("NUMBER " + num.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println(tree.getValueType().toString());
                break;
        }
    }*/

    public static void navigatePrintTree(JsonValue tree, String key) {
        if (key != null)
            System.out.print("Key " + key + ": ");
        switch(tree.getValueType()) {
            case OBJECT:
                System.out.println("OBJECT");
                JsonObject object = (JsonObject) tree;
                for (String name : object.keySet())
                    navigatePrintTree(object.get(name), name);
                break;
            case ARRAY:
                System.out.println("ARRAY");
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array)
                    navigatePrintTree(val, null);
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                System.out.println("STRING " + st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                System.out.println("NUMBER " + num.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println(tree.getValueType().toString());
                break;
        }
    }
}
