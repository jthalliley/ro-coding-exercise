package com.routeone.interview;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoreRegister implements Receipt {

    private Inventory    inventory;
    private List<String> items;    
    
    public void loadInventory(File inventoryFile) throws InvalidInventoryDataException {

	if (inventoryFile == null) {
	    throw new IllegalArgumentException("Null inventoryFile.");
	}

	String inventoryPath = inventoryFile.getPath();

	if (inventoryPath == null) {
	    throw new IllegalArgumentException("Null inventoryFile path.");
	}
	
	inventory = new Inventory(inventoryPath);
	
	inventory.load();
    }
 
    
    public Receipt checkoutOrder(final List<String> items) {
	if (items == null) {
	    return null;
	}

	this.items = items;

	try {
	    // Verify that all items are in inventory...
	    items.forEach(item -> inventory.getComponent(item));

	} catch (final IllegalArgumentException e) {
	    Logger.error(e.getMessage());
	    return null;
	}
	
	return this;
    }

    @Override
    public String getFormattedTotal() {

	BigDecimal total = items.stream()
	    .map(item -> inventory.getPrice(item))
	    .reduce(BigDecimal.ZERO, BigDecimal::add)
	    ;

	return Component.convertToDisplayablePrice(total);
    }
    

    /**
     * Returns ordered items, in order of most expensive item to least. 
     * If items have an identical price, sort in alphabetical (A-Z) order.
     */
    @Override
    public List<String> getOrderedItems() {

	List<Component> sortedComponents = items.stream()
	    .map(item -> inventory.getComponent(item))
	    .sorted(Comparator
		    .comparing(Component::getPrice).reversed()
		    .thenComparing(Component::getName)
	    )
	    .collect(Collectors.toList())
	    ;

	return sortedComponents.stream()
	    .map(component -> component.getName())
	    .collect(Collectors.toList())
	    ;
    }


}
