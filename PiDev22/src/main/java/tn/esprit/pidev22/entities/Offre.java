package tn.esprit.pidev22.entities;

import java.util.Date;

public class Offre {
    private int id;
    private Partenaire partenaire_id;
    private String nom_offre;
    private String description_offre;
    private Date date_exp;
    private Date created_at;
    private String photo_url;
    private double prix;

    public Offre(Partenaire partenaire_id, String nom_offre, String description_offre, Date date_exp, String photo_url, double prix) {
        this.partenaire_id = partenaire_id;
        this.nom_offre = nom_offre;
        this.description_offre = description_offre;
        this.date_exp = date_exp;
        this.photo_url = photo_url;
        this.prix = prix;
    }

    public Offre(int id, Partenaire partenaire_id, String nom_offre, String description_offre, Date date_exp, Date created_at, String photo_url, double prix) {

        this.id = id;
        this.partenaire_id = partenaire_id;
        this.nom_offre = nom_offre;
        this.description_offre = description_offre;
        this.date_exp = date_exp;
        this.created_at = created_at;
        this.photo_url = photo_url;
        this.prix = prix;
    }
    public Offre() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partenaire getPartenaire_id() {
        return partenaire_id;
    }

    public void setPartenaire_id(Partenaire partenaire_id) {
        this.partenaire_id = partenaire_id;
    }

    public String getNom_offre() {
        return nom_offre;
    }

    public void setNom_offre(String nom_offre) {
        this.nom_offre = nom_offre;
    }

    public String getDescription_offre() {
        return description_offre;
    }

    public void setDescription_offre(String description_offre) {
        this.description_offre = description_offre;
    }

    public Date getDate_exp() {
        return date_exp;
    }

    public void setDate_exp(Date date_exp) {
        this.date_exp = date_exp;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    @Override
    public String toString() {
        return "Offre{" +
                "partenaire_id='" + partenaire_id + '\'' +
                ", nom_offre='" + nom_offre + '\'' +
                ", description_offre='" + description_offre + '\'' +
                ", date_exp='" + date_exp + '\'' +
                ", reated_at='" + created_at + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", prix='" + prix + '\'' +

                '}';
    }
}
