package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.GroupType;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainXml {
    private static final String RESOURCE_XML = "payload.xml";
    private static final String USER = "User";
    private static final String USER_NAME = "fullName";
    private static final String USER_EMAIL = "email";
    private static final String USER_GROUP = "group";
    private static final String GROUP = "Group";
    private static final String GROUP_ID = "id";
    private static final String GROUP_PROJECT = "project";
    private static final String PROJECT = "Project";
    private static final String PROJECT_ID = "id";
    private static final String PROJECT_NAME = "name";

    public static void main(String[] args) throws Exception {
        String projectName = "TopJava";

        System.out.println("*** User in project " + projectName + " with JaxB ***");
        List<User> listUsersJaxb = getUserListJaxb(projectName);
        for (User user : listUsersJaxb) {
            System.out.println(user.getFullName() + " (" + user.getEmail() + ")");
        }

        System.out.println("*** User in project " + projectName + " with Stax ***");
        List<UserModel> listUsersStax = getUserListStax(projectName);
        for (UserModel user : listUsersStax) {
            System.out.println(user.getUserName() + " (" + user.getEmail() + ")");
        }
    }

    private static List<User> getUserListJaxb(String projectName) throws IOException, JAXBException {
        final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);
        Payload payload = JAXB_PARSER.unmarshal(Resources.getResource(RESOURCE_XML).openStream());

        List<User> listUser = payload.getUsers().getUser()
                .stream()
                .filter(user -> {
                    List<GroupType> groups = user.getGroup();
                    for (GroupType group : groups) {
                        return group.getProject().getName().equals(projectName);
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return listUser;
    }

    private static List<UserModel> getUserListStax(String projectName) throws Exception {
        List<UserModel> userList = new ArrayList<>();
        List<GroupModel> groupList = new ArrayList<>();
        List<ProjectModel> projectList = new ArrayList<>();
        List<UserModel> userResultList = new ArrayList<>();

        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource(RESOURCE_XML).openStream())) {
            XMLStreamReader reader = processor.getReader();
            UserModel userModel = null;
            GroupModel groupModel = null;
            ProjectModel projectModel = null;

            while (reader.hasNext()) {
                int event = reader.next();

                if (event == XMLEvent.START_ELEMENT) {
                    if (USER.equals(reader.getLocalName())) {
                        userModel = new UserModel();
                        int attrCount = reader.getAttributeCount();
                        for (int i = 0; i < attrCount; i++) {
                            String propertyAttr = reader.getAttributeLocalName(i);
                            if (USER_GROUP.equals(propertyAttr)) {
                                userModel.setGroupName(reader.getAttributeValue(i));
                            }
                            if (USER_EMAIL.equals(propertyAttr)) {
                                userModel.setEmail(reader.getAttributeValue(i));
                            }
                        }
                    }

                    if (USER_NAME.equals(reader.getLocalName())) {
                        userModel.setUserName(reader.getElementText());
                    }

                    if (GROUP.equals(reader.getLocalName())) {
                        groupModel = new GroupModel();
                        int attrCount = reader.getAttributeCount();
                        for (int i = 0; i < attrCount; i++) {
                            String propertyAttr = reader.getAttributeLocalName(i);
                            if (GROUP_ID.equals(propertyAttr)) {
                                groupModel.setIdGroup(reader.getAttributeValue(i));
                            }
                            if (GROUP_PROJECT.equals(propertyAttr)) {
                                groupModel.setProjectName(reader.getAttributeValue(i));
                            }
                        }
                    }

                    if (PROJECT.equals(reader.getLocalName())) {
                        projectModel = new ProjectModel();
                        int attrCount = reader.getAttributeCount();
                        for (int i = 0; i < attrCount; i++) {
                            String propertyAttr = reader.getAttributeLocalName(i);
                            if (PROJECT_ID.equals(propertyAttr)) {
                                projectModel.setId(reader.getAttributeValue(i));
                            }
                        }
                    }

                    if (PROJECT_NAME.equals(reader.getLocalName())) {
                        projectModel.setName(reader.getElementText());
                    }

                }

                if (event == XMLEvent.END_ELEMENT) {
                    if (USER.equals(reader.getLocalName())) {
                        userList.add(userModel);
                    }
                    if (GROUP.equals(reader.getLocalName())) {
                        groupList.add(groupModel);
                    }
                    if (PROJECT.equals(reader.getLocalName())) {
                        projectList.add(projectModel);
                    }
                }
            }
        }

        for (ProjectModel project : projectList) {
            if (projectName.equals(project.name)) {
                for (GroupModel group : groupList) {
                    if (project.id.equals(group.projectName)) {
                        for (UserModel user : userList) {
                            if (group.idGroup.equals(user.getGroupName())) {
                                userResultList.add(user);
                            }
                        }
                    }
                }
            }

        }
        return userResultList;
    }

    public static class UserModel {
        private String userName;
        private String email;
        private String groupName;

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getGroupName() {
            return groupName;
        }

    }

    public static class GroupModel {
        private String idGroup;
        private String projectName;

        public void setIdGroup(String idGroup) {
            this.idGroup = idGroup;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getIdGroup() {
            return idGroup;
        }

        public String getProjectName() {
            return projectName;
        }
    }

    public static class ProjectModel {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

