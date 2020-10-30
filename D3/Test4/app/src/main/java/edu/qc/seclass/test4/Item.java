package edu.qc.seclass.test4;

import java.util.List;

public class Item {
    public boolean check;
    public String itemName;
    public List<String> itemPrice;
    public double itemQuantity;

    public Item(boolean cb, String name, List<String> price, int quantity){
        this.check = cb;
        this.itemName = name;
        this.itemPrice = price;
        this.itemQuantity = quantity;
    }
}
