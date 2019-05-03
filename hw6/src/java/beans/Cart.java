package beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;


public class Cart implements Serializable {
    
    private ArrayList<Line> items;
    
    public Cart() {
        items = new ArrayList<>();
    }

    public ArrayList<Line> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }

    public void addItem(Line item) {
        String title = item.getBook().getTitle();
        int quantity = item.getQuantity();
        for (int i = 0; i < items.size(); i++) {
            Line lineItem = items.get(i);
            if (lineItem.getBook().getTitle().equals(title)) {
                lineItem.setQuantity(quantity);
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(Line item) {
        String title = item.getBook().getTitle();
        for (int i = 0; i < items.size(); i++) {
            Line lineItem = items.get(i);
            if (lineItem.getBook().getTitle().equals(title)) {
                items.remove(i);
                return;
            }
        }
    }
    
    public double getTotal() {
        double grandTotal = 0.0;
        for(Line l: items) {
            grandTotal += l.getTotal();
        }
        return grandTotal;
    }
    
    public String getTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
}
