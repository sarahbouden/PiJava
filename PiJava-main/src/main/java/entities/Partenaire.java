package entities;

import java.util.List;

public class Partenaire {
    private int id;
    private String nom_p;
    private String type_p;
    private String description_p;
    private String adresse;
    private String photo_url;
    private List<Offre> offres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_p() {
        return nom_p;
    }

    public void setNom_p(String nom_p) {
        this.nom_p = nom_p;
    }

    public String getType_p() {
        return type_p;
    }

    public void setType_p(String type_p) {
        this.type_p = type_p;
    }

    public String getDescription_p() {
        return description_p;
    }

    public void setDescription_p(String description_p) {
        this.description_p = description_p;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


}
