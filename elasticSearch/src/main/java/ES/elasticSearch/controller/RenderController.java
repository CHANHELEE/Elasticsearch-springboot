package ES.elasticSearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RenderController {

    @GetMapping("/finder")
    public String getFinder( ) {

        return "/finder";
    }

}
