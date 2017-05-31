package ru.otus.hw8.json;

import com.google.gson.Gson;
import org.junit.Test;
import ru.otus.hw8.dto.Address;
import ru.otus.hw8.dto.Person;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by stas on 31.05.2017
 */
public class JsonWriterTest {

    JsonWriter jsonWriter = new JsonWriter();
    Gson gson = new Gson();

    @Test
    public void testPersonToJsonString() throws Exception {
        Person person = createPerson();

        String result = jsonWriter.toJsonString(person);
        String expected = gson.toJson(person);

        assertEquals(expected, result);

    }

    @Test
    public void testPersonArrayToJsonString() {
        Person[] persons = new Person[3];
        persons[0] = createPerson();
        persons[1] = new Person();

        String result = jsonWriter.toJsonString(persons);
        String expected = gson.toJson(persons);

        assertEquals(expected, result);
    }

    @Test
    public void testPersonListToJsonString() {
        List<Person> personList = new ArrayList<>();
        personList.add(createPerson());
        personList.add(null);
        personList.add(new Person());

        String result = jsonWriter.toJsonString(personList);
        String expected = gson.toJson(personList);

        assertEquals(expected, result);
    }

    @Test
    public void testPrimitiveArrayToJsonString() {
        int[] a = {1, 2, 3};
        char[] c = {'a', 'b', 'c'};

        String result = jsonWriter.toJsonString(a);
        String expected = gson.toJson(a);

        assertEquals(expected, result);

        result = jsonWriter.toJsonString(c);
        expected = gson.toJson(c);

        assertEquals(expected, result);
    }

    @Test
    public void testNullToJsonString() {
        String result = jsonWriter.toJsonString(null);
        String expected = gson.toJson(null);

        assertEquals(expected, result);
    }

    private Person createPerson() {
        Person person = new Person();
        person.setName("Sherlock Holmes");
        person.setAge(40);
        person.setAddress(createAddresses());
        return person;
    }

    private Address createAddresses() {
        Address address = new Address();
        address.setCity("London");
        address.setStreet("Baker st.");
        address.setHouseNumber(221L);
        address.setCh('b');
        return address;
    }

}