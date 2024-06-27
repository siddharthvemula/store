package com.sid.store.persistence;

import com.sid.store.model.Item;
import com.sid.store.model.Temp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
//    List<Temp> findByLastName(String lastName);

    Item findById(long id);
}
