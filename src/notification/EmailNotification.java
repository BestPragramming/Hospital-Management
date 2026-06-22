package notification;

import interfaces.Notifiable;

// OCP: new notification channel — NotificationService never modified
// LSP: works wherever Notifiable is expected
public class EmailNotification implements Notifiable {

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("[EMAIL] To: " + recipient + " | " + message);
    }
}
