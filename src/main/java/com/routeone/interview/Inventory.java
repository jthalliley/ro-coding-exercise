package com.routeone.interview;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Inventory {

    private String         csvPath;
    private Set<Component> components;

    
    public Inventory(final String csvPath) {
	this.csvPath = csvPath;
    }

    public void load() throws InvalidInventoryDataException {

	components = new HashSet<>();

	List<String> lines = new ArrayList<String>();
	
	try {	
	    lines = Files.readAllLines(Paths.get(csvPath), Charset.forName("UTF-8"));
	} catch (final IOException e) {
	    throw new InvalidInventoryDataException("Trouble reading CSV file provided.");
	}
	
	for (String line : lines) {
			
	    String[] fields = line.split(",");
                
	    if (fields.length == 3) {
		Component newComponent   = new Component(fields[0], fields[1], fields[2]);
		boolean   isNewComponent = components.add(newComponent);
                
		if (!isNewComponent) {
		    Logger.warning("Duplicate component in inventory, discarded: " + newComponent.toString());
		}
			    
	    } else {
		throw new InvalidInventoryDataException("Expected 3 fields: name, price, category, but got: " + line);
	    }
	}

	Logger.info("Inventory load successful with " + components.size() + " components.");
    }


    public BigDecimal getPrice(final String item) {
	Component result = getComponent(item);
	
	if (result == null) throw new IllegalArgumentException("No such item (" + item + ") in inventory.");

	return result.getPrice();

    }
    
    public Component getComponent(final String item) {
	if (item == null) throw new IllegalArgumentException("Null item.");

	List<Component> result = components.stream()
	    .filter(component -> component.getName().equalsIgnoreCase(item))
	    .collect(Collectors.toList())
	    ;

	if (result.size() == 0) throw new IllegalArgumentException("No such item (" + item + ") in inventory.");

	return result.get(0);
    }
    
}
