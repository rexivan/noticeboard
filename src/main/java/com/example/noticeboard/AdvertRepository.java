package com.example.noticeboard;

import java.util.ArrayList;

public class AdvertRepository {

public ArrayList<Advert>advertList= new ArrayList<>();

    public AdvertRepository() {
        advertList.add(new Advert(1,"CarSeat for baby", "very nice car seat for baby up to 2 years", 299,"",0,0 ));
        advertList.add(new Advert(2,"Fjällstuga i Åre uthyres", "Jättemysig stuga med 2 rok nära lift", 3000,"",0,0 ));
        advertList.add(new Advert(3,"Matbord", "Använt men i gott skick! Bortskänkes, först till kvarn", 0,"",0,0 ));

    }
}

