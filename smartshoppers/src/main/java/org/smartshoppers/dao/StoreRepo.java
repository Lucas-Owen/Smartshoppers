package org.smartshoppers.dao;

import java.util.Collection;

import org.smartshoppers.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepo extends JpaRepository<Store, String>{
	
	@Query("SELECT s FROM Store s WHERE s NOT IN (SELECT u.store FROM User u)")
	public Collection<Store> findAllWithoutManager();
}
