package org.smartshoppers.dao;

import java.util.List;

import org.smartshoppers.model.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepo extends JpaRepository<ShoppingItem, String>{
	public List<ShoppingItem> findAllByStore(String store);
}
