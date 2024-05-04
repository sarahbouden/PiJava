package entities;

import java.util.Date;
import java.util.List;

public class comment {
    private int id;
    private String content;
    private Date created_at;
    private List<User> participants;
    private List<Activite> activity;

    public comment(int id, String content, Date created_at, List<User> participants, List<Activite> activity) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
        this.participants = participants;
        this.activity = activity;
    }

    public comment(String content, Date created_at, List<User> participants, List<Activite> activity) {
        this.content = content;
        this.created_at = created_at;
        this.participants = participants;
        this.activity = activity;
    }

    public comment() {
    }

    public List<Activite> getActivity() {
        return activity;
    }

    public void setActivity(List<Activite> activity) {
        this.activity = activity;
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
        return "comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                ", participants=" + participants +
                ", activity=" + activity +
                '}';
    }
}
