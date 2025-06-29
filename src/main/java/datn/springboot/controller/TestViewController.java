package datn.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestViewController {

    @GetMapping("/test")
    public String test() {
        return "package"; // kiểm tra load file package.html
    }
}
