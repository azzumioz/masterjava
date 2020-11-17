package ru.javaops.masterjava.persist.dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.GroupTestData;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javaops.masterjava.persist.GroupTestData.GROUPS;

public class GroupDaoTest extends AbstractDaoTest<GroupDao> {

    public GroupDaoTest() {
        super(GroupDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        GroupTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        GroupTestData.setUp();
    }

    @Test
    public void getAll() {
        List<Group> groups = dao.getAll();
        assertEquals(GROUPS, groups);
        groups.forEach(System.out::println);
    }

}
