package entities;

import java.util.List;

public class Hotel {
    private int id;
    private String name_h;
    private String location;


    public Hotel(int id, String name_h, String location, float rating, int num_h, String description, String photo_url) {
        this.id = id;
        this.name_h = name_h;
        this.location = location;
        this.description = description;
        this.photo_url = photo_url;
    }

    private String description;
    private String photo_url;


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

}
