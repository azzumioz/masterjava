package ru.javaops.masterjava.util;

import ru.javaops.masterjava.model.User;
import ru.javaops.masterjava.model.UserFlag;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class UserExport {
    private static final String USER = "User";
    private static final String USER_EMAIL = "email";
    private static final String USER_FLAG = "flag";

    public static List<User> getUserListStax(String file) throws Exception {
        List<User> userList = new ArrayList<>();
        try (InputStream in = Files.newInputStream(Paths.get(file));
             StaxStreamProcessor processor =
                     new StaxStreamProcessor(in)) {
            XMLStreamReader reader = processor.getReader();
            User user = null;

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT) {
                    if (USER.equals(reader.getLocalName())) {
                        user = new User();
                        int attrCount = reader.getAttributeCount();
                        for (int i = 0; i < attrCount; i++) {
                            String propertyAttr = reader.getAttributeLocalName(i);
                            if (USER_EMAIL.equals(propertyAttr)) {
                                user.setEmail(reader.getAttributeValue(i));
                            }
                            if (USER_FLAG.equals(propertyAttr)) {
                                user.setFlag(UserFlag.fromValue(reader.getAttributeValue(i)));
                            }
                        }
                    }
                }
                if (event == XMLEvent.END_ELEMENT) {
                    if (USER.equals(reader.getLocalName())) {
                        userList.add(user);
                    }
                }
            }
        }
        return userList;
    }
}

