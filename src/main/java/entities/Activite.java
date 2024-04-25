package entities;

import java.util.List;

public class Activite {
    private int id;
    private int users_id;
    private String nom_act;

    public Activite(int id, int users_id, String nom_act, String image_name, List<Challenge> challenges, String type_act, String location_act, String description_act) {
        this.id = id;
        this.users_id = users_id;
        this.nom_act = nom_act;
        this.image_name = image_name;
        this.challenges = challenges;
        this.type_act = type_act;
        this.location_act = location_act;
        this.description_act = description_act;
    }

    private String image_name;

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    private List<Challenge> challenges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getNom_act() {
        return nom_act;
    }

    public void setNom_act(String nom_act) {
        this.nom_act = nom_act;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getType_act() {
        return type_act;
    }

    public void setType_act(String type_act) {
        this.type_act = type_act;
    }

    public String getLocation_act() {
        return location_act;
    }

    public void setLocation_act(String location_act) {
        this.location_act = location_act;
    }

    public String getDescription_act() {
        return description_act;
    }

    public void setDescription_act(String description_act) {
        this.description_act = description_act;
    }

    private String type_act;
    private String location_act;
    private String description_act;
}
