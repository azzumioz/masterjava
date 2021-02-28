package ru.javaops.masterjava.service.mail;

import com.google.common.collect.ImmutableList;
import com.typesafe.config.Config;
import ru.javaops.masterjava.config.Configs;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class MailServiceClient {

    public static void main(String[] args) throws MalformedURLException {
        Service service = Service.create(
                new URL("http://localhost:8080/mail/mailService?wsdl"),
                new QName("http://mail.service.masterjava.javaops.ru/", "MailServiceImplService"));

        Config mailConf = Configs.getConfig("mail.conf", "mail");
        MailService mailService = service.getPort(MailService.class);
        mailService.sendMail(ImmutableList.of(new Addressee(mailConf.getString("to1"), null), new Addressee(mailConf.getString("to2"), null)), null, "Subject", "Body");
    }
}
