package Thunderbird;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import java.util.Scanner;

public class MailReceiver {

    public static void recibirEmail(String usuario, String contrasena) {

        Session session = Propiedades.credencialesRecibir(usuario, contrasena);

        if (session == null) {
            System.out.println("Error: No se pudo establecer una sesión válida. Por favor, revisa las credenciales.");
            return;
        }

        try {
            // Conexión al servidor POP3 para recibir mensajes
            Store store = session.getStore("pop3");
            store.connect("damplaya.hopto.org", usuario, contrasena);

            // Obtener las carpetas de mensajes
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY); // Abrir la bandeja de entrada solo para leer

            // Obtener los mensajes
            Message[] messages = inbox.getMessages();
            if (messages.length == 0) {
                System.out.println("No tienes correos en la bandeja de entrada.");
                inbox.close(false);
                store.close();
                return;
            }

            // Mostrar la lista de correos
            System.out.println("\n--- Lista de correos ---");
            for (int i = 0; i < messages.length; i++) {
                String from = ((InternetAddress) messages[i].getFrom()[0]).getAddress();
                String subject = messages[i].getSubject();
                System.out.println((i + 1) + ". De: " + from + " | Asunto: " + subject);
            }

            // Pedir al usuario que seleccione un correo
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nSelecciona el número del correo para ver su contenido (o -1 para salir): ");

            int selectedEmail;
            try {
                selectedEmail = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número válido.");
                inbox.close(false);
                store.close();
                return;
            }

            // Si el usuario elige -1, salir
            if (selectedEmail == -1) {
                System.out.println("Saliendo...");
                inbox.close(false);
                store.close();
                return;
            }

            // Verificar si la opción es válida
            if (selectedEmail < 1 || selectedEmail > messages.length) {
                System.out.println("Opción no válida.");
                inbox.close(false);
                store.close();
                return;
            }

            // Obtener el correo seleccionado
            Message message = messages[selectedEmail - 1];

            // Mostrar el contenido del correo
            System.out.println("\n--- Contenido del correo ---");
            System.out.println("De: " + ((InternetAddress) message.getFrom()[0]).getAddress());
            System.out.println("Asunto: " + message.getSubject());
            System.out.println("Fecha: " + message.getSentDate());
            System.out.println("\nCuerpo del mensaje: ");
            System.out.println("--------------------------------------");

            // Mostrar el contenido del mensaje, manejando distintos tipos
            Object content = message.getContent();
            if (content instanceof String) {
                System.out.println((String) content);
            } else {
                System.out.println("El contenido del mensaje no es texto plano.");
            }

            // Cerrar la conexión
            inbox.close(false);
            store.close();

        } catch (MessagingException e) {
            System.out.println("Error de conexión o al acceder a los correos: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.out.println("Error al leer el contenido del mensaje: " + e.getMessage());
        }
    }
}
