package services;

import entities.Activite;
import entities.comment;

import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceComment implements IService<comment> {
    static Connection connection;

    public  ServiceComment(){
        connection  = MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(comment comment) throws SQLException {
        String req = "INSERT INTO comment (content, created_at) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setString(1, comment.getContent());
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis())); // Utilisation de la date actuelle
            pstmt.executeUpdate();
        }
    }

    @Override
    public void modifier(comment comment) throws SQLException {
        String req = "UPDATE comment SET content = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setString(1, comment.getContent());
            pstmt.setInt(2, comment.getId());
            pstmt.executeUpdate();
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM comment WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<comment> afficher() throws SQLException {
        List<comment> commentaires = new ArrayList<>();
        String req = "SELECT * FROM comment";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(req)) {
            while (rs.next()) {
                comment commentaire = new comment();
                commentaire.setId(rs.getInt("id"));
                commentaire.setContent(rs.getString("content"));
                commentaire.setCreated_at(rs.getTimestamp("created_at"));
                commentaires.add(commentaire);
            }
        }
        return commentaires;
    }

    public List<comment> getCommentsByActivity(Activite activite) {

            List<comment> comments = new ArrayList<>();
            String req = "SELECT * FROM comment WHERE activites_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(req)) {
                pstmt.setInt(1, activite.getId());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    comment comment = new comment();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    comment.setCreated_at(rs.getTimestamp("created_at"));
                    comments.add(comment);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return comments;
    }
}

