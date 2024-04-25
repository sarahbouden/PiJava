/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Hotel;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceHotel implements IService<Hotel> {


    private Connection cnx;
    public ServiceHotel(){cnx = MyDataBase.getInstance().getConnection();}
    @Override
    public void ajouter(Hotel hotel) throws SQLException {

        String req = "INSERT INTO `hotel`(`name_h`,`location`,`description`,`photo_url`) VALUES ( ?, ?, ?,?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            // preparedStatement.setInt(1, hotel.getId()); // No need for this line
          //  preparedStatement.setInt(1, hotel.getId());
            preparedStatement.setString(1, hotel.getName_h());
            preparedStatement.setString(2, hotel.getLocation());
            preparedStatement.setString(3, hotel.getDescription());
            preparedStatement.setString(4, hotel.getPhoto_url());


            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    public void modifier(Hotel hotel) throws SQLException {
        try {
            String requete = "UPDATE hotel SET name_h=? ,location=? ,description=? ,photo_url=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);

            pst.setString(1, hotel.getName_h());
            pst.setString(2, hotel.getLocation());
            pst.setString(3, hotel.getDescription());
            pst.setString(4, hotel.getPhoto_url());
            pst.setInt(5, hotel.getId()); // Utiliser le 5ème paramètre pour l'id
            pst.executeUpdate();
            System.out.println("Hotel modifié avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Hotel hotel) throws SQLException {
        try{
            String req="DELETE FROM `hotel` WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, hotel.getId());
            pst.executeUpdate();
            System.out.println("hotel supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Hotel> afficher() throws SQLException {
        List<Hotel> hotelList=new ArrayList<>();
        String req="SELECT * FROM `hotel`";
        Statement st = cnx.createStatement();
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            Hotel c=new Hotel();
            c.setId(rs.getInt(1));
            c.setName_h(rs.getString(2));
            c.setLocation(rs.getString(3));
            c.setDescription(rs.getString(4));
            c.setPhoto_url(rs.getString(5));
            hotelList.add(c);
            System.out.println("done");
        }
        return hotelList;
    }
}
