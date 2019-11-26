package de.freitag.stefan.alexa.dealbooker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum KEY {
  FROM,
  SUBJECT,
  TO
}

// TODO: Proper exception handling in case of problem with file and/ or missing keys.
class MailerConfiguration {

  private static final Logger log = LoggerFactory.getLogger(MailerConfiguration.class);

  private static Map<String, String> properties;

  static {
    File configFile = new File("mailer.cfg");
    try {
      FileReader reader = new FileReader(configFile);
      Properties props = new Properties();
      props.load(reader);
      properties =
          props.entrySet().stream()
              .collect(Collectors.toMap(e -> (String) e.getKey(), e -> (String) e.getValue()));
      reader.close();

    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
  }

  List<String> getTo() {
    return Arrays.stream(properties.get(KEY.TO.name()).split(",")).collect(Collectors.toList());
  }

  String getFrom() {
    return properties.get(KEY.FROM.name());
  }

  String getSubject() {
    return properties.get(KEY.SUBJECT.name());
  }
}
