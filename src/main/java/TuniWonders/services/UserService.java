package TuniWonders.services;

import TuniWonders.entities.User;
import TuniWonders.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    Connection connection;
    public UserService(){
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req="INSERT INTO user (user_name, password,vpwd, roles , cin, email, adresse, num_tel)"+
                "values('"+user.getUsername()+"','"+user.getPassword()+"','"+user.getVpwd()+"','"+user.getRoles()+"',"+user.getCin()+",'"+user.getEmail()+"','"+user.getAdresse()+"',"+user.getNum_tel()+")";
        Statement statement=connection.createStatement();
        statement.executeUpdate(req);
    }


    public void modifier(User user) throws SQLException {
        String req = "UPDATE user SET user_name=?, password=?, vpwd=?, roles=?, cin=?, email=?, adresse=?, num_tel=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getVpwd());
        preparedStatement.setString(4, user.getRoles());
        preparedStatement.setInt(5, user.getCin());
        preparedStatement.setString(6, user.getEmail());
        preparedStatement.setString(7, user.getAdresse());
        preparedStatement.setInt(8, user.getNum_tel());
        preparedStatement.setInt(9, user.getId());

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("User updated");
            // Commit changes if necessary
            // connection.commit();
        } else {
            System.out.println("No user updated");
        }
        preparedStatement.close();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req ="delete from user where id="+id;
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public List<User> afficher() throws SQLException {
        List<User> users= new ArrayList<>();
        String req="SELECT * FROM user";
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery(req);
        while (rs.next()) {
            User user=new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setVpwd(rs.getString("vpwd"));
            user.setRoles(rs.getString("roles"));
            user.setCin(rs.getInt("cin"));
            user.setEmail(rs.getString("email"));
            user.setAdresse(rs.getString("adresse"));
            user.setNum_tel(rs.getInt("num_tel"));

            users.add(user);

        }
        return users;
    }
    public User rechercheUser(int id) {
        User user = new User();
        try {
            String req = "SELECT * FROM `user` WHERE id=?";
            PreparedStatement st = connection.prepareStatement(req);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setVpwd(rs.getString("vpwd"));
                user.setRoles(rs.getString("roles"));
                user.setCin(rs.getInt("cin"));
                user.setEmail(rs.getString("email"));
                user.setAdresse(rs.getString("adresse"));
                user.setNum_tel(rs.getInt("num_tel"));

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

}
