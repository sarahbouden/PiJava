package TuniWonders.tests;

import TuniWonders.entities.User;

import TuniWonders. services.UserService;
import TuniWonders.utils.MyDataBase;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyDataBase.getInstance();
        User user= new User(9,"Sarra bawden","11111111!","11111111!","ROLE_USER",44444444,"sarra.bouden@gmail.com","NajbEbnFernes",22574667,"ACTIVE");
        UserService Uservice= new UserService();
       /* try{

        }
        catch ( SQLException e){
            System.out.println(e.getMessage());
        }*/
        User user1=Uservice.rechercheUser(11);
        user1.toString();

       /* try{
            List<User> users=Uservice.afficher();
            Iterator<User> iterator = users.iterator();

            // Use a loop to iterate through the elements of the list using the iterator
            while (iterator.hasNext()) {
                User u = iterator.next();
                System.out.println(u);
            }

        }
        catch ( SQLException e){
            System.out.println(e.getMessage());
        }*/
    }
}
