package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.owlike.genson.Genson;
import org.example.adaptor.Car;
import org.example.adaptor.CarAdapter;
import org.example.formatdate.DateFormatSample1;
import org.example.formatdate.DateFormatSample2;
import org.example.generics.Person;
import org.example.mapping.Nillable1;
import org.example.mapping.Nillable2;
import org.example.mapping.Nillable3;
import org.example.names.Customer;
import org.example.names.MyNamingStrategy;
import org.example.order.Child;
import org.example.order.Parent;
import org.example.orderp.Sample;
import org.example.serialise_deserialize.*;
import org.example.transient1.*;
import org.example.visibility.VisibilityTester;
import org.glassfish.json.JsonProviderImpl;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.BinaryDataStrategy;
import javax.json.bind.config.PropertyOrderStrategy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
                    break;
                case "8":
                    customJsonbEngineScenario();
                    break;
                case "9":
                    jsonbPropertyScenario();
                    break;
                case "10":
                    jsonbPropertyOrderScenario();
                    break;
                case "11":
                    jsonbNillableScenario();
                    break;
                case "12":
                    jsonbTransientScenario();
                    break;
                case "13":
                    jsonbCreatorScenario();
                    break;
                case "14":
                    dateFormatScenario();
                    break;
                case "15":
                    iJsonScenario();
                    break;
                case "16":
                    binaryDataScenario();
                    break;
                case "17":
                    adaptersScenario();
                    break;
                case "18":
                    serializersScenario();
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
        System.out.println("8. Custom JSON-B engine");
        System.out.println("9. Changing properties names");
        System.out.println("10. Customizing properties order (Video 7.3)");
        System.out.println("11. Nillable customization (Video 7.4)");
        System.out.println("12. Ignoring properties (Video 7.5)");
        System.out.println("13. Custom instantiation (Video 7.6)");
        System.out.println("14. Date/time and number format customization (Video 7.7)");
        System.out.println("15. Strict I-JSON compliance (Video 7.8)");
        System.out.println("16. Binary data strategies (Video 7.9)");
        System.out.println("17. Adapters (Video 7.10)");
        System.out.println("18. Serializers/deserializers (Video 7.11)");

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


    /*
    * Custom JSON-B engine
    * */

    private void customJsonbEngineScenario(){
        Jsonb defaultEngine = JsonbBuilder.create();

        JsonbConfig config = new JsonbConfig().withFormatting(true);

        Jsonb jsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
//                .withProvider(new JsonProviderImpl())
                .build();

        JsonObject json = Json.createObjectBuilder()
                .add("name", "Jason Bourne")
                .add("profession", "Super Agent")
                .add("bad-guy", false)
                .add("kills", 1000)
                .add("phoneNumbers", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "123-456-789")))
                .build();
        System.out.println(defaultEngine.toJson(json));
        System.out.println(jsonb.toJson(json));
    }

    /*
    * Changing property names
    * */
    private void jsonbPropertyScenario(){
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withPropertyNamingStrategy(new MyNamingStrategy());

        Jsonb jsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();

        System.out.println(jsonb.toJson(new Customer()));

         String json = "{\"name\": \"Jason Bourne\"}";
         Customer customer = jsonb.fromJson(json, Customer.class);
         System.out.println(customer.getCustomerName());
    }


    /**
     * Customizing properties order.
     */
    private void jsonbPropertyOrderScenario() {
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withPropertyOrderStrategy(PropertyOrderStrategy.REVERSE);
        Jsonb jsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();

        System.out.println(jsonb.toJson(new Sample()));
    }

    /**
     * Nillble customization.
     */
    private void jsonbNillableScenario() {
        JsonbConfig config = new JsonbConfig()
                .withFormatting(true);
        Jsonb jsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();

        System.out.println("Default mapping:");
        System.out.println(jsonb.toJson(new Nillable1()));

        System.out.println("Nillable field:");
        System.out.println(jsonb.toJson(new Nillable2()));

        System.out.println("Nillable on class:");
        System.out.println(jsonb.toJson(new Nillable3()));


        JsonbConfig config1 = new JsonbConfig()
                .withFormatting(true)
                .withNullValues(true);
        Jsonb jsonb1 = JsonbBuilder.newBuilder()
                .withConfig(config1)
                .build();

        System.out.println("withNullValues:");
        System.out.println(jsonb1.toJson(new Nillable1()));
    }

    /**
     * Ignoring properties.
     */
    private void jsonbTransientScenario() {
        final String json = "{\"transientField\": \"Deserialized?!\", \"field\": \"Deserialized!!\"}";

        Jsonb jsonb = JsonbBuilder.create();

        System.out.println("Default mapping:");
        System.out.println(jsonb.toJson(new TransientSample1()));
        System.out.println(jsonb.fromJson(json, TransientSample1.class));


        System.out.println("\n@JsonbTransient on field:");
        System.out.println(jsonb.toJson(new TransientSample2()));
        System.out.println(jsonb.fromJson(json, TransientSample2.class));

        System.out.println("\n@JsonbTransient on getter:");
        System.out.println(jsonb.toJson(new TransientSample3()));
        System.out.println(jsonb.fromJson(json, TransientSample3.class));

        System.out.println("\n@JsonbTransient on setter:");
        System.out.println(jsonb.toJson(new TransientSample4()));
        System.out.println(jsonb.fromJson(json, TransientSample4.class));

    }

    /**
     * Custom instantiation.
     */
    private void jsonbCreatorScenario() {
        final String json = "{\"field1\": \"f1\", \"field2\": \"f2\", \"publicField\": \"JSON-B\"}";

        Jsonb jsonb = JsonbBuilder.create();
        System.out.println(jsonb.fromJson(json, JsonbCreatorSample.class));
    }

    /**
     * Date/time and number format customization.
     */
    private void dateFormatScenario() {
        Jsonb jsonb = JsonbBuilder.create();

        System.out.println("Default mapping:");
        System.out.println(jsonb.toJson(new DateFormatSample1()));

        System.out.println("\n@JsonbDateFormat and @JsonbNumberFormat:");
        System.out.println(jsonb.toJson(new DateFormatSample2()));

        JsonbConfig config = new JsonbConfig()
                .withDateFormat("dd.MM.yyyy", Locale.getDefault());
        Jsonb customJsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();

        System.out.println("\nwithDateFormat:");
        System.out.println(customJsonb.toJson(new DateFormatSample1()));
    }

    /**
     * Strict I-JSON compliance.
     */
    private void iJsonScenario() {
        Jsonb jsonb = JsonbBuilder.create();
        System.out.println(jsonb.toJson("I-JSON"));

        JsonbConfig config = new JsonbConfig()
                .withStrictIJSON(true);
        Jsonb customJsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();
        System.out.println(customJsonb.toJson("I-JSON"));
    }


    /**
     * Binary data strategies.
     */
    private void binaryDataScenario() {
        byte[] bytes = "Yasson is perfect!".getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes);

        Jsonb jsonb = JsonbBuilder.create();
        System.out.println(jsonb.toJson(bytes));

        JsonbConfig config = new JsonbConfig()
                .withBinaryDataStrategy(BinaryDataStrategy.BASE_64);
        Jsonb customJsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();
        System.out.println(customJsonb.toJson(bytes));
    }

    /**
     * Adapters.
     */
    private void adaptersScenario() {
        JsonbConfig config = new JsonbConfig()
                .withAdapters(new CarAdapter());
        Jsonb jsonb = JsonbBuilder.newBuilder()
                .withConfig(config)
                .build();

        Car car = new Car();
        car.setDistance(100.0);
        System.out.println(jsonb.toJson(car));

        final String json = "{\"distance\":200.0}";
        car = jsonb.fromJson(json, Car.class);
        System.out.println(car.getDistance() + " in miles");
    }

    /**
     * Serializers/deserializers.
     */
    private void serializersScenario() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dog("Falco", false));
        animals.add(new Cat("Harris", true));
        Type type = new ArrayList<Animal>() {}.getClass().getGenericSuperclass();

        JsonbConfig config = new JsonbConfig()
                .withFormatting(true)
                .withSerializers(new AnimalSerializer())
                .withDeserializers(new AnimalDeserializer());
        Jsonb jsonb = JsonbBuilder.create(config);

        String json = jsonb.toJson(animals, type);
        System.out.println(json);

        List<Animal> deserialized = jsonb.fromJson(json, type);
        System.out.println(deserialized);
    }
}