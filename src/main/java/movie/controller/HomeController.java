package movie.controller;

import movie.domain.Movie;
import movie.domain.Search;
import movie.service.DealUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class HomeController {
    @Autowired
    DealUrlService dealMjUrlImpl;

    @Autowired
    DealUrlService dealMovieUrlImpl;

    @Autowired
    DealUrlService dealRjUrlImpl;

    @RequestMapping(value = "/")
    public String index(Model model){
        System.out.println("有人来啦~");
        Search search = new Search();
        List<Movie> movieList = dealMovieUrlImpl.getLastestMovie();
        List<Movie> mjList = dealMjUrlImpl.getLastestMovie();
        List<Movie> rjList = dealRjUrlImpl.getLastestMovie();
        model.addAttribute("movieList",movieList);
        model.addAttribute("mjList",mjList);
        model.addAttribute("rjList",rjList);
        model.addAttribute("search",search);
        return "index";
    }

}
