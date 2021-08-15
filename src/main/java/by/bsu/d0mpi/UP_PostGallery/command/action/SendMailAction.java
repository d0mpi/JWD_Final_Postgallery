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

/**
 * Implementation of the Command interface, which is responsible
 * for sending mail to the administrator of the web-application.
 * Implements thread-safe Singleton pattern using double checked locking idiom.
 *
 * @author d0mpi
 * @version 1.0
 * @see Command
 * @see CommandResponse
 * @see MimeMessage
 */
public class SendMailAction implements Command {
    private static final Logger logger = LogManager.getLogger();

    public static final String REQUEST_CONTACT_NAME_PARAM = "contact-name";
    public static final String REQUEST_CONTACT_EMAIL_PARAM = "contact-email";
    public static final String REQUEST_CONTACT_TEXT_PARAM = "contact-text";
    private static volatile SendMailAction instance;

    private final CommandResponse redirectHomePage;
    private final CommandResponse forwardContactPage;
    private final Properties mailProperties;

    private SendMailAction() {
        redirectHomePage = new SimpleCommandResponse("/controller?command=contact_page", true);
        forwardContactPage = new SimpleCommandResponse("/controller?command=contact_page", false);
        mailProperties = new Properties();
        try {
            mailProperties.load(new FileInputStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath() + "mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provide a global access point to the instance of the {@link SendMailAction} class.
     *
     * @return the only instance of the {@link SendMailAction} class
     */
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


    /**
     * Sends mail to the administrator of the web-application with the help of javax.mail.
     *
     * @param request the object contains a request received from the client
     * @return an object of the {@link CommandResponse} class with redirection to the main page
     * after successful mail sending, otherwise forward to the contact page.
     */
    @Override
    public CommandResponse execute(CommandRequest request) {
        final String userName = new String(request.getParameter(REQUEST_CONTACT_NAME_PARAM).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        final String userAddress = request.getParameter(REQUEST_CONTACT_EMAIL_PARAM);
        final String text = new String(request.getParameter(REQUEST_CONTACT_TEXT_PARAM).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        String login = mailProperties.getProperty("mail.user");
        String to = mailProperties.getProperty("mail.user");
        String password = mailProperties.getProperty("mail.password");

        Properties systemProperties = setMailProperties();

        if (!isCaptchaChecked(request))
            return forwardContactPage;

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
        } catch (MessagingException mex) {
            logger.error("Error during sending mail");
        }
        return redirectHomePage;
    }

    private Properties setMailProperties() {
        Properties systemProperties = System.getProperties();
        systemProperties.setProperty("mail.smtp.host", mailProperties.getProperty("mail.smtp.host"));
        systemProperties.put("mail.smtp.port", mailProperties.getProperty("mail.smtp.port"));
        systemProperties.put("mail.smtp.auth", mailProperties.getProperty("mail.smtp.auth"));
        systemProperties.put("mail.smtp.starttls.enable", mailProperties.getProperty("mail.smtp.starttls.enable"));
        return systemProperties;
    }

    private boolean isCaptchaChecked(CommandRequest request) {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            request.setAttribute("errorString", "You missed Captcha. Try again.");
            return false;
        }
        return true;
    }
}
