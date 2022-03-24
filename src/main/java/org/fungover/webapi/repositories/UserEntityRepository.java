package org.fungover.webapi.repositories;

import org.fungover.webapi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
