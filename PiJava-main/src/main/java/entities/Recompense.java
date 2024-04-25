package entities;

import java.util.Date;

public class Recompense {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_recp() {
        return nom_recp;
    }

    public void setNom_recp(String nom_recp) {
        this.nom_recp = nom_recp;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDescription_recp() {
        return description_recp;
    }

    public void setDescription_recp(String description_recp) {
        this.description_recp = description_recp;
    }

    private String nom_recp;
    private String niveau;
    private String description_recp;
}
