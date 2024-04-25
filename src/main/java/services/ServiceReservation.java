package services;

import entities.Reservation;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation implements IService<Reservation> {

    private Connection cnx;

    public ServiceReservation() {
        cnx = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String req = "INSERT INTO reservation(id_hotel_id,date_debut_r, date_fin_r, nbr_perso, type_room) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, reservation.getId_hotel_id());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate_debut_r()));
            preparedStatement.setDate(3, Date.valueOf(reservation.getDate_fin_r()));
            preparedStatement.setInt(4, reservation.getNbr_perso());
            preparedStatement.setString(5, reservation.getType_room());

            preparedStatement.executeUpdate();
            System.out.println("Reservation ajoutée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Reservation reservation) throws SQLException {
        try {
            String requete = "UPDATE reservation SET id_hotel_id=?, date_debut_r=?, date_fin_r=?, nbr_perso=?, type_room=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, reservation.getId_hotel_id());
             pst.setDate(2, Date.valueOf(reservation.getDate_debut_r()));
                pst.setDate(3, Date.valueOf(reservation.getDate_fin_r()));
                pst.setInt(4, reservation.getNbr_perso());
                pst.setString(5, reservation.getType_room());
                pst.setInt(6, reservation.getId());

            pst.executeUpdate();
            System.out.println("Reservation modifiée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Reservation reservation) throws SQLException {
        try {
            String req = "DELETE FROM reservation WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, reservation.getId());
            pst.executeUpdate();
            System.out.println("Reservation supprimée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> afficher() throws SQLException {
        List<Reservation> reservationList = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Reservation r = new Reservation();
            r.setId(rs.getInt(1));
            r.setDate_debut_r(rs.getDate(3).toLocalDate());
            r.setDate_fin_r(rs.getDate(4).toLocalDate());
            r.setNbr_perso(rs.getInt(5));
            r.setType_room(rs.getString(6));
            reservationList.add(r);
        }
        rs.close();
        st.close();
        return reservationList;
    }
}
