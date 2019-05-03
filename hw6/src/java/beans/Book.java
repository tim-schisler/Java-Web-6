package beans;

import java.io.Serializable;
import java.text.NumberFormat;


public class Book implements Serializable {
    
    private String coverUrl;
    private String title;
    private double price;

    public Book() {
        coverUrl = "";
        title = "";
        price = 0.0;
    }

    public Book(String coverUrl, String title, double price) {
        this.coverUrl = coverUrl;
        this.title = title;
        this.price = price;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getPriceCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }
    
}
