package entities;

import java.util.Date;

public class Reservation {
    private int id;
    private int id_hotel;

    public Reservation(int id, int id_hotel, int id_client, Date date_debut_r, Date date_fin_r, int nbr_perso, String type_room) {
        this.id = id;
        this.id_hotel = id_hotel;
        this.id_client = id_client;
        this.date_debut_r = date_debut_r;
        this.date_fin_r = date_fin_r;
        this.nbr_perso = nbr_perso;
        this.type_room = type_room;
    }

    private int id_client;
    private Date date_debut_r;
    private Date date_fin_r;
    private int nbr_perso;
    private String type_room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public Date getDate_debut_r() {
        return date_debut_r;
    }

    public void setDate_debut_r(Date date_debut_r) {
        this.date_debut_r = date_debut_r;
    }

    public Date getDate_fin_r() {
        return date_fin_r;
    }

    public void setDate_fin_r(Date date_fin_r) {
        this.date_fin_r = date_fin_r;
    }

    public int getNbr_perso() {
        return nbr_perso;
    }

    public void setNbr_perso(int nbr_perso) {
        this.nbr_perso = nbr_perso;
    }

    public String getType_room() {
        return type_room;
    }

    public void setType_room(String type_room) {
        this.type_room = type_room;
    }




}
