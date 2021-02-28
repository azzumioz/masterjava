package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;


import java.util.List;

@Slf4j
public class MailSender {

    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        Config mailConf = Configs.getConfig("mail.conf", "mail");
        try {
            Email email = new SimpleEmail();
            email.setHostName(mailConf.getString("host"));
            email.setSmtpPort(mailConf.getInt("port"));
            email.setAuthenticator(new DefaultAuthenticator(mailConf.getString("username"), mailConf.getString("password")));
            email.setSSLOnConnect(mailConf.getBoolean("useSSL"));
            email.setFrom(mailConf.getString("fromName"));
            email.setSubject(subject);
            email.setMsg(body);
            for (Addressee addressee : to)
            {
                email.addTo(addressee.getEmail());
            }
            email.setStartTLSRequired(mailConf.getBoolean("useTLS"));
            email.setDebug(mailConf.getBoolean("debug"));
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
        log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
    }
}
