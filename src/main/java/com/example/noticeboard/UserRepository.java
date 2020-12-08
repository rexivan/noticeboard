package com.example.noticeboard;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class UserRepository {
    public ArrayList<User> userList = new ArrayList<>();

    public UserRepository() {
        this.userList = userList;
        userList.add(new User(1, "fbs@hm.com", "Fredrik", "Bull", "+72 375 65 33"));
        userList.add(new User(2, "Eva.Erikssson@hm.com", "Eva", "Eriksson", "+72 444 12 00"));
        userList.add(new User(3, "sven.gran@hm.com", "Sven", "Gran", "+72 555 12 77"));
        userList.add(new User(4, "Liisa.Larsson@hm.com", "Lisa", "Larsson", "+72 345 65 33"));
    }

    public User findById(int id)   {
        for(var us: userList)   {
            if (id == us.getId())
                return us;
        }
        return null;
    }
}
