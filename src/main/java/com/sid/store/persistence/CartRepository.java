package com.sid.store.persistence;

import com.sid.store.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findById(long id);
}
