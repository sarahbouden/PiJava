package tn.esprit.pidev22.services;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.pidev22.entities.Offre;
import tn.esprit.pidev22.entities.Partenaire;
import tn.esprit.pidev22.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesOffre implements IService<Offre> {
    Connection connection;
    public  ServicesOffre(){
        connection  = MyDataBase.getInstance().getConnection();
    }

    public ObservableList<Offre> RecupBase(){
        ServicePartenaire r = new ServicePartenaire();

        ObservableList<Offre> list = FXCollections.observableArrayList();

        java.sql.Connection cnx;
        cnx = MyDataBase.getInstance().getConnection();
        String sql = "select *from Offre";
        try {

            PreparedStatement st = (PreparedStatement) cnx.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Offre offre = new Offre();
                offre.setId(rs.getInt("id"));
                offre.setPartenaire_id(r.SelectPartenaire(rs.getInt("partenaire_id")));
                offre.setNom_offre(rs.getString("nom_offre"));
                offre.setDescription_offre(rs.getString("description_offre"));
                offre.setDate_exp(rs.getDate("date_exp"));
                offre.setCreated_at(rs.getDate("created_at"));
                offre.setPhoto_url(rs.getString("photo_url"));
                offre.setPrix(rs.getDouble("prix"));


                list.add(offre);
            }
        }catch (SQLException ex){
            ex.getMessage();
        }
        return list;
    }


    @Override
    public void ajouter(Offre offre) throws SQLException {
        String req = "INSERT INTO offre (partenaire_id, nom_offre, description_offre, date_exp, created_at, photo_url, prix) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(req);

        // Set parameter values
        statement.setInt(1, offre.getPartenaire_id().getId());
        statement.setString(2, offre.getNom_offre());
        statement.setString(3, offre.getDescription_offre());
        statement.setDate(4, new java.sql.Date(offre.getDate_exp().getTime())); // Assuming Offre.getDate_exp() returns a java.util.Date
        statement.setString(5, offre.getPhoto_url());
        statement.setDouble(6, offre.getPrix());

        // Execute query
        statement.executeUpdate();
        System.out.println("Offer published");
    }

    public void modifier(Offre offre) throws SQLException {
        String req = "UPDATE offre SET partenaire_id = ?, nom_offre = ?, description_offre = ?, date_exp = ?, photo_url = ?, prix = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setInt(1, offre.getPartenaire_id().getId());
            pstmt.setString(2, offre.getNom_offre());
            pstmt.setString(3, offre.getDescription_offre());
            pstmt.setDate(4, new java.sql.Date(offre.getDate_exp().getTime()));
            pstmt.setString(5, offre.getPhoto_url());
            pstmt.setDouble(6, offre.getPrix());
            pstmt.setInt(7, offre.getId());

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Updated successfully");
            } else {
                System.out.println("No record updated");
            }
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

            String req = "DELETE FROM offre WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(req);{
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
    }

    public boolean isOfferNameUnique(String name) throws SQLException {
        String req = "SELECT COUNT(*) AS count FROM offre WHERE nom_offre = ?";
        PreparedStatement pstmt = connection.prepareStatement(req);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt("count");
        return count == 0; // If count is 0, it means the name is unique
    }


    @Override
    public List<Offre> afficher() throws SQLException {
        ServicePartenaire r = new ServicePartenaire();

        List<Offre> offres = new ArrayList<>();
        String req = "SELECT * FROM Offre";
        Statement stmt = connection.createStatement(); {
            ResultSet rs = stmt.executeQuery(req);
            while (rs.next()) {
                Offre offre = new Offre();
                offre.setId(rs.getInt("id"));
               offre.setPartenaire_id(r.SelectPartenaire(rs.getInt("partenaire_id")));
                offre.setNom_offre(rs.getString("nom_offre"));
                offre.setDescription_offre(rs.getString("description_offre"));
                offre.setDate_exp(rs.getDate("date_exp"));
                offre.setCreated_at(rs.getDate("created_at"));
                offre.setPhoto_url(rs.getString("photo_url"));
                offre.setPrix(rs.getDouble("prix"));




                offres.add(offre);
            }
        }
        return offres;
    }
}
