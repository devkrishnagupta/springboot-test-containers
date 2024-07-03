package com.example.myspringtestcontainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Testcontainers
@SpringBootTest
class MongoDBContainerTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private PersonRepository personRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrievePerson() {
        Person person = new Person();
        person.setName("John Doe");
        person.setAge(30);
        personRepository.save(person);

        List<Person> persons = personRepository.findAll();
        assertEquals(1, persons.size());
        System.out.println("----------Person Details---------");
        System.out.println(persons);
        assertEquals("John Doe", persons.get(0).getName());
    }
}
