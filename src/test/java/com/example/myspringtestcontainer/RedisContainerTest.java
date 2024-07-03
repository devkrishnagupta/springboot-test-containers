package com.example.myspringtestcontainer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataRedisTest
class RedisContainerTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisContainerTest.class);

    @Container
    @ServiceConnection
    static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:5.0.3"))
            .withExposedPorts(6379)
            .withLogConsumer(new Slf4jLogConsumer(logger));

    @Autowired
    private PersonRedisRepository personRedisRepository;

    @BeforeEach
    void setUp() {
        personRedisRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrievePerson() {
        PersonRedis person = new PersonRedis();
        person.setName("John Doe");
        person.setAge(30);
        personRedisRepository.save(person);

        Optional<PersonRedis> retrievedPerson = personRedisRepository.findById(person.getId());
        assertTrue(retrievedPerson.isPresent());
        System.out.println("---------Person Details-----------");
        System.out.println(retrievedPerson.get());
        assertEquals("John Doe", retrievedPerson.get().getName());
    }
}
