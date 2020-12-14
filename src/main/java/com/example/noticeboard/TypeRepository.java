package com.example.noticeboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TypeRepository {
    @Autowired
    private DataSource dataSource;

    public List<String> advertTypeList = new ArrayList<>();
    public List<String> advertLocationList = new ArrayList<>();
    public List<String> advertCategoryList = new ArrayList<>();


    public void getAllLists()   {
        advertTypeList = readList("adtype");
        advertLocationList = readList("location");
        advertCategoryList = readList("category");
    }

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
}
