package services;

import entities.Challenge;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



import java.sql.*;


public class ServiceChallenge implements IService<Challenge> {
    Connection connection;

    public  ServiceChallenge(){
        connection  = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Challenge challenge) throws SQLException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDebut = sdf.format(challenge.getDate_debut_ch());
        String dateFin = sdf.format(challenge.getDate_fin_ch());

        String req = "INSERT INTO challenge (titre_ch, date_debut_ch, date_fin_ch, objectif_ch) " +
                "VALUES ('" + challenge.getTitre_ch() + "','" + dateDebut + "','" + dateFin + "','" + challenge.getObjectif_ch() + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("challenge ajouté");
    }


    @Override
    public void modifier(Challenge challenge) throws SQLException {
        // Vérifier si la date de fin est après la date de début



        String req = "UPDATE challenge SET titre_ch = ?, date_debut_ch = ?, date_fin_ch = ?, objectif_ch = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(req); {
            pstmt.setString(1, challenge.getTitre_ch());
            pstmt.setDate(2, new java.sql.Date(challenge.getDate_debut_ch().getTime()));
            pstmt.setDate(3, new java.sql.Date(challenge.getDate_fin_ch().getTime()));
            pstmt.setString(4, challenge.getObjectif_ch());
            pstmt.setInt(5, challenge.getId());


            pstmt.executeUpdate();
        }
    }



    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM challenge WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(req);{
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Challenge> afficher() throws SQLException {
        List<Challenge> challenges = new ArrayList<>();
        String req = "SELECT * FROM challenge";
        Statement stmt = connection.createStatement(); {
            ResultSet rs = stmt.executeQuery(req);
            while (rs.next()) {
                Challenge challenge=new Challenge();
                challenge.setId(rs.getInt("id"));
                challenge.setTitre_ch(rs.getString("titre_ch"));
                challenge.setDate_debut_ch(rs.getDate("date_debut_ch"));
                challenge.setDate_fin_ch(rs.getDate("date_fin_ch"));
                challenge.setObjectif_ch(rs.getString("objectif_ch"));



                challenges.add(challenge);
            }
        }
        return challenges;
    }



}
