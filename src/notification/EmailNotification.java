package notification;

import interfaces.Notifiable;
import ui.ConsoleUI;

// OCP: new notification channel — NotificationService never modified
// LSP: works wherever Notifiable is expected
public class EmailNotification implements Notifiable {

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println(
            ConsoleUI.BOLD + ConsoleUI.BR_CYAN + "  [✉  EMAIL]" + ConsoleUI.RESET +
            ConsoleUI.WHITE + "  " + ConsoleUI.BOLD + recipient + ConsoleUI.RESET +
            ConsoleUI.DIM   + "  →  " + ConsoleUI.RESET +
            ConsoleUI.WHITE + message + ConsoleUI.RESET
        );
    }
}
