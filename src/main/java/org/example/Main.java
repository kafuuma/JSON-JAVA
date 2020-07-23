package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.owlike.genson.Genson;
import org.example.generics.Person;
import org.example.order.Child;
import org.example.order.Parent;
import org.example.visibility.VisibilityTester;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        while (true) {
            printOptions();

            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "q":
                case "Q":
                    return;
                case "1":
                    basicTypesScenario();
                    break;
                case "2":
                    collectionsScenario();
                    break;
                case "3":
                    datesScenario();
                    break;
                case "4":
                    fieldOrderScenario();
                    break;
                case "5":
                    visibilityPolicyScenario();
                    break;
                case "6":
                    jsonpTypesScenario();
                    break;
                case "7":
                    genericTypesScenario();
            }
        }
    }

    private void printOptions() {
        System.out.println("JSON with Java EE: Hand on Training");
        System.out.println("===================================");
        System.out.println();
        System.out.println("Choose a scenario to run or press 'Q' to exit:");
        System.out.println();
        System.out.println("1. Mapping of basic and JDK specific types");
        System.out.println("2. Mapping of collections and enums");
        System.out.println("3. Mapping of dates");
        System.out.println("4. Fields order");
        System.out.println("5. Visibility policy");
        System.out.println("6. Mapping of JSON-P types");
        System.out.println("7. Mapping of generic types");

        System.out.println();
        System.out.println("===================================");
    }

    /**
     * Mapping of basic and JDK specific types.
     */
    private void basicTypesScenario() {
        Jsonb jsonb = JsonbBuilder.create();

        //String
        String str = "JSON Binding";
        System.out.println("String: "+str);

        String toJson = jsonb.toJson(str);
        System.out.println("toJson: "+toJson);


        //Big Decimal
        System.out.println();
        BigDecimal big = BigDecimal.TEN;
        System.out.println("BigDecimal: "+big);

        toJson = jsonb.toJson(big);
        System.out.println("toJson:  "+ big);

        BigDecimal bigFromJson = jsonb.fromJson(toJson, BigDecimal.class);
        System.out.println("fromJson:  "+ bigFromJson);


    }

    /*
     * Mapping of collections and enums
     * */

    private void collectionsScenario(){
        Jsonb jsonb = JsonbBuilder.create();
        //List

        List list = new ArrayList();
        list.add(10);
        list.add("String");
        list.add(Arrays.asList(1,2,3));
        System.out.println("Original list: " + list);

        String json = jsonb.toJson(list);
        System.out.println("toJson:  "+json);

        list.clear();
        list = jsonb.fromJson(json, ArrayList.class );
        System.out.println("fromJson:  "+ list);

        //Enum
        System.out.println();
        json = jsonb.toJson(Language.RU);
        System.out.println("Enum toJson:  "+ json);

        Language lang = jsonb.fromJson(json, Language.class);
        System.out.println("Enum fromJson:  "+ lang);

    }

    enum Language {
        EN, CZ, RU
    }

    /**
     * Mapping of dates.
     */
    private void datesScenario() {
        // java.util.Date
        Date date = new Date();
        printResults("Date", date);

        // java.util.Calendar
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2018, 01, 05);
        printResults("Calendar", cal);

        // java.time.Instant
        Instant instant = Instant.now();
        printResults("Instant", instant);

        // java.time.Duration
        Duration duration = Duration.ofHours(5).plusMinutes(4);
        printResults("Duration", duration);

        // java.time.Period
        Period period = Period.between(LocalDate.of(1973, Month.JULY, 26), LocalDate.now());
        printResults("Period", period);
    }

    private void printResults(String name, Object object) {
        Jsonb jsonb = JsonbBuilder.create();
        Gson gson = new Gson();
        Genson genson = new Genson();
        ObjectMapper jackson = new ObjectMapper();
        jackson.registerModule(new JavaTimeModule());

        System.out.println(name + " (JSON-B)  : " + jsonb.toJson(object));
        System.out.println(name + " (Gson)    : " + gson.toJson(object));
        System.out.println(name + " (Genson)  : " + genson.serialize(object));

        try {
            System.out.println(name + " (Jackson) : " + jackson.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    /*
    * Fields order scenario
    * */

    private void fieldOrderScenario(){
        printResults("Parent", new Parent());
        printResults("Child", new Child());
    }

    /**
     * Visibility policy scenario.
     */
    private void visibilityPolicyScenario() {
        printResults("", new VisibilityTester());
    }

    /**
     * JSON-P types scenario.
     */
    private void jsonpTypesScenario() {
        JsonObject jason = Json.createObjectBuilder()
                .add("name", "Jason Bourne")
                .add("profession", "Super Agent")
                .add("bad-guy", false)
                .add("kills", 1000)
                .add("phoneNumbers", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "123-456-789")))
                .build();

        printResults("", jason);
    }

    public List<Person> classFieldJasons = new ArrayList<>();
    /**
     * Generic types scenario.
     */
    private void genericTypesScenario() {
        Jsonb jsonb = JsonbBuilder.create();
        List<Person> jasons = jsonb.fromJson(
                Main.class.getResourceAsStream("/jasons.json"),
                new ArrayList<Person>(){}.getClass().getGenericSuperclass()
        );
        System.out.println(jasons);

        try {
            classFieldJasons = jsonb.fromJson(
                    Main.class.getResourceAsStream("/jasons.json"),
                    Main.class.getField("classFieldJasons").getGenericType()
            );
            System.out.println(classFieldJasons);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}