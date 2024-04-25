package TuniWonders.entities;

import java.util.List;

public class User {
    private int id;
    private int reservation_id;
    private int recompense_id;
    private int activite_id;
    private String username;
    private String password;
    private String vpwd;
    private String roles;
    private int cin;
    private String email;
    private String adresse;
    private int num_tel;

    private List<Challenge> challenges;

    public int getActivite_id() {
        return activite_id;
    }

    public void setActivite_id(int activite_id) {
        this.activite_id = activite_id;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }




    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getRecompense_id() {
        return recompense_id;
    }

    public void setRecompense_id(int recompense_id) {
        this.recompense_id = recompense_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVpwd() {
        return vpwd;
    }

    public void setVpwd(String vpwd) {
        this.vpwd = vpwd;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public User() {
    }

    public User(String username, String password, String vpwd, String roles, int cin, String email, String adresse, int num_tel) {
        this.username = username;
        this.password = password;
        this.vpwd = vpwd;
        this.roles = roles;
        this.cin = cin;
        this.email = email;
        this.adresse = adresse;
        this.num_tel = num_tel;
    }
    public User(int id, String username, String password, String vpwd, String roles, int cin, String email, String adresse, int num_tel) {
        this.id=id;
        this.username = username;
        this.password = password;
        this.vpwd = vpwd;
        this.roles = roles;
        this.cin = cin;
        this.email = email;
        this.adresse = adresse;
        this.num_tel = num_tel;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", reservation_id=" + reservation_id +
                ", recompense_id=" + recompense_id +
                ", activite_id=" + activite_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", vpwd='" + vpwd + '\'' +
                ", roles='" + roles + '\'' +
                ", cin=" + cin +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", num_tel=" + num_tel +
                ", challenges=" + challenges +
                '}';
    }
}
