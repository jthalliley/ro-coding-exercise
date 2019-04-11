package com.routeone.interview;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Component implements Comparable<Component> {

    private String     name;
    private BigDecimal price;
    private String     category;
    

    public Component(final String name, final String price, final String category) throws InvalidInventoryDataException {
	this.name     = "".equals(name)     ? null : name;
	this.price    = price == null       ? null : validatedPrice(price);
	this.category = "".equals(category) ? null : category;
    }

    public String     getName()     { return name;     }
    public BigDecimal getPrice()    { return price;    }
    public String     getCategory() { return category; }


    @Override
    public String toString() {
	return "Component ("
	    + "name: "       + name
	    + ", price: "    + convertToDisplayablePrice(getPrice())
	    + ", category: " + category
	    + ")";
    }

    @Override
    public boolean equals(final Object other) {
	if (other != null && other instanceof Component) {
	    Component that = (Component) other;
	    return this.name != null && this.name.equalsIgnoreCase(that.getName());
	}

	return false;
    }

    @Override
    public int hashCode() {
	return getName() == null || getCategory() == null
	    ? 0
	    : (getName() + getCategory()).hashCode();
    }

    @Override
    public int compareTo(final Component that) {
	return this.getName() == null || this.getCategory() == null
	    ? -1
	    : that == null || that.getName() == null || that.getCategory() == null
	    ? +1
	    : (this.getName() + this.getCategory()).compareTo(that.getName() + that.getCategory());
    }

    public static String convertToDisplayablePrice(BigDecimal price) {

	NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);
	usdFormat.setMaximumFractionDigits(2);

	return usdFormat.format(price);
    }
	
    //--------------------------------- P R I V A T E ---------------------------------//
    
    private String getDisplayablePrice() {

	NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);
	usdFormat.setMaximumFractionDigits(2);

	return usdFormat.format(getPrice());
    }
    
    private BigDecimal validatedPrice(String price) throws InvalidInventoryDataException {
	try {
	    BigDecimal result = new BigDecimal(price);

	    if (result.compareTo(BigDecimal.ZERO) <= 0) {
		throw new InvalidInventoryDataException("Price cannot be <= 0.");
	    }

	    return result.setScale(2, RoundingMode.HALF_EVEN);
	    
	} catch (final ArithmeticException | NumberFormatException e) {
	    throw new InvalidInventoryDataException("Could not convert (" + price + ") to a price.");
	}
    }    
}
