package zad1;

import java.time.LocalDate;

public class Offer {
    public Offer(int offerID, String loc, String country, LocalDate depDate, LocalDate arrDate, String place, Float price, String currencyCode) {
        this.offerID = offerID;
        this.loc = loc;
        this.country = country;
        this.depDate = depDate;
        this.arrDate = arrDate;
        this.place = place;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    int offerID;
    String loc;
    String country;
    LocalDate depDate;
    LocalDate arrDate;
    String place;
    Float price;
    String currencyCode;
}
