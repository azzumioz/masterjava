package ru.javaops.masterjava.persist.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.CityTestData;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javaops.masterjava.persist.CityTestData.CITIES;

public class CityDaoTest extends AbstractDaoTest<CityDao> {

    public CityDaoTest() {
        super(CityDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        CityTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        CityTestData.setUp();
    }

    @Test
    public void getAll() {
        List<City> cities = dao.getAll();
        assertEquals(CITIES, cities);
        cities.forEach(System.out::println);
    }

}
