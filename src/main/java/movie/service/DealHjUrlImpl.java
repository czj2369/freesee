package movie.service;

import movie.domain.Movie;
import movie.domain.Search;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DealHjUrlImpl implements DealUrlService{

   private Document doc;
    private String addpre = "http://2hanju.com";//地址前缀

   private void init(){
       if(doc==null){
           Connection connect = Jsoup.connect("http://2hanju.com/");
           try {
               doc = connect.get();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
    @Override
    public Search searchFrom(String searchName) {
       // 初始化 doc
        init();
        // 封装搜索结果集
        Search search = new Search();
        // 取页面所有韩剧所在的a标签
        Elements es = doc.select("#all > div > ul > a");
        // 搜索的韩剧url
        String nUrl ="";
        // 正确的韩剧名
        String realSearchName="";
        for(Element href: es){
            if(href.attr("title").contains(searchName)){
                nUrl = "http://2hanju.com"+href.attr("href");
                realSearchName = href.attr("title");
                break;
            }
        }
        // System.out.println(nUrl);
        try {
            Document doc2 = Jsoup.connect(nUrl).get();
            // 封装search
            Map<String, String> obj = new LinkedHashMap<>();
            search.setImgUrl(doc2.select("#all > div.wenzhang_left > div:nth-child(2) > div.link_box_main > div.jianjie_left > img").attr("src"));
            search.setSearchName(realSearchName);
            search.setBriefContent(doc2.select("#all > div.wenzhang_left > div:nth-child(2) > div.link_box_main > div.jianjie_right > p:nth-child(2)").text());
            obj.put(nUrl,realSearchName);
            search.setParseUrlList(obj);
            //System.out.println(search);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return search;
    }

    @Override
    public Search searchFrom(String chooseSearchNet, String searchName, String parseUrl) {
        return null;
    }

    /**
     *
     * @param url 传入能够搜索到剧集的url地址
     * @return
     */
    @Override
    public Map<String, String> getEpisodes(String url) {
        Map<String,String> Episodes=new LinkedHashMap<>();

        try {
            Connection connect = Jsoup.connect(url);

            Document doc = connect.get();
            Elements e = doc.select("#all > div > div > div > a");//爬取页数
            for (Element href:e){
                Episodes.put(addpre+href.attr("href"),href.text());//播放页面链接，第几集
                //System.out.println(Episodes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Episodes;
    }

    /**
     *
     * @param url 播放界面的url地址
     * @return
     */
    @Override
    public String getPlayUrl(String url) {
        String res = "";
        try {
            Document doc = Jsoup.connect(url).get();
            //获取的标签是播放器的地址
            res = doc.select("#all > div.play > div.playleft > iframe").attr("src");
            //System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 搜索结果接口
     * @param searchName
     * @return
     */
    @Override
    public Search getSearch(String searchType, String searchName) {
        Search search = searchFrom(searchName);
        return search;
    }

    /**
     * 获取首页显示的韩剧列表
     * @return
     */
    @Override
    public List<Movie> getLastestMovie() {
        List<Movie> movieList = new ArrayList<>();
        init();
        Elements e = doc.select("#all > div > ul > li > a");
        int getNum = 8;//需要获取的个数
        //创建Movie对象，并对里面的属性进行赋值（值的来源从上面的e变量的标签区域获取）
        for(int i=8;i<getNum+8;i++){
            Movie movie = new Movie();
            String imgUrl = e.get(i).select("a > img").attr("src");
            movie.setMovieImgUrl(imgUrl);
            String movieName = e.get(i).select("a").attr("title");
            movie.setMovieName(movieName);
            movie.setMovieHref(addpre+e.get(i).select("a").attr("href"));
            movieList.add(movie);
        }
        return movieList;
    }

    public static void main(String[] args) {
        DealHjUrlImpl d = new DealHjUrlImpl();
        //d.searchFrom("当你沉睡时");
        //d.getEpisodes("http://2hanju.com/hanju/1856.html");
        //d.getPlayUrl("http://2hanju.com/player/1856_1_1.html");
        d.getLastestMovie();
    }
}
