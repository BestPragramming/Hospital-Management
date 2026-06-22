package interfaces;

// ISP: small, focused interface — only one method, nothing extra
public interface Notifiable {
    void sendNotification(String recipient, String message);
}
