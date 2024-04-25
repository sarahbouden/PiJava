package entities;

public class Publication {
    private int id;
    private String titre;
    private String description;
    private String url_ressource;
    private float rating;
    private float somme;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_ressource() {
        return url_ressource;
    }

    public Publication(int id, String titre, String description, String url_ressource, float rating, float somme) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.url_ressource = url_ressource;
        this.rating = rating;
        this.somme = somme;
    }

    public void setUrl_ressource(String url_ressource) {
        this.url_ressource = url_ressource;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getSomme() {
        return somme;
    }

    public void setSomme(float somme) {
        this.somme = somme;
    }
}

