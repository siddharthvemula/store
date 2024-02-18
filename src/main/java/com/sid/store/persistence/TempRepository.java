package com.sid.store.persistence;

import com.sid.store.model.Temp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TempRepository extends CrudRepository<Temp, Long> {
    List<Temp> findByLastName(String lastName);

    Temp findById(long id);
}
