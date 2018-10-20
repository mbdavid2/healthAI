package testJSON;

import entities.Establishment;
import entities.Hospital;
import entities.MedicCenter;
import entities.Vehicle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;

public class CreatorJSON {

    private List<Hospital> hospitals;
    private List<MedicCenter> medicCenters;

    public CreatorJSON(String filePath) {
        JsonReader reader = null;
        try {
            reader = Json.createReader(new FileReader("./lambda-functions/src/testJSON/input.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonStructure jsonst = reader.read();
        hospitals = new ArrayList<Hospital>();
        medicCenters = new ArrayList<MedicCenter>();
        navigateTreeCreateObjects(jsonst, "A.rar");
        //navigatePrintTree(jsonst, "A.rar");
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<MedicCenter> getMedicCenters() {
        return medicCenters;
    }

    private void getArrayObject(JsonValue tree, String key) {
        /*System.out.println("testing");
        return new Hospital(2, 2, 2, 2);*/
        JsonObject object = (JsonObject) tree;
        int i = 0;
        double lat = 0, lon = 0;
        int totalBeds = 0, freeBeds = 0;
        String name = "biene", id;
        for (String obj : object.keySet()) {
            switch (i) {
                case 0:
                    //TODO: EL id si es un hospital es un string y si es un medic center es un entero asi que peta, ignoramos de momento
                    /*JsonString idV = (JsonString) object.get(obj);
                    id = idV.toString();
                    System.out.println("test  " + id);*/
                    System.out.println("Ignoring id");
                    break;
                case 1:
                    JsonString nameV = (JsonString) object.get(obj);
                    name = nameV.toString();
                    System.out.println("Name:  " + name);
                    break;
                case 2:
                    JsonNumber latV = (JsonNumber) object.get(obj);
                    lat = latV.doubleValue();
                    System.out.println("Lat:  " + lat);
                    break;
                case 3:
                    JsonNumber lonV = (JsonNumber)object.get(obj);
                    lon = lonV.doubleValue();
                    System.out.println("Long:  " + lon);
                    break;
                case 4:
                    JsonNumber bedsV = (JsonNumber)object.get(obj);
                    totalBeds = bedsV.intValue();
                    System.out.println("Total number of beds:  " + totalBeds);
                    break;
                case 5:
                    JsonNumber bedsFV = (JsonNumber)object.get(obj);
                    freeBeds = bedsFV.intValue();
                    System.out.println("Free number of beds:  " + freeBeds);
                    break;
            }
            i++;
        }
        //All data of the object obtained, create it
        if (key.equals("create_medic_center")) {
            //TODO: añadir al constructor name, id y vehiculos????
            MedicCenter newMed = new MedicCenter(lat, lon);
            System.out.println(newMed);
            System.out.println();
            medicCenters.add(newMed);
        }
        else {
            Hospital newHosp = new Hospital(lat, lon, totalBeds, freeBeds);
            System.out.println(newHosp);
            System.out.println();
            hospitals.add(newHosp);
        }
    }

    private void navigateTreeCreateObjects(JsonValue tree, String key) {
        if (key != null) {
            System.out.print("Key " + key + ": ");
            if (key.equals("medic_center") || key.equals("hospitals")) {
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array)
                    getArrayObject(val, "create_" + key);
            }
            else if (key.equals("vehicles")) {
                System.out.println("Holita");
                /*JsonArray array = (JsonArray) tree;
                for (JsonValue val : array)
                    navigateTreeCreateObjects(val, null);*/
                System.out.println("ignoring");
            }
            else {
                System.out.println("OBJECT");
                JsonObject object = (JsonObject) tree;
                for (String name : object.keySet())
                    navigateTreeCreateObjects(object.get(name), name);
            }
        }
        else System.out.println("play despacito");
    }

    private void navigatePrintTree(JsonValue tree, String key) {
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