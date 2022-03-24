package org.fungover.webapi.repositories;

import org.fungover.webapi.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    @Query("select u.name from User u")
    List<String> getNameOnly();
}
