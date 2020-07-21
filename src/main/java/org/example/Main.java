package org.example;

import javax.json.*;
import javax.json.stream.JsonCollectors;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.Scanner;

/**
 *
 * JSON with JAVA EE: Hands on Training
 * @author Henry Kafuuma
 *
 * **/

public class Main{

    public static void main(String[] args) {
        new Main().start();
    }

    private  void start(){
        while(true){
            printOptions();
            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextLine()){
                case "q":
                case "Q":
                    return;
                case "1":
                    jsonParserScenario();
                    break;
                case "2":
                    jsonGeneratorScenario();
                    break;
                case "3":
                    objectModelScenario();
                    break;
                case "4":
                    jsonPointerScenario();
                    break;
                case "5":
                    jsonPatchScenario();
                    break;
                case "6":
                    jsonMergePatchScenario();
                    break;
                case "7":
                    streamApiScenario();
                    break;
                case "8":
                    bigJsonScenario();
            }
        }
    }

    private void printOptions(){
        System.out.println();
        System.out.println("----------------------- JSON with Java EE: Hand on Training -----------------------");
        System.out.println();
        System.out.println("Choose a scenario to run or press 'Q' to exit:");
        System.out.println();
        System.out.println("1. JSON parser");
        System.out.println("2. JSON generator");
        System.out.println("3. Create object model from file");
        System.out.println("4. JSON Pointer");
        System.out.println("5. JSON Patch");
        System.out.println("6. JSON Merge Patch");
        System.out.println("7. Java 8 Stream API");
        System.out.println("8. Processing big JSON documents");

        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /*
    * Scenario 1: Using JsonParser (Video 2.3)
    *
    * Challenges:
    * 1.Parse documents and list all JSON-P events
    * 2.List all keys corresponding to events
    * 3.List all values
    *
    * */

    private void jsonParserScenario() {
        JsonParser jsonParser = Json.createParser(Main.class.getResourceAsStream("/jsons.json"));


        //JsonParserFactory jsonParserFactory = Json.createParserFactory(null);
        //JsonParser parser = jsonParserFactory.createParser(Main.class.getResourceAsStream("/jsons.json"));

        while (jsonParser.hasNext()){
            JsonParser.Event event = jsonParser.next();
            System.out.print(event.toString());


            switch (event){
                case KEY_NAME:
                case VALUE_STRING:
                case VALUE_NUMBER:
                    System.out.print("***: "+jsonParser.getString());
                    break;
                case VALUE_NULL:
                    System.out.print(": null");
                    break;
                case VALUE_TRUE:
                    System.out.print(": true");
                    break;
                case VALUE_FALSE:
                    System.out.print(": false");
            }

            System.out.println();
        }
    }


    /**
    * Scenario 2: Using JsonGenerator
    * Challenge
    * Write to system.out a JSON document below using JsonGenerator
     *   {
     *     "name": "Jason Bourne",
     *     "profession": "Super agent",
     *     "bad-guy": false,
     *     "kills": 1000,
     *     "phoneNumbers": [
     *       {
     *         "type": "home",
     *         "number": "123-456-789"
     *       }
     *     ]
     *   }
    * */

    private void jsonGeneratorScenario(){
        //JsonGenerator generator = Json.createGenerator(System.out);


//        Map<String,Boolean> config = new HashMap<>(JsonGenerator.PRETTY_PRINTING, true);

        JsonGeneratorFactory jsonGeneratorFactory = Json.createGeneratorFactory(null);
        JsonGenerator generator = jsonGeneratorFactory.createGenerator(System.out);

        generator.writeStartObject()
                .write("name", "Json Bourne")
                .write("profession","Super Agent")
                .write("bad-guy", false)
                .write("kills", 1000)
                    .writeStartArray("phoneNumbers")
                        .writeStartObject()
                            .write("type", "home")
                            .write("number", "123-345-987")
                        .writeEnd()
                    .writeEnd()
                .writeEnd()
                .flush();
    }


    /**
     * Scenario 3: Using Object Model API (Video 2.5).
     *
     * Challenges:
     *
     * 1. Create object model representing the following JSON document:
     *
     *   {
     *     "name": "Jason Bourne",
     *     "profession": "Super agent",
     *     "bad-guy": false,
     *     "kills": 1000,
     *     "phoneNumbers": [
     *       {
     *         "type": "home",
     *         "number": "123-456-789"
     *       }
     *     ]
     *   }
     *
     * 2. Write it to a stream.
     * 3. Create JsonReader and read JSON object model from a stream.
     * 4. Traverse the model and print all keys, key types and values
     */

    public void objectModelScenario(){

        //create object model
        JsonObject data = Json.createObjectBuilder()
                .add("name", "Json Bourne")
                .add("profession", "Super Agent")
                .add("bad-gy", false)
                .add("phoneNumbers", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                        .add("type", "home")
                        .add("number", "123-456-200")))
                .build();

        //write to the model stream
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stringWriter);


        //JsonWriterFactory jsonWriterFactory = Json.createWriterFactory(null);
        //JsonWriter jsonWriter = jsonWriterFactory.createWriter(stringWriter);

        jsonWriter.writeObject(data);
        jsonWriter.close();

        String str = stringWriter.toString();
        System.out.println(str);

        //create object model from a stream
        StringReader stringReader = new StringReader(str);
        JsonReader jsonReader = Json.createReader(stringReader);


        //JsonReaderFactory jsonReaderFactory = Json.createReaderFactory(null);
        //JsonReader jsonReader = jsonReaderFactory.createReader(stringReader);


        JsonObject obj = jsonReader.readObject();

        //traverse mode

        traversModel(obj,  null);

    }

    private void traversModel(JsonValue value, String key) {

        System.out.print("Key: "+key+"; ");

        switch (value.getValueType()){
            case ARRAY:
                System.out.println("Type: Array");
                JsonArray array = (JsonArray) value;
                for(JsonValue item : array){
                    traversModel(item, null);
                }
                break;
            case OBJECT:
                System.out.println("Type: OBJECT");
                JsonObject obj = (JsonObject) value;
                for (String objKey: obj.keySet()) {
                    traversModel(obj.get(objKey), objKey);
                }
                break;
            case STRING:
                JsonString jsonString = (JsonString) value;
                System.out.println("Type: STRING; Value: " + jsonString.getString());
                break;
            case NUMBER:
                JsonNumber jsonNumber = (JsonNumber) value;
                System.out.println("Type: NUMBER; Value: " + jsonNumber.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println("Value: " + value.getValueType());
        }
    }


    /**
     * Scenario 4: Using JsonPointer (Video 3.2).
     *
     * Challenges:
     *
     * 1. Load jasons.json file
     * 2. Use JsonPointer to change Jason Voorhees profession
     *    to "Hockey Player"
     * 3. Use JsonPointer to delete Jason Bourne work phone number
     * 4. Use JsonPointer to add a property "/~" with
     *    value "slash tilde" to Json Bourne object
     */
    private void jsonPointerScenario() {
        // Load jason.json file
        JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/jsons.json"));
        JsonArray jasons = reader.readArray();

        // Use JsonPointer to change Jason Voorhees profession
        // to "Hockey Player"
        JsonPointer p = Json.createPointer("/1/profession");
        jasons = p.replace(jasons, Json.createValue("Hockey Player"));
        System.out.println(jasons);

        // Use JsonPointer to delete Jason Bourne work phone number
        p = Json.createPointer("/0/phoneNumbers/1");
        jasons = p.remove(jasons);
        System.out.println(jasons);

        // Use JsonPointer to add a property "/~"
        // with value "slash tilde" to Jason Bourne object
        p = Json.createPointer("/0/~1~0");
        jasons = p.add(jasons, Json.createValue("slash tilde"));
        System.out.println(jasons);
    }

    /**
     * Scenario 5: Using JsonPatch (Video 3.3).
     *
     * Challenge:
     *
     * Create and apply a patch to jasons.json which
     *
     * 1. Changes Jason Voorhees profession
     *    to "Hockey Player"
     * 2. Deletes Jason Bourne work phone number
     * 3. Adds "notes" property with value
     *    "Wanted by the police" to Jason Voorheez
     */


    private void jsonPatchScenario(){
        //Load json.json file
        JsonReader reader = Json.createReader(Main.class.getResourceAsStream("/jsons.json"));
        JsonArray jsons = reader.readArray();

        //create JsonPatch
        JsonPatch patch = Json.createPatchBuilder()
                .replace("/1/profession", "Hockey Player")
                .remove("/0/phoneNumbers/1")
                .add("/1/notes", "Wanted by the police")
                .build();

        //Apply patch
        jsons = patch.apply(jsons);
        System.out.println(jsons);
    }

    /**
     * Scenario 6: Using JsonMergePatch (Video 3.4).
     *
     * Challenge:
     *
     * Use JsonMergePatch to apply scenario_6/patch.json
     * to scenario_6/issue.json
     */

    private void jsonMergePatchScenario(){

        //load issue.json
        JsonObject issue = Json.createReader(Main.class.getResourceAsStream("/scenario_6/issue.json"))
                .readObject();

        //load patch.json
        JsonObject patch = Json.createReader(Main.class.getResourceAsStream("/scenario_6/patch.json"))
                .readObject();

        //create JsonMergePatch
        JsonMergePatch mergePatch = Json.createMergePatch(patch);
        JsonValue result = mergePatch.apply(issue);

        //Display results
        System.out.println("Original:");
        System.out.println(issue);

        System.out.println("Patch:");
        System.out.println(patch);

        System.out.println("Result:");
        System.out.println(result);
    }

    /**
     * Scenario 7: Using Java 8 Stream API (Video 3.5)
     *
     * Challenges:
     *
     * 1. Use Stream API to calculate total number of kills
     *    in jasons.json.
     * 2. Filter Jasons who have more than 100 kills.
     * 3. Produce JSON Object containing Jason's names as key and
     *    professions as a value.
     * 4. Group good and bad Jasons.
     */

    private void streamApiScenario(){
        //Load json.json file

        JsonReader  reader = Json.createReader(Main.class.getResourceAsStream("/jsons.json"));
        JsonArray jsons = reader.readArray();


        //Total kills

        int totalKills = jsons.getValuesAs(JsonObject.class).stream()
                .mapToInt(i -> i.getInt("kills", 0))
                .sum();
        System.out.println();
        System.out.println("Total kills:");
        System.out.println(totalKills);

        //Filter more than 100 kills
        JsonArray filtered = jsons.getValuesAs(JsonObject.class).stream()
                .filter(i -> i.getInt("kills", 0) > 100 )
                .collect(JsonCollectors.toJsonArray());
        System.out.println();
        System.out.println("Kills > 100:");
        System.out.println(filtered);

        //Name -> Profession

        JsonObject nameProfession = jsons.stream()
                .collect(JsonCollectors.toJsonObject(
                        i -> i.asJsonObject().getString("name"),
                        i -> i.asJsonObject().getJsonString("profession")
                ));

        System.out.println();
        System.out.println("Name -> Profession:");
        System.out.println(nameProfession);


        //Group by bad guy
        JsonObject grouping = jsons.stream()
                .collect(JsonCollectors.groupingBy(
                        i -> (String.valueOf(i.asJsonObject().getBoolean("bad-guy")))
                ));
        System.out.println();
        System.out.println("Group by bad-guy:");
        System.out.println(grouping);
    }


    /**
     * Scenario 8: Processing big JSON documents (Video 3.6)
     *
     * Challenges:
     *
     * While parse jasons.json file
     *
     * 1. Skip processing of phoneNumbers arrays.
     * 2. Skip processing of third object.
     */

    private void bigJsonScenario(){
        JsonParser parser = Json.createParser(Main.class.getResourceAsStream("/jsons.json"));

        String key = "";
        int objCount = 0;
        while (parser.hasNext()){
            JsonParser.Event event = parser.next();
            System.out.print(event.toString());
            switch (event) {
                case START_OBJECT:
                    if (++objCount == 3) {
                        System.out.print("\nSkipping 3-rd object");
                        parser.skipObject();
                    }
                    break;
                case START_ARRAY:
                    if (key.equals("phoneNumbers")) {
                        System.out.print("\nSkipping array");
                        parser.skipArray();
                    }
                    break;
                case KEY_NAME:
                    key = parser.getString();
                case VALUE_STRING:
                case VALUE_NUMBER:
                    System.out.print(": " + parser.getString());
                    break;
                case VALUE_NULL:
                    System.out.print(": null");
                    break;
                case VALUE_TRUE:
                    System.out.print(": true");
                    break;
                case VALUE_FALSE:
                    System.out.print(": false");
            }

            System.out.println();
        }
    }

}

