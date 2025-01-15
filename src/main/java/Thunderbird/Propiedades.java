package Thunderbird;

import jakarta.mail.*;
import java.util.Properties;

public class Propiedades {

    // Metodo para enviar correos
    public static Session credencialesEnviar(String usuario, String contrasena) {

        // Propiedades de configuración
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "damplaya.hopto.org");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "false");

        // Crear sesión con autenticación
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario + "@damplaya.hopto.org", contrasena);
            }
        });

        // Validar conexión al servidor SMTP
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect();
            transport.close();
        } catch (Exception e) {
            System.out.println("Error de autenticación o conexión: " + e.getMessage());
            return null;
        }

        return session;
    }

    // Metodo para recibir correos
    public static Session credencialesRecibir(String usuario, String contrasena) {

        // Propiedades de configuración
        Properties properties = new Properties();
        properties.put("mail.pop3.host", "damplaya.hopto.org");
        properties.put("mail.pop3.port", "110");
        properties.put("mail.pop3.auth", "true");
        properties.put("mail.pop3.starttls.enable", "false");
        properties.put("mail.pop3.ssl.enable", "false");

        // Crear sesión con autenticación
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contrasena);
            }
        });

        // Validar conexión al servidor POP3
        try {
            Store store = session.getStore("pop3");
            store.connect("damplaya.hopto.org", usuario + "@damplaya.hopto.org", contrasena);
            store.close();
        } catch (Exception e) {
            System.out.println("Error de autenticación o conexión: " + e.getMessage());
            return null;
        }

        return session;
    }

}
