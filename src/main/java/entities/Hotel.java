package entities;

import javafx.scene.control.TextField;

import java.util.List;

public class Hotel {
    private int id;
    private String name_h;
    private String location;
    private int rating;
    private String description;
    private String photo_url;

    private int price;



    public Hotel(int id, String name_h, String location, int rating,  String description, String photo_url, int price) {
        this.id = id;
        this.name_h = name_h;
        this.location = location;
        this.rating = rating;
        this.description = description;
        this.photo_url = photo_url;
        this.price = price;
    }


    public Hotel(String name_h, String location, int rating,  String description, String photo_url, int price) {
    }
    public Hotel(String name_h, String location, int rating,  String description, String photo_url) {
    }

    public Hotel() {

    }
    public Hotel(String name_h, String location, String description, String photo_url) {
        this.name_h = name_h;
        this.location = location;
        this.description = description;
        this.photo_url = photo_url;
    }
    public Hotel(int id, String name_h, String location, String description, String photo_url) {
    }



    public Hotel(TextField id, TextField name, TextField locat, TextField description, TextField ftUrl) {
    }

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
