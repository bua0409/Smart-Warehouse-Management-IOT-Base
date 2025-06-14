package datn.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/packages")
    public String showPackagePage() {
        return "index";
    }

    @GetMapping("/")
    public String showPage() {
        return "index";
    }
}
