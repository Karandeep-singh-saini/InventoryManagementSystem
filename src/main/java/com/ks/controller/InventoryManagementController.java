package com.ks.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ks.model.InventoryItem;
import com.ks.service.IInventoryItemService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Controller
public class InventoryManagementController {

	@Autowired
	private IInventoryItemService inventoryItemService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("itemInventorys", inventoryItemService.getAllInventoryItems());
		return "index";
	}

	@GetMapping("/createInventoryItemForm")
	public String addItem(InventoryItem inventoryItem) {
		return "createInventoryItem.html";
	}

	// Create InventoryItem by passing inventoryItem details
	@PostMapping("/inventoryItem/create")
	public String createInventoryItem(@ModelAttribute InventoryItem inventoryItem, Model model) {
		inventoryItemService.save(inventoryItem);
		model.addAttribute("itemInventorys", inventoryItemService.getAllInventoryItems());
		return "index";
	}

	@GetMapping("/inventoryItem/edit/{id}")
	public String editInventoryItem(@PathVariable long id, Model model) {
		InventoryItem inventoryItem = inventoryItemService.getById(id);
		model.addAttribute("inventoryItem", inventoryItem);
		return "updateInventoryItem.html";
	}

	@PostMapping("/inventoryItem/update/{id}")
	public String updateInventoryItem(@PathVariable long id, @ModelAttribute InventoryItem inventoryItem, Model model) {
		InventoryItem updatedInventoryItem = inventoryItemService.updateInventoryItem(id, inventoryItem);
		if (updatedInventoryItem != null) {
			model.addAttribute("updateMessage", "Updated InventoryItem with id :" + id);
		}
		model.addAttribute("itemInventorys", inventoryItemService.getAllInventoryItems());
		return "index";
	}

	// Delete InventoryItem by passing inventoryItem ID
	@GetMapping("/inventoryItem/delete/{id}")
	public String deleteInventoryItemById(@PathVariable long id, Model model) {
		if (inventoryItemService.deleteInventoryItemById(id)) {
			model.addAttribute("deleteMessage", "Deleted InventoryItem with id :" + id);
		}
		model.addAttribute("itemInventorys", inventoryItemService.getAllInventoryItems());
		return "index.html";
	}

	@GetMapping("/exportDataToCSV")
	public void exportDataToCSV(HttpServletResponse response) {

		String filename = "inventoryItems.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
		try {

			StatefulBeanToCsv<InventoryItem> writer = new StatefulBeanToCsvBuilder<InventoryItem>(response.getWriter())
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR)
					.withOrderedResults(false).build();

			writer.write(inventoryItemService.getAllInventoryItems());
		} catch (CsvDataTypeMismatchException e) {

			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
