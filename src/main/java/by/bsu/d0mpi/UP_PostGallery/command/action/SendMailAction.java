package by.bsu.d0mpi.UP_PostGallery.command.action;

import by.bsu.d0mpi.UP_PostGallery.command.Command;
import by.bsu.d0mpi.UP_PostGallery.command.CommandRequest;
import by.bsu.d0mpi.UP_PostGallery.command.CommandResponse;
import by.bsu.d0mpi.UP_PostGallery.command.SimpleCommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class SendMailAction implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static volatile SendMailAction instance;

    private final CommandResponse homePageResponse;
    private final Properties mailProperties;

    public static SendMailAction getInstance() {
        SendMailAction localInstance = instance;
        if (localInstance == null) {
            synchronized (SendMailAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SendMailAction();
                }
            }
        }
        return localInstance;
    }

    public SendMailAction() {
        homePageResponse = new SimpleCommandResponse("/controller?command=main_page", true);
        mailProperties = new Properties();
        try {
            mailProperties.load(new FileInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath() + "mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String userName = new String(request.getParameter("contact-name").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        final String userAddress = request.getParameter("contact-email");
        final String text = new String(request.getParameter("contact-text").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        String login = mailProperties.getProperty("mail.user");
        String to = mailProperties.getProperty("mail.user");
        String password = mailProperties.getProperty("mail.password");
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("mail.smtp.host", mailProperties.getProperty("mail.smtp.host"));
        systemProperties.put("mail.smtp.port", mailProperties.getProperty("mail.smtp.port"));
        systemProperties.put("mail.smtp.auth", mailProperties.getProperty("mail.smtp.auth"));
        systemProperties.put("mail.smtp.starttls.enable", mailProperties.getProperty("mail.smtp.starttls.enable"));

        Session session = Session.getDefaultInstance(systemProperties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(login));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Message from UP_PostGallery user");
            message.setText("User name: " + userName + "\nUser address: " + userAddress + "\n" + text);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return homePageResponse;
    }
}
