package entities;

import java.util.Date;
import java.util.List;

public class Challenge {
    private int id;
    private String titre_ch;
    private Date date_debut_ch;
    private Date date_fin_ch;
    private String objectif_ch;
    private List<User> participants;
    private List<Activite> activity;



    public Challenge() {
    }

    public Challenge(int id, String titre_ch, Date date_debut_ch, Date date_fin_ch, String objectif_ch) {
        this.id = id;
        this.titre_ch = titre_ch;
        this.date_debut_ch = date_debut_ch;
        this.date_fin_ch = date_fin_ch;
        this.objectif_ch = objectif_ch;
    }

    public Challenge(String titre_ch, Date date_debut_ch, Date date_fin_ch, String objectif_ch, List<User> participants, List<Activite> activity) {
        this.titre_ch = titre_ch;
        this.date_debut_ch = date_debut_ch;
        this.date_fin_ch = date_fin_ch;
        this.objectif_ch = objectif_ch;
        this.participants = participants;
        this.activity = activity;
    }

    public Challenge(String titre_ch, String objectif_ch, Date date_debut_ch, Date date_fin_ch) {
        this.titre_ch = titre_ch;
        this.date_debut_ch = date_debut_ch;
        this.date_fin_ch = date_fin_ch;
        this.objectif_ch = objectif_ch;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<Activite> getActivity() {
        return activity;
    }

    public void setActivity(List<Activite> activity) {
        this.activity = activity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre_ch() {
        return titre_ch;
    }

    public void setTitre_ch(String titre_ch) {
        this.titre_ch = titre_ch;
    }

    public Date getDate_debut_ch() {
        return date_debut_ch;
    }

    public void setDate_debut_ch(Date date_debut_ch) {
        this.date_debut_ch = date_debut_ch;
    }

    public Date getDate_fin_ch() {
        return date_fin_ch;
    }

    public void setDate_fin_ch(Date date_fin_ch) {
        this.date_fin_ch = date_fin_ch;
    }

    public String getObjectif_ch() {
        return objectif_ch;
    }

    public void setObjectif_ch(String objectif_ch) {
        this.objectif_ch = objectif_ch;
    }

    public List<Activite> getActivite() {
        return activity;
    }

    public void setActivite(List<Activite> activity) {
        this.activity = activity;
    }
    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", titre_ch='" + titre_ch + '\'' +
                ", date_debut_ch=" + date_debut_ch +
                ", date_fin_ch=" + date_fin_ch +
                ", objectif_ch='" + objectif_ch + '\'' +
                '}';
    }



}
