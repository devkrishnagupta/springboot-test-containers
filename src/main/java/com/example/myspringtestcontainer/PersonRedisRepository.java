package com.example.myspringtestcontainer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRedisRepository extends CrudRepository<PersonRedis, String> {
}
