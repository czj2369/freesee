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
import java.util.*;

@Service
public class DealRjUrlImpl implements DealUrlService{//获取搜索结果页面
    private String prefix = "https://www.rijutv.com/index.php?c=so&module=&keyword=";//搜索的前缀,默认是腾讯视频搜索
    private String addpre = "https://www.rijutv.com";//地址前缀

    /**
     * 爬取搜索结果页面
     * @param searchName 电影名称
     * @return 搜索结果页面
     */
    public Search searchFrom(String searchName){

        Search search = new Search();
        try {
            Connection connect = Jsoup.connect(prefix+searchName);
            //Elements href = dealDoc(chooseSearchNet,doc);//根据页面搜索情况不同分情况处理标签
            Document doc = connect.get();
            Elements e = doc.select("body > div.warp > div > div.main-inner.mt20 > " +
                    "div.mod-search-list > div > div > div > h3 > a");//搜索结果页面，每个结果的selector
            String nUrl = "";
            for (Element href:e){
                nUrl = "https://www.rijutv.com"+href.attr("href");
            }
            Document doc2 = Jsoup.connect(nUrl).get();

            Map<String, String> obj = new LinkedHashMap<>();
            for (Element href:e){
                obj.put(addpre+href.attr("href"),href.attr("title"));
                search.setImgUrl(doc.select("body > div.warp > div > div.main-inner.mt20 > div.mod-search-list > div > div:nth-child(1) > a > img").attr("data-original"));
                search.setBriefContent(doc.select("body > div.warp > div > div.main-inner.mt20 > div.mod-search-list > div > div:nth-child(1) > div > div > p:nth-child(5)").text());
                search.setSearchName(searchName);

            }
            search.setParseUrlList(obj);
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
     * 集数页面
     * @param url 集数页面MAP
     * @return 链接和集数
     */
    public Map<String,String> getEpisodes(String url){
       Map<String,String> Episodes=new LinkedHashMap<>();

        try {
            Connection connect = Jsoup.connect(url);

            Document doc = connect.get();
            Elements e = doc.select("body > div.warp > div > div.main-inner > " +
                    "div:nth-child(3) > div.mod-main > div > ul > li> a");//爬取集数
            for (Element href:e){
                Episodes.put(addpre+href.attr("href"),href.text());//播放页面链接，第几集
            }
  /*          Iterator<Map.Entry<String, String>> iterator = Episodes.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                System.out.println( entry.getKey()+ entry.getValue());
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Episodes;
    }

    @Override
    public String getPlayUrl(String url) {
        String res = "";
        try {
            Document doc = Jsoup.connect(url).get();
            //获取的标签是播放器的地址
            Elements elements = doc.select("#playPath");
            res = "http:"+elements.attr("src");
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
    public Search getSearch(String searchType,String searchName){
        Search search = searchFrom(searchName);
        return search;
    }

    /**
     * 得到最新的日剧
     * @return
     */
    @Override
    public List<Movie> getLastestMovie() {
        List<Movie> movieList = new ArrayList<>();
        try {
            Elements href = null;
            //获取最新日剧的地址
            Document doc = Jsoup.connect("https://www.rijutv.com")
                    .get();
            String docHtml = doc.html();
            //需要获得区域selector
            Elements e = doc.select(".m-item");
            int getNum = 8;//需要获取的个数
            //创建Movie对象，并对里面的属性进行赋值（值的来源从上面的e变量的标签区域获取）
            for(int i=8;i<getNum+8;i++){
                Movie movie = new Movie();
                String imgUrl = e.get(i).select("a > img").attr("data-original");
                movie.setMovieImgUrl(imgUrl);
                String movieName = e.get(i).select("a").attr("title");
                movie.setMovieName(movieName);
                movie.setMovieHref("https://www.rijutv.com"+e.get(i).select("a").attr("href"));
                movieList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
