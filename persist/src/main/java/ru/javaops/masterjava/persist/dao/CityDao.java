package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao implements AbstractDao {

    @SqlUpdate("INSERT INTO cities (ref, name) VALUES (:ref, :name)")
    @GetGeneratedKeys
    public abstract int insertGeneratedId(@BindBean City city);

    public void insert(City city) {
        int id = insertGeneratedId(city);
        city.setId(id);
    }

    @SqlUpdate("TRUNCATE cities CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM cities ORDER BY name")
    public abstract List<City> getAll();

}
