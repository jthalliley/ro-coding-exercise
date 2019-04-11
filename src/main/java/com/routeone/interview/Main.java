package com.routeone.interview;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  This is the main class that exercises the solution.
 */
public class Main {


    public static void main(String[] args) {

	Logger.banner("Load");
	
	if (args.length != 2) {
	    Logger.error("Expected exactly 2 args: csvPath, orderPath");
	    System.exit(2);
	}
	
	try {
	    String csvPath   = args[0];
	    String orderPath = args[1];

	    File inventoryFile = new File(csvPath);
	    
	    StoreRegister storeRegister = new StoreRegister();
	    storeRegister.loadInventory(inventoryFile);
	    
	    List<String> order = readOrder(orderPath);

	    Receipt receipt = storeRegister.checkoutOrder(order);

	    if (receipt == null) {
		Logger.error("Could not create a receipt.");
		
	    } else {
		List<String> orderedItems = storeRegister.getOrderedItems();
		Logger.banner("Ordered Items");
		orderedItems.stream()
		    .forEach(item -> {
			    Logger.out(item);
			}
		    )
		    ;
	    
		Logger.totalsBanner();
		Logger.out("Grand Total of your Order: " + storeRegister.getFormattedTotal());
	    }
	    
	} catch (final InvalidInventoryDataException e) {
	    Logger.error(e.getMessage());
	    System.exit(2);
	}
    }


    private static List<String> readOrder(final String orderPath) throws InvalidInventoryDataException {

	List<String> order;
	
	try {	
	    order = Files.readAllLines(Paths.get(orderPath), Charset.forName("UTF-8"));
	} catch (final IOException e) {
	    throw new InvalidInventoryDataException("Trouble reading order file provided.");
	}

	Logger.info("Order load successful with " + order.size() + " items.");

	return order;
    }

}

