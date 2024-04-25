package tn.esprit.pidev22.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.pidev22.entities.Partenaire;
import tn.esprit.pidev22.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicePartenaire implements IService<Partenaire>{
    Connection connection;

    public  ServicePartenaire(){
        connection  = MyDataBase.getInstance().getConnection();
    }



    public ObservableList<Partenaire> RecupBase(){

        ObservableList<Partenaire> list = FXCollections.observableArrayList();

        java.sql.Connection cnx;
        cnx = MyDataBase.getInstance().getConnection();
        String sql = "select *from Partenaire";
        try {

            PreparedStatement st = (PreparedStatement) cnx.prepareStatement(sql);

            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Partenaire partenaire = new Partenaire();
                partenaire.setId(rs.getInt("id"));
                partenaire.setNom_p(rs.getString("nom_p"));
                partenaire.setType_p(rs.getString("type_p"));
                partenaire.setDescription_p(rs.getString("description_p"));
                partenaire.setPhoto_url(rs.getString("photo_url"));
                partenaire.setAddress(rs.getString("address"));




                list.add(partenaire);
            }
        }catch (SQLException ex){
            ex.getMessage();
        }
        return list;
    }
    public static ObservableList<String> RecupCombo(){


        ObservableList<String> list = FXCollections.observableArrayList();

        java.sql.Connection cnx;
        cnx = MyDataBase.getInstance().getConnection();
        String sql = "SELECT nom_p FROM Partenaire";
        try {

            PreparedStatement st = (PreparedStatement) cnx.prepareStatement(sql);

            ResultSet R = st.executeQuery();
            while (R.next()){


                String r = R.getString(1);
                System.out.println(r);


                list.add(r);
            }
        }catch (SQLException ex){
            ex.getMessage();
        }
        return list;
    }

    public Partenaire SelectPartenaire(int id){
        Partenaire r = new Partenaire();
        String req = "SELECT * FROM Partenaire where id ="+id+"";

        try {
            PreparedStatement ps = connection.prepareStatement(req);

            ResultSet rs = ps.executeQuery(req);

            while(rs.next()){

                r = new Partenaire(rs.getInt("id"), rs.getString("nom_p"),rs.getString("type_p"),rs.getString("description_p"),rs.getString("address"),rs.getString("photo_url"));

            }


        } catch (SQLException ex) {
            Logger.getLogger(ServicePartenaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    public Partenaire SelectPartenairebyname(String nom){
        Partenaire r = null;
        String req = "SELECT * FROM Partenaire WHERE nom_p = ?";  // Use prepared statements with placeholders

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nom);  // Set the name parameter in the query
            ResultSet rs = ps.executeQuery();

            if(rs.next()){  // Use if instead of while if you expect one or zero results
                r = new Partenaire(
                        rs.getInt("id"),
                        rs.getString("nom_p"),
                        rs.getString("type_p"),
                        rs.getString("description_p"),
                        rs.getString("address"),
                        rs.getString("photo_url")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePartenaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }


    @Override
    public void ajouter(Partenaire partenaire) throws SQLException {
        String req ="INSERT INTO partenaire (nom_p, type_p, description_p, address,  photo_url)"+
                "VALUES ('"+partenaire.getNom_p()+"','"+partenaire.getType_p()+"','"+partenaire.getDescription_p()+"','"+partenaire.getAddress()+"','"+partenaire.getPhoto_url()+"')";
        Statement statement=connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("new partner added");
    }



    @Override
    public void modifier(Partenaire partenaire) throws SQLException {
        String req = "UPDATE partenaire SET nom_p = ?, type_p = ?, description_p = ?, address= ?,photo_url= ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(req); {
            pstmt.setString(1, partenaire.getNom_p());
            pstmt.setString(2, partenaire.getType_p());
            pstmt.setString(3, partenaire.getDescription_p());
            pstmt.setString(4, partenaire.getAddress());
            pstmt.setString(5, partenaire.getPhoto_url());
            pstmt.setInt(6, partenaire.getId());


            pstmt.executeUpdate();

        }

        }



    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM partenaire WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(req);{
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Partenaire> afficher() throws SQLException {
        List<Partenaire> partenaires = new ArrayList<>();
        String req = "SELECT * FROM Partenaire";
        Statement stmt = connection.createStatement(); {
            ResultSet rs = stmt.executeQuery(req);
            while (rs.next()) {
                Partenaire partenaire = new Partenaire();
                partenaire.setId(rs.getInt("id"));
                partenaire.setNom_p(rs.getString("nom_p"));
                partenaire.setType_p(rs.getString("type_p"));
                partenaire.setDescription_p(rs.getString("description_p"));
                partenaire.setPhoto_url(rs.getString("photo_url"));
                partenaire.setAddress(rs.getString("address"));




               partenaires.add(partenaire);
            }
        }
        return partenaires;


    }


}
