package TuniWonders.services;

import TuniWonders.entities.User;
import TuniWonders.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    Connection connection;

    public UserService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO user (user_name, password,vpwd, roles , cin, email, adresse, num_tel,status)" +
                "values('" + user.getUsername() + "','" + user.getPassword() + "','" + user.getVpwd() + "','" + user.getRoles() + "'," + user.getCin() + ",'" + user.getEmail() + "','" + user.getAdresse() + "'," + user.getNum_tel() + ",'" + user.getStatus() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
    }


    public void modifier(User user) throws SQLException {
        String req = "UPDATE user SET user_name=?, password=?, vpwd=?,  cin=?, email=?, adresse=?, num_tel=?, status=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getVpwd());
        preparedStatement.setInt(4, user.getCin());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getAdresse());
        preparedStatement.setInt(7, user.getNum_tel());
        preparedStatement.setString(8, user.getStatus());
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
        String req = "delete from user where id=" + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public List<User> afficher() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setVpwd(rs.getString("vpwd"));
            user.setRoles(rs.getString("roles"));
            user.setCin(rs.getInt("cin"));
            user.setEmail(rs.getString("email"));
            user.setAdresse(rs.getString("adresse"));
            user.setNum_tel(rs.getInt("num_tel"));
            user.setStatus(rs.getString("status"));

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
                user.setStatus(rs.getString("status"));

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public User login(String email, String password) throws SQLException {
        User user = new User();
        String req = "SELECT * FROM user WHERE email=? AND Vpwd=?";
        PreparedStatement st = connection.prepareStatement(req);
        st.setString(1, email);
        st.setString(2, password);
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
            user.setStatus(rs.getString("status"));
            System.out.println("user logged in");
        } else {
            System.out.println("login error");
        }
        return user;
    }
}