package Thunderbird;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Scanner;

public class MailSender {
    public static void enviarEmail(String usuario, String contrasena) {
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario el destinatario, asunto y mensaje
        System.out.print("Introduce el destinatario del correo (sin @damplaya....): ");
        String destinatario = scanner.nextLine();

        System.out.print("Introduce el asunto del correo: ");
        String asunto = scanner.nextLine();

        System.out.print("Introduce el contenido del correo: ");
        String textoMensaje = scanner.nextLine();

        // Confirmar los detalles antes de enviar
        System.out.println("\n--- Detalles del correo ---");
        System.out.println("Destinatario: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje: " + textoMensaje);
        System.out.print("¿Estás seguro de que quieres enviar este correo? (s/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("s")) {

            Session session = Propiedades.credencialesEnviar(usuario, contrasena);

            try {
                // Crear el mensaje
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(usuario +"@damplaya.hopto.org"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario +"@damplaya.hopto.org"));
                message.setSubject(asunto);
                message.setText(textoMensaje);

                // Enviar el correo
                Transport.send(message);

                System.out.println("Correo enviado exitosamente.\n");
            } catch (MessagingException e) {
                System.out.println("Error al enviar el correo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("El correo no ha sido enviado.");
        }
    }
}
