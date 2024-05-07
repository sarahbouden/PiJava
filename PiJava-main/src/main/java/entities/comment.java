package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class comment {
    private int id;
    private String content;
    private Date created_at;
    private List<User> participants;
    private List<Activite> activites_id;

    public comment(int id, String content, Date created_at, List<User> participants, List<Activite> activites_id) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
        this.participants = participants;
        this.activites_id = activites_id;
    }

    public comment(String content, Date created_at, List<User> participants, List<Activite> activites_id) {
        this.content = content;
        this.created_at = created_at;
        this.participants = participants;
        this.activites_id = activites_id;
    }

    public comment() {
    }
    public Activite getActivite() {
        if (activites_id != null && !activites_id.isEmpty()) {
            return activites_id.get(0); // Retourne la première activité de la liste
        } else {
            return null; // Retourne null si la liste des activités est vide ou nulle
        }
    }

    public List<Activite> getActivites_id() {
        return activites_id;
    }

    public void setActivity(List<Activite> activites_id) {
        this.activites_id = activites_id;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }


    @Override
    public String toString() {
        return content;
    }
}
