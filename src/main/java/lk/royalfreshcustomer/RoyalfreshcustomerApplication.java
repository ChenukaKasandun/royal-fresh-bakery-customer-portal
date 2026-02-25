package lk.royalfreshcustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@RestController
public class RoyalfreshcustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoyalfreshcustomerApplication.class, args);

        System.out.println("Hello World");

	}

    // mapping for return index page url --> [/index or /]x
    @RequestMapping(value = { "/index", "/" })
    public ModelAndView uiIndexPage() {
        ModelAndView indexPage = new ModelAndView();
        indexPage.setViewName("index.html");
        return indexPage;

    }

}
