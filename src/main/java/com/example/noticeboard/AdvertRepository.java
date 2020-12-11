package com.example.noticeboard;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class AdvertRepository {

public ArrayList<Advert>advertList= new ArrayList<>();

    public AdvertRepository() {
        advertList.add(new Advert(1,"CarSeat for baby", "very nice car seat for baby up to 2 years", 299,"",0,0,1, 0));
        advertList.add(new Advert(3,"Moped", "Använt men i gott skick! Bortskänkes, först till kvarn", 0,"",0,0 , 3, 0));
        advertList.add(new Advert(2,"Fjällstuga i Åre uthyres", "Jättemysig stuga med 2 rok nära lift", 3000,"",0,0 , 2, 0));
        advertList.add(new Advert(3,"Matbord", "Använt men i gott skick! Bortskänkes, först till kvarn", 0,"",0,0 , 3, 0));
        advertList.add(new Advert(3,"Soffbord", "Använt men i gott skick! Bortskänkes, först till kvarn", 0,"",0,0 , 3, 0));

    }

    public Advert findById(int id)   {
        for(Advert adv : advertList ) {
            if (id == adv.getId())
                return adv;
        }
        return null;
    }

    public void save(Advert advert) {
        advertList.add(advert);
    }
}

