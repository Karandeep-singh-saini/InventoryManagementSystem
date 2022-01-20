package com.ks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ks.model.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem,Long> {

	
}
