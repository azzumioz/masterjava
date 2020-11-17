package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao implements AbstractDao {

    @SqlUpdate("INSERT INTO groups (name, type, project_id) VALUES (:name, CAST(:type AS GROUP_TYPE), :projectId)")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Group group);

    public void insert(Group group) {
        int id = insertGeneratedId(group);
        group.setId(id);
    }

    @SqlUpdate("TRUNCATE groups CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM groups ORDER BY name")
    public abstract List<Group> getAll();

}
