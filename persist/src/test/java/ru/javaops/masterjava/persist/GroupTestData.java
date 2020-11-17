package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;

import java.util.List;

import static ru.javaops.masterjava.persist.ProjectTestData.MASTERJAVA_ID;
import static ru.javaops.masterjava.persist.ProjectTestData.TOPJAVA_ID;

public class GroupTestData {
    public static Group TOPJAVA06;
    public static Group TOPJAVA07;
    public static Group TOPJAVA08;
    public static Group MASTARJAVA01;
    public static List<Group> GROUPS;

    public static int TOPJAVA_06_ID;
    public static int TOPJAVA_07_ID;
    public static int TOPJAVA_08_ID;
    public static int MASTERJAVA_01_ID;

    public static void init() {
        ProjectTestData.init();
        ProjectTestData.setUp();

        TOPJAVA06 = new Group("topjava06", GroupType.FINISHED, TOPJAVA_ID);
        TOPJAVA07 = new Group("topjava07", GroupType.FINISHED, TOPJAVA_ID);
        TOPJAVA08 = new Group("topjava08", GroupType.CURRENT, TOPJAVA_ID);
        MASTARJAVA01 = new Group("masterjava01", GroupType.CURRENT, MASTERJAVA_ID);
        GROUPS = ImmutableList.of(MASTARJAVA01, TOPJAVA06, TOPJAVA07, TOPJAVA08);
    }

    public static void setUp() {
        GroupDao dao = DBIProvider.getDao(GroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            for (Group GROUP : GROUPS) {
                dao.insert(GROUP);
            }
        });
        TOPJAVA_06_ID = TOPJAVA06.getId();
        TOPJAVA_07_ID = TOPJAVA07.getId();
        TOPJAVA_08_ID = TOPJAVA08.getId();
        MASTERJAVA_01_ID = MASTARJAVA01.getId();
    }
}
