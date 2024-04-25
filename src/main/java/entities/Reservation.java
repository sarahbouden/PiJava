package entities;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private int id_hotel_id;
    private int id_client;
    private LocalDate date_debut_r;
    private LocalDate date_fin_r;
    private int nbr_perso;
    private String type_room;

    public Reservation(int id, int id_hotel_id, int id_client, LocalDate date_debut_r, LocalDate date_fin_r, int nbr_perso, String type_room) {
        this.id = id;
        this.id_hotel_id = id_hotel_id;
        this.id_client = id_client;
        this.date_debut_r = date_debut_r;
        this.date_fin_r = date_fin_r;
        this.nbr_perso = nbr_perso;
        this.type_room = type_room;
    }

    public Reservation(int id_hotel_id,LocalDate date_debut_r, LocalDate date_fin_r, int nbr_perso, String type_room) {
        this.id = id;
        this.id_hotel_id = id_hotel_id;
        this.date_debut_r = date_debut_r;
        this.date_fin_r = date_fin_r;
        this.nbr_perso = nbr_perso;
        this.type_room = type_room;
    }

    public Reservation() {

    }

   public Reservation(LocalDate dated, LocalDate datef, int nombre, String typer) {
        this.date_debut_r = dated;
        this.date_fin_r = datef;
        this.nbr_perso = nombre;
        this.type_room = typer;
    }

    public Reservation(int idr, int idh, LocalDate dated, LocalDate datef, int nombre, String typer) {
        this.id_hotel_id = idh;
        this.date_debut_r = dated;
        this.date_fin_r = datef;
        this.nbr_perso = nombre;
        this.type_room = typer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hotel_id() {
        return id_hotel_id;
    }

    public void setId_hotel_id(int id_hotel) {
        this.id_hotel_id = id_hotel;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public LocalDate getDate_debut_r() {
        return date_debut_r;
    }

    public void setDate_debut_r(LocalDate date_debut_r) {
        this.date_debut_r = date_debut_r;
    }

    public LocalDate getDate_fin_r() {

        return date_fin_r;
    }

    public void setDate_fin_r(LocalDate date_fin_r) {
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
