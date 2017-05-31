package ru.otus.hw8;

import ru.otus.hw8.dto.Address;
import ru.otus.hw8.dto.Person;
import ru.otus.hw8.json.JsonWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stas on 30.05.2017
 */
public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        Person person = createPerson();
        person.setAddress(createAddresses());

        Person[] persons = new Person[3];
        persons[0] = person;
        persons[1] = new Person();

        JsonWriter jsonWriter = new JsonWriter();

        List<Person> list = new ArrayList<>();
        list.add(person);
        list.add(null);

        int[] a = {1, 2, 3};
        char[] c = {'a', 'b', 'c'};

        System.out.println("------------------------------------------------------");
        System.out.println("                  JsonWriter Demo                     ");
        System.out.println("------------------------------------------------------");
        System.out.println("Person:       " + jsonWriter.toJsonString(person) + "\n");
        System.out.println("Person[]:     " + jsonWriter.toJsonString(persons) + "\n");
        System.out.println("String:       " + jsonWriter.toJsonString(person.getName()) + "\n");
        System.out.println("Address:      " + jsonWriter.toJsonString(person.getAddress()) + "\n");
        System.out.println("List<Person>: " + jsonWriter.toJsonString(list) + "\n");
        System.out.println("null:         " + jsonWriter.toJsonString(null) + "\n");
        System.out.println("int[]:        " + jsonWriter.toJsonString(a) + "\n");
        System.out.println("char[]:       " + jsonWriter.toJsonString(c) + "\n");
        System.out.println("------------------------------------------------------");
    }

    private static Person createPerson() {
        Person person = new Person();
        person.setName("Sherlock Holmes");
        person.setAge(40);
        person.setAddress(createAddresses());
        return person;
    }

    private static Address createAddresses() {
        Address address = new Address();
        address.setCity("London");
        address.setStreet("Baker st.");
        address.setHouseNumber(221L);
        address.setCh('b');
        return address;
    }

}
