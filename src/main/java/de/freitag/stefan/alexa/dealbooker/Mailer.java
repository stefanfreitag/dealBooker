package de.freitag.stefan.alexa.dealbooker;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

class Mailer {

    private static final Logger log = LoggerFactory.getLogger(Mailer.class);

    private static final String FROM = "stefan.freitag@rwe.com";

    private static final List<String> TO = Arrays.asList("stefan@stefreitag.de", "stefan.freitag@rwe.com");


    private static final String SUBJECT = "A deal has been booked";


    static void sendMail(DealType dealType, int amount, Unit unit, Product product, int price) {

        final String rawTextBody = Mailer.generateRawTextMessage(dealType, amount, unit, product, price);
        log.info("Raw email text: " + rawTextBody);

        final String htmlTextBody = Mailer.generateHtmlTextMessage(dealType, amount, unit, product, price);
        log.info("HTML email text: " + htmlTextBody);
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
                                            .withCharset("UTF-8").withData(rawTextBody))
                                    .withHtml(new Content().withCharset("UTF-8").withData(htmlTextBody)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(FROM);
            client.sendEmail(request);
            log.info("An e-mail was sent to " + TO);
        } catch (final Exception exception) {
            log.error("The email to " + TO + " was not sent. Error message: "
                    + exception.getMessage());
        }

    }

    static String generateRawTextMessage(final DealType dealType, final int amount, final Unit unit, final Product product, final int price) {
        Object[] params = new Object[]{dealType, amount, unit, product, price};

        return MessageFormat.format("Hello trader,\r\n" +
                "\r\n" +
                "a deal has been made.\r\n" +
                "\r\n" +
                "Deal type:\t {0}\r\n" +
                "Quantity: {1}\r\n" +
                "Unit: {2}\r\n" +
                "Product: {3}\r\n" +
                "Price: {4}\r\n" +
                "\r\n" +
                "\r\n" +
                "Best regards,\r\n" +
                "The Deal Booker service", params);
    }

    static String generateHtmlTextMessage(final DealType dealType, final int amount, final Unit unit, final Product product, final int price) {
        Object[] params = new Object[]{dealType.getText(), amount, unit.getText(), product, price};

        return MessageFormat.format(
                "<h3>Hello,</h3><br />" +
                "<p>a deal has been made.</p><br />" +
                "<table border=\"1\">" +
                "<tr><th colspan=\"2\">Deal information</th></tr>"+
                "<tr><td>Deal type</td><td>{0}</td></tr>" +
                "<tr><td>Quantity</td><td>{1}</td></tr>" +
                "<tr><td>Unit</td><td>{2}</td></tr>" +
                "<tr><td>Product</td><td>{3}</td></tr>" +
                        "<tr><td>Price</td><td>{4}</td></tr>" +
                "</table>" +
                        "<br />" +
                "<br />" +
                "Best regards,<br />" +
                "<i>The Deal Booker service</i>", params);
    }

}
