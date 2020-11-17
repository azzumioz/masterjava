package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.UserGroupDao;
import ru.javaops.masterjava.persist.model.UserGroup;

import java.util.List;
import java.util.Set;

import static ru.javaops.masterjava.persist.GroupTestData.*;

public class UserGroupTestData {

    public static List<UserGroup> USER_GROUPS;

    public static void init() {
        UserTestData.init();
        UserTestData.setUp();
        GroupTestData.init();
        GroupTestData.setUp();

        USER_GROUPS = ImmutableList.of(
                new UserGroup(UserTestData.ADMIN.getId(), TOPJAVA_06_ID),
                new UserGroup(UserTestData.ADMIN.getId(), TOPJAVA_07_ID),
                new UserGroup(UserTestData.ADMIN.getId(), TOPJAVA_08_ID),
                new UserGroup(UserTestData.FULL_NAME.getId(), TOPJAVA_07_ID),
                new UserGroup(UserTestData.USER1.getId(), MASTERJAVA_01_ID),
                new UserGroup(UserTestData.USER1.getId(), TOPJAVA_06_ID),
                new UserGroup(UserTestData.USER2.getId(), MASTERJAVA_01_ID),
                new UserGroup(UserTestData.USER3.getId(), MASTERJAVA_01_ID)
        );
    }

    public static void setUp() {
        UserGroupDao dao = DBIProvider.getDao(UserGroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            dao.insertBatch(USER_GROUPS);
        });
    }

    public static Set<Integer> getByGroupId(int groupId) {
        return UserGroupDao.getByGroupId(groupId, USER_GROUPS);
    }
}
