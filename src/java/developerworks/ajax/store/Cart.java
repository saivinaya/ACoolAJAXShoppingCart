package developerworks.ajax.store;

import java.math.BigDecimal;
import java.util.*;

/**
 * A very simple shopping Cart
 */
public class Cart {

    private HashMap<Item, Integer> contents;

    /**
     * Creates a new Cart instance
     */
    public Cart() {
        contents = new HashMap<Item, Integer>();
    }

    /**
     * Adds a named item to the cart
     *
     * @param itemName The name of the item to add to the cart
     */
    public void addItem(String itemCode) {
        // creates a new catalog object
        Catalog catalog = new Catalog();
        // checking if itemCode is present in the catalog or not
        if (catalog.containsItem(itemCode)) {
            // getting the item object from the catalog using itemCode
            Item item = catalog.getItem(itemCode);
            int newQuantity = 1;
            // if the item is already in the cart, addding 1 to the quantity
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                newQuantity += currentQuantity.intValue();
            }
            // if the item is not in the cart putting the quantity as 1 into the cart or else changed quantity
            contents.put(item, new Integer(newQuantity));
        }
    }

    /**
     * Removes the named item from the cart
     *
     * @param itemName Name of item to remove
     */
    public void removeItems(String itemCode) {
        // remove the item completely from the cart
        contents.remove(new Catalog().getItem(itemCode));
    }

    /**
     * @return XML representation of cart contents
     */
    public String toXml() {
        StringBuffer xml = new StringBuffer();
        // setting the xml version tag to specify the version
        xml.append("<?xml version=\"1.0\"?>\n");
        // adding timestamp and total to the stringbuffer as an xml format 
        xml.append("<cart generated=\"" + System.currentTimeMillis() + "\" total=\"" + getCartTotal() + "\">\n");

        // iterating through the contents of the cart and adding them to the xml string
        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            // getting the quantity for that item
            int itemQuantity = contents.get(item).intValue();
            //adding the item to the xml string, adding the itemCode
            xml.append("<item code=\"" + item.getCode() + "\">\n");
            //adding name of the Item
            xml.append("<name>");
            xml.append(item.getName());
            xml.append("</name>\n");
            //adding the quantity of the item
            xml.append("<quantity>");
            xml.append(itemQuantity);
            xml.append("</quantity>\n");
            xml.append("</item>\n");
        }

        xml.append("</cart>\n");
        // converting the stringbuffer to a string and returning it 
        return xml.toString();
    }

    // this method is to get the total of the cart
    private String getCartTotal() {
        int total = 0;
        // creating an iterator for the cart and iterating through it
        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            // getting the quantity of the item
            int itemQuantity = contents.get(item).intValue();
            // calculating the total value of the cart (multiplying the quantity and their price)
            total += (item.getPrice() * itemQuantity);
        }
        //returing the total of the cart as a string value
        return "$" + new BigDecimal(total).movePointLeft(2);
    }
}