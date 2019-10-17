package movie.controller;

import movie.domain.Search;
import movie.service.DealUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WatchController {

    @Autowired
    DealUrlService dealMovieUrlImpl;

    @Autowired
    DealUrlService dealMjUrlImpl;

    @Autowired
    DealUrlService dealRjUrlImpl;

    @Autowired
    DealUrlService dealHjUrlImpl;

    @RequestMapping(value = "/watch")
    public String demo(Model model, @RequestParam(value = "type")String searchType,@RequestParam(value = "url")String url){
        String playUrl = "";
        Search search = new Search();
        if(searchType.equals("MJjuji")){
            playUrl = dealMjUrlImpl.getPlayUrl(url);
        }else if(searchType.equals("RJjuji")){
            playUrl = dealRjUrlImpl.getPlayUrl(url);
        }else if(searchType.equals("HJjuji")){
            playUrl = dealHjUrlImpl.getPlayUrl(url);
        }else if(searchType.equals("vqq")){
            playUrl = dealMovieUrlImpl.getPlayUrl(url);
        }
        model.addAttribute("playUrl",playUrl);
        model.addAttribute("searchType",searchType);
        model.addAttribute("search",search);
        return "watch";
    }
}
