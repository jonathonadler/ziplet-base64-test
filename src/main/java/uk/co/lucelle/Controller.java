package uk.co.lucelle;

import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @RequestMapping("/")
    public @ResponseBody String index(@RequestBody String data) {
        // echo
        return data;
    }


}
