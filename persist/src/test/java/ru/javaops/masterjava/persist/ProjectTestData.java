package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

public class ProjectTestData {
    public static Project MASTERJAVA;
    public static Project TOPJAVA;
    public static List<Project> PROJECTS;
    public static int TOPJAVA_ID;
    public static int MASTERJAVA_ID;

    public static void init() {
        MASTERJAVA = new Project("masterjava", "Masterjava");
        TOPJAVA = new Project("topjava", "Topjava");
        PROJECTS = ImmutableList.of(MASTERJAVA, TOPJAVA);
    }

    public static void setUp() {
        ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            for (Project PROJECT : PROJECTS) {
                dao.insert(PROJECT);
            }
        });
        TOPJAVA_ID = TOPJAVA.getId();
        MASTERJAVA_ID = MASTERJAVA.getId();
    }
}
