package project.moviewebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class represents a controller for the index page.
 */
@Controller
public class IndexController {

    /**
     * Handle the request for the index page.
     * @return the redirect url of the index
     */
    @RequestMapping({"", "/", "/index", "/movies"})
    public String getIndex() {
        return "redirect:/movies/sortby/id/page/1";
    }

}
