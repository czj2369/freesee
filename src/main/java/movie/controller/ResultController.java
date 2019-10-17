package movie.controller;

import movie.domain.Search;
import movie.service.DealUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ResultController {

    @Autowired
    DealUrlService dealMjUrlImpl;

    @Autowired
    DealUrlService dealMovieUrlImpl;

    @Autowired
    DealUrlService dealRjUrlImpl;

    @Autowired
    DealUrlService dealHjUrlImpl;

    @RequestMapping(value = "/searchresult")
    public String single(Model model, @RequestParam(value = "searchName") String searchName, @RequestParam(value = "type")String searchType){
        //Search search = DealUrl.getSearch("vqq",searchName);
        Search search = null;
        if(searchType.equals("vqq")){
            search = dealMovieUrlImpl.getSearch(searchType,searchName);
        }else if(searchType.equals("MJjuji")){
            search = dealMjUrlImpl.getSearch(searchType,searchName);
        }else if(searchType.equals("RJjuji")){
            search = dealRjUrlImpl.getSearch(searchType,searchName);
        }else if(searchType.equals("HJjuji")){
            search = dealHjUrlImpl.getSearch(searchType,searchName);
        }
        model.addAttribute("search",search);
        model.addAttribute("searchType",searchType);
        if (search.getParseUrlList()==null){
            return "error";
        }

        return "searchresult";
    }

    @RequestMapping(value = "/searchresult/episodes")
    public String episodes(Model model,@RequestParam(value = "url") String url,@RequestParam(value = "type") String searchType){
        Map<String, String> map = null;
        Search search = new Search();
        if(searchType.equals("vqq")){
            map.put("无此结果","无此结果");
        }else if(searchType.equals("MJjuji")){
            map = dealMjUrlImpl.getEpisodes(url);
        }else if(searchType.equals("RJjuji")){
            map = dealRjUrlImpl.getEpisodes(url);
        }else if(searchType.equals("HJjuji")){
            map = dealHjUrlImpl.getEpisodes(url);
        }
        model.addAttribute("episodes",map);
        model.addAttribute("search",search);
        model.addAttribute("searchType",searchType);
        return "episodes";
    }
}
