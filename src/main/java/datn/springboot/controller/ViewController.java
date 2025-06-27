package datn.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showPage() {
        return "dashboard";
    }

    @GetMapping("/zones")
    public String showZonesPage() {
        return "zone"; // Tương ứng với templates/zone.html
    }

    @GetMapping("/packages")
    public String showPackagesPage() {
        return "package";
    }

    @GetMapping("/sidebar")
    public String showSiderbarPage() {
        return "sidebar";
    }

    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard";
    }

    @GetMapping("shelf")
    public String showShelfPage() {
        return "shelf";
    }
    @GetMapping("/popup")
    public String showPopupPage() {
        return "popup-package"; // KHÔNG cần .html
    }
}
