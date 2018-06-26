package de.freitag.stefan.alexa.dealbooker;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mailer {

    private static final Logger log = LoggerFactory.getLogger(Mailer.class);

    private static final String FROM = "stefan@stefreitag.de";

    private static final String TO = "stefan@stefreitag.de";

    // The subject line for the email.
    private static final String SUBJECT = "A deal has been booked";


    static void sendMail(DealType dealType, int amount, Unit unit, Tenor tenor){

        String rawTextBody = dealType.getText() + " " + amount + " " + unit + " " + tenor;
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.EU_WEST_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(TO))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(rawTextBody)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(FROM);
            client.sendEmail(request);
            log.info("Email was sent");
        } catch (Exception ex) {
            log.error("The email was not sent. Error message: "
                    + ex.getMessage());
        }

    }

}
