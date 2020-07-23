package org.example.model;



import java.util.List;

public class Jason {
    private Integer id;
    private String name;
    private String profession;

    private List<PhoneNumber> phoneNumbers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }


    @Override
    public String toString() {
        return "Jason{" +
                "name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}

