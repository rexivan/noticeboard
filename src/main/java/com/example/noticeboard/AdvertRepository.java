package com.example.noticeboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdvertRepository {
    @Autowired
    private DataSource dataSource;

    public List<Advert> advertList= new ArrayList<>();
    /*public List<String> advertTypeList = new ArrayList<>();
    public List<String> advertLocationList = new ArrayList<>();
    public List<String> advertCategoryList = new ArrayList<>();
*/
    public AdvertRepository() {

    }

    public List<Advert> getAdverts() {
        List<Advert> adverts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, header, description, price, url, categoryid, locationid, userid, adtypeid FROM advert order by id desc")) {

            while (rs.next()){
                adverts.add(rsAdvert(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        advertList = adverts;
        return adverts;
    }

    public List<Advert> getMyAdverts(int id)   {
        List<Advert> adverts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, header, description, price, url, categoryid, locationid, userid, adtypeid FROM advert where userid = " + id + " order by id desc")) {
           while (rs.next()){
              adverts.add(rsAdvert(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        advertList = adverts;
        return adverts;
    }

    private Advert rsAdvert(ResultSet rs) throws SQLException {
        return new Advert(rs.getInt("id"),
                rs.getString("header"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getString("url"),
                rs.getInt("categoryid"),
                rs.getInt("locationid"),
                rs.getInt("userid"),
                rs.getInt("adtypeid"));
    }

    public Advert findById(int id)   {
        for(Advert adv : advertList ) {
            if (id == adv.getId())
                return adv;
        }
        return null;
    }

    public void addAdvert(Advert advert) {
        try (Connection conn = dataSource.getConnection(); //(url, header,  description, price, categoryId,  locationId, userId, adTypeId)
             PreparedStatement ps = conn.prepareStatement("INSERT INTO ADVERT(url, header,  description, price, categoryId,  locationId, userId, adTypeId) VALUES (?,?,?,?,?,?,?,?) ")) {
            ps.setString(1, advert.getUrl());
            ps.setString(2, advert.getHeader());
            ps.setString(3, advert.getDescription());
            ps.setDouble(4, advert.getPrice());
            ps.setInt(5, advert.getCategoryId());
            ps.setInt(6, advert.getLocationId());
            ps.setInt(7, advert.getUserId());
            ps.setInt(8, advert.getAdTypeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAdvert(Advert advert, int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("update ADVERT set url=?, header=?,  description=?, price=?, categoryId=?,  locationId=?, adTypeId=?  where id =" +id)) {
             ps.setString(1, advert.getUrl());
             ps.setString(2, advert.getHeader());
             ps.setString(3, advert.getDescription());
             ps.setDouble(4, advert.getPrice());
             ps.setInt(5, advert.getCategoryId());
             ps.setInt(6, advert.getLocationId());
             ps.setInt(7, advert.getAdTypeId());
             ps.executeUpdate();
        } catch (SQLException e)  {
        e.printStackTrace();
        }
    }

   /* public void getAllLists()   {
       // advertTypeList = readList("adtype");
       // advertLocationList = readList("location");
       // advertCategoryList = readList("category");
    }
*/
    public List<String> readList(String listname)   {
        List <String> aList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name from "+listname)) {

            while (rs.next()){
                aList.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exxception: " +e.getMessage());
        }
        return aList;
    }

    public void deleteAdvert(int id)   {
        try (Connection conn = dataSource.getConnection(); //(url, header,  description, price, categoryId,  locationId, userId, adTypeId)
             PreparedStatement ps = conn.prepareStatement("DELETE FROM advert WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

