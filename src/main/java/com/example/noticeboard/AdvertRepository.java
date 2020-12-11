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

    public AdvertRepository() {

        advertList.add(new Advert(1,"CarSeat for baby", "very nice car seat for baby up to 2 years", 299,"",0,0,1, 0));
        advertList.add(new Advert(2,"Fjällstuga i Åre uthyres", "Jättemysig stuga med 2 rok nära lift", 3000,"",0,0 , 2, 0));
        advertList.add(new Advert(3,"Matbord", "Använt men i gott skick! Bortskänkes, först till kvarn", 0,"",0,0 , 3, 0));
        advertList.add(new Advert(4,"Badrumsmatta", "Rosa badrumsmatta. Oanvänd. Bortskänkes, pga felköp", 0,"Item1.jpg",0,0 , 1, 0));

//        advertList = getAdverts();
    }

    public List<Advert> getAdverts() {
        List<Advert> adverts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, header, description, price, url, categoryid, locationid, userid, adtypeid FROM advert")) {

            while (rs.next()){
                adverts.add(rsAdvert(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

 /*   public void save(Advert advert) {
        advertList.add(advert);
    }
*/
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
}

