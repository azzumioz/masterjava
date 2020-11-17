package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;

import java.util.*;

public class CityTestData {

    public static City SPB;
    public static City MOW;
    public static City KIV;
    public static City MNSK;
    public static List<City> CITIES;

    public static void init() {
        SPB = new City( "Санкт-Петербург","SPB");
        MOW = new City("Москва","MOW");
        KIV = new City("Киев","KIV");
        MNSK = new City("Минск","MNSK");
        CITIES = ImmutableList.of(KIV, MNSK, MOW, SPB);
    }

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            CITIES.forEach(dao::insert);
        });
    }
}
