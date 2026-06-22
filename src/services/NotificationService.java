package services;

import interfaces.Notifiable;
import java.util.List;

/**
 * SRP: Only coordinates sending notifications across channels.
 * DIP: Depends on Notifiable abstraction — not EmailNotification or SMSNotification directly.
 * OCP: Adding a new channel (e.g. PushNotification) requires zero changes here.
 * ISP: Uses the small, focused Notifiable interface — not a bloated interface with unrelated methods.
 */
public class NotificationService {
    private final List<Notifiable> channels;

    public NotificationService(List<Notifiable> channels) {
        this.channels = channels;
    }

    public void notifyAll(String recipient, String message) {
        for (Notifiable channel : channels) {
            channel.sendNotification(recipient, message);
        }
    }
}
