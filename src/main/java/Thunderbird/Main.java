package Thunderbird;

import jakarta.mail.Session;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String usuario;
        String contrasena = "";
        Session verificar;

        System.out.println("Bienvenido a Thunderbird.");
        logoInicio();
        do {
            System.out.println("Ingrese el nombre de usuario (sin @damplaya....) (s para salir): ");
            usuario = scanner.nextLine();
            if (usuario.equalsIgnoreCase("s")) {
                System.out.println("Gracias por su atención.");
                exit = true;
                break; // Salir del bucle inmediatamente
            }

            System.out.println("Ingrese la contraseña:");
            contrasena = scanner.nextLine();

            verificar = Propiedades.credencialesEnviar(usuario, contrasena);
            if (verificar == null) {
                System.out.println("Credenciales inválidas o error de conexión. Por favor, inténtelo de nuevo.");
            }
        } while (verificar == null && !exit);

        System.out.println("Inicio de sesion realizado correctamente.");
        // Menú principal
        while (!exit) {

            mostrarMenu();

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    // Enviar mensaje
                    MailSender.enviarEmail(usuario, contrasena);
                    break;
                case 2:
                    // Recibir mensaje
                    MailReceiver.recibirEmail(usuario, contrasena);
                    break;
                case 3:
                    // Salir
                    System.out.println("Saliendo del programa...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción entre 1 y 3.");
                    break;
            }
        }

        scanner.close();
    }

    public static void mostrarMenu(){
        System.out.println("|--------------------|");
        System.out.println("|    THUNDERBIRD     |");
        System.out.println("|--------------------|");
        System.out.println("| 1. Enviar mensaje  |");
        System.out.println("| 2. Recibir mensaje |");
        System.out.println("| 3. Salir           |");
        System.out.println("|--------------------|");
        System.out.print("Elige una opción (1-3): ");
    }

    public static void logoInicio(){
        System.out.println("   \\ \\/ /");
        System.out.println("    (o.o)");
        System.out.println("     (   )");
        System.out.println("     `-'-`");
    }
}
