package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao implements AbstractDao {

    @SqlUpdate("INSERT INTO projects (name, description) VALUES (:name, :description) ")
    @GetGeneratedKeys
    public abstract int insertGeneratedId(@BindBean Project project);

    public void insert(Project project) {
        int id = insertGeneratedId(project);
        project.setId(id);
    }

    @SqlUpdate("TRUNCATE projects CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM projects ORDER BY name")
    public abstract List<Project> getAll();

}
