package notification;

import interfaces.Notifiable;

// OCP: new channel without touching NotificationService
// LSP: works wherever Notifiable is expected
public class SMSNotification implements Notifiable {

    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("[SMS]   To: " + recipient + " | " + message);
    }
}
