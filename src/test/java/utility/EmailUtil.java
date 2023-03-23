package utility;

import com.Ninja.BaseClass.CommonFunctions;
import constants.IConstants;
import logging.Log;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailUtil {
    public static Properties prop = CommonFunctions.init_properties(IConstants.EMAIL_CONFIG_PATH);
    private static final String USER_NAME = prop.getProperty("UserName");  // GMail username (just the part before "@gmail.com")
    private static final String PASSWORD = prop.getProperty("Password"); // GMail password
    private static final String RECIPIENT = prop.getProperty("Recipient");

    //this method is used to send mail by giving the details of body subject, to address, attachment path
    public static void sendMail(String reportPath, String fileName) {


        String[] to = {RECIPIENT}; // list of recipient mail addresses
        String subject = prop.getProperty("Subject");
        String body = prop.getProperty("body");

        sendFromGMail(to, subject, body, reportPath,fileName);
    }

    public static String readMailOtp(String subject) {
        String message = readGmail(subject);
        String regex = "\\D+";
        String[] otp = message.split(regex);

        return otp[1];
    }

    private static void sendFromGMail(String[] to, String subject, String body, String reportPath, String fileName) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(body);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(reportPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static String readGmail(String subject) {

        String message = null;
        try {
            Properties props = new Properties();
            props.setProperty("mail.imaps.host", "imap.gmail.com");
            props.setProperty("mail.imaps.user", USER_NAME);
            props.setProperty("mail.imaps.password", PASSWORD);
            props.setProperty("mail.imaps.port", "993");
            props.setProperty("mail.imaps.auth", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USER_NAME, PASSWORD);
                }
            });
            // create the imap store object and connect to the imap server
            Store store = session.getStore("imaps");
            store.connect();

            // create the inbox object and open it
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message value : messages) {

                if (value.getSubject().equals(subject)) {
                    value.setFlag(Flags.Flag.SEEN, true);
                    message = getTextFromMessage(value);
                    Log.info("email is found");

                } else {
                    Log.info("email not found");
                }
            }

            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    private static String getTextFromMessage(Message message) {
        String result = "";
        try {
            if (message.isMimeType("text/plain")) {
                result = message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                result = getTextFromMimeMultipart(mimeMultipart);
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) {
        StringBuilder result = new StringBuilder();
        try {
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result.append("\n").append(bodyPart.getContent());
                    break;
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result.append("\n").append(org.jsoup.Jsoup.parse(html).text());
                } else if (bodyPart.getContent() instanceof MimeMultipart) {
                    result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
                }
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }


}
