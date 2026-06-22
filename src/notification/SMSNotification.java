package notification;

import interfaces.Notifiable;
import ui.ConsoleUI;

// OCP: new channel without touching NotificationService
// LSP: works wherever Notifiable is expected
public class SMSNotification implements Notifiable {

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println(
            ConsoleUI.BOLD + ConsoleUI.BR_GREEN + "  [✆  SMS  ]" + ConsoleUI.RESET +
            ConsoleUI.WHITE + "  " + ConsoleUI.BOLD + recipient + ConsoleUI.RESET +
            ConsoleUI.DIM   + "  →  " + ConsoleUI.RESET +
            ConsoleUI.WHITE + message + ConsoleUI.RESET
        );
    }
}
