package com.example.myspringtestcontainer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("PersonRedis")
public class PersonRedis {
    @Id
    private String id;
    private String name;
    private int age;
}
