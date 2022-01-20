package com.ks.service;

import java.util.List;

import com.ks.model.InventoryItem;

public interface IInventoryItemService {

	public InventoryItem getById(long id);
	public InventoryItem save(InventoryItem inventoryItem);
	public List<InventoryItem> getAllInventoryItems();
	public boolean deleteInventoryItemById(long id);
	public InventoryItem updateInventoryItem(long id, InventoryItem inventoryItem);
}
