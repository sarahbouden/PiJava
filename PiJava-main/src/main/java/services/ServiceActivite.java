package services;

import entities.Activite;
import entities.Challenge;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivite implements IService<Activite> {
    static Connection connection;

    public  ServiceActivite(){
        connection  = MyDataBase.getInstance().getConnection();
    }

    public static void addActiviteChallenge(int actId, int id)
        throws SQLException {
            String qry = "INSERT INTO challenge_activite (challenge_id, activite_id) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(qry);
            pstmt.setInt(1, id);
            pstmt.setInt(2, actId  );
            pstmt.executeUpdate();

            System.out.println("Activity added to event successfully!");
        }


    @Override
    public void ajouter(Activite activite) throws SQLException {
        String req ="INSERT INTO activite ( nom_act,image_name,type_act,location_act,description_act)"+
                "VALUES ('"+activite.getNom_act()+"','"+activite.getImage_name()+"','"+activite.getType_act()+"','"+activite.getLocation_act()+"','"+activite.getDescription_act()+"')";
        Statement statement=connection.createStatement();
        statement.executeUpdate(req);

        System.out.println("challenge ajoute");
    }

    @Override
    public void modifier(Activite activite) throws SQLException {
        String req = "UPDATE activite SET nom_act = ?, image_name = ?, type_act = ?,location_act= ?, description_act = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(req); {
            pstmt.setString(1, activite.getNom_act());
            pstmt.setString(2, activite.getImage_name());
            pstmt.setString(3, activite.getType_act());
            pstmt.setString(4, activite.getDescription_act());
            pstmt.setString(5, activite.getLocation_act());
            pstmt.setInt(6,activite.getId());


            pstmt.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM activite WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(req);{
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }

    }

    // ServiceActivite.java
    @Override
    public List<Activite> afficher() throws SQLException {
        List<Activite> activites = new ArrayList<>();
        String req = "SELECT * FROM activite"; // Correct table name
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(req)) {
            while (rs.next()) {
                Activite activite = new Activite();
                activite.setId(rs.getInt("id"));
                activite.setNom_act(rs.getString("nom_act"));
                activite.setImage_name(rs.getString("image_name"));
                activite.setType_act(rs.getString("type_act"));
                activite.setLocation_act(rs.getString("location_act"));
                activite.setDescription_act(rs.getString("description_act"));
                activites.add(activite);
            }
        }
        return activites;
    }


    // Méthode pour vérifier si le nom de l'activité existe déjà dans la base de données
    public boolean nomExisteDeja(String nom) throws SQLException {
        String req = "SELECT COUNT(*) FROM activite WHERE nom_act = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setString(1, nom);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

}
