package entities;

import java.util.Date;

public class Recompense {
    private int id;
    private String nom_recp;
    private String niveau;
    private String description_recp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recompense(String nom_recp, String niveau, String description_recp) {
        this.nom_recp = nom_recp;
        this.niveau = niveau;
        this.description_recp = description_recp;
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

    @Override
    public String toString() {
        return "Recompense{" +
                "nom_recp='" + nom_recp + '\'' +
                ", niveau='" + niveau + '\'' +
                ", description_recp='" + description_recp + '\'' +
                '}';
    }
}
