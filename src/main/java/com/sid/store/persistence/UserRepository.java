package com.sid.store.persistence;

import com.sid.store.model.Item;
import com.sid.store.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);
}
