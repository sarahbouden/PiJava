package entities;

import java.util.List;

public class Hotel {
    private int id;
    private String name_h;
    private String location;
    private float rating;
    private int num_h;

    public Hotel(int id, String name_h, String location, float rating, int num_h, String description, String photo_url, float price, List<Reservation> reservations) {
        this.id = id;
        this.name_h = name_h;
        this.location = location;
        this.rating = rating;
        this.num_h = num_h;
        this.description = description;
        this.photo_url = photo_url;
        this.price = price;
        this.reservations = reservations;
    }

    private String description;
    private String photo_url;
    private float price;

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    private List<Reservation> reservations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_h() {
        return name_h;
    }

    public void setName_h(String name_h) {
        this.name_h = name_h;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNum_h() {
        return num_h;
    }

    public void setNum_h(int num_h) {
        this.num_h = num_h;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
