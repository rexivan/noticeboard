package com.example.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private DataSource dataSource;

    public ArrayList<User> userList = new ArrayList<>();
    public int userId=0;
    public String userName="";

    public List<User> getUsers() {
        List<User> books = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, email, firstName, lastName, phone, pwd FROM user")) {

            while (rs.next()){
                books.add(rsUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private User rsUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("phone"),
                rs.getString("pwd"));
    }

    public UserRepository() {
        this.userList = userList;
        userList.add(new User(1, "fbs@hm.com", "Fredrik", "Bill", "+72 444 65 33", "secret1"));
        userList.add(new User(2, "Eva.EriXXon@hm.com", "Eva", "Eriksson", "+72 444 12 00", "secret2"));
        userList.add(new User(3, "sven.GranZon@hm.com", "Sven", "Gran", "+72 555 12 77", "secret3"));
        userList.add(new User(4, "Liisa.LarZon@hm.com", "Lisa", "Larsson", "+72 345 65 33", "secret4"));

     }

     public void addUserlistoDB()   {  // Test purpouse only...
         for(var u : userList)
             addUser(u);
     }

    public User findById(int id)   {
        for(var us: userList)   {
            if (id == us.getId())
                return us;
        }
        return null;
    }

    public void addUser(User user) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO USER(EMAIL, FIRSTNAME, LASTNAME, PHONE, PWD) VALUES (?,?,?,?,?) ")) {
            ps.setString(1, user.getEmail ());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
