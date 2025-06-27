package datn.springboot.websocket;

import datn.springboot.entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class PackageNotifier {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyNewPackage(Package pkg) {
        messagingTemplate.convertAndSend("/topic/package-init", pkg);
    }
}
