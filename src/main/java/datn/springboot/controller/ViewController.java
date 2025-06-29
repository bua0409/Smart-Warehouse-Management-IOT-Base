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
        return "zone";
    }

    @GetMapping("/packages")
    public String showPackagesPage() {
        System.out.println("✅ Đã vào được /packages");
        return "mypackage";
    }

    @GetMapping("/sidebar")
    public String showSiderbarPage() {
        return "sidebar";
    }

    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard";
    }

    @GetMapping("/shelf")
    public String showShelfPage() {
        return "shelf";
    }

    @GetMapping("/block")
    public String showBlockPage() {
        return "block";
    }

    @GetMapping("/order")
    public String showOrderPage(){ return "order";}
}
