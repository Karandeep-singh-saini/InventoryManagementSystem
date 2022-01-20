package com.ks.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ks.model.InventoryItem;
import com.ks.repository.InventoryItemRepository;

@Service
public class InventoryItemService implements IInventoryItemService  {

	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Override
	public InventoryItem getById(long id) {
		return inventoryItemRepository.getById(id);
	}

	@Override
	public InventoryItem save(InventoryItem inventoryItem) {
		return inventoryItemRepository.save(inventoryItem);
	}

	@Override
	public List<InventoryItem> getAllInventoryItems() {
		return inventoryItemRepository.findAll();
	}

	@Override
	public boolean deleteInventoryItemById(long id) {
		inventoryItemRepository.deleteById(id);
		return true;

	}

	@Override
	public InventoryItem updateInventoryItem(long id, InventoryItem inventoryItem) {
		inventoryItem.setId(id);
		return inventoryItemRepository.save(inventoryItem);
	}


	
	
}
