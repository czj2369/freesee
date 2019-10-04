package movie.service;

import movie.domain.Movie;
import movie.domain.Search;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class DealMjUrlImpl implements DealUrlService{

    /**
     * 从美剧网（https://www.ttkmj.net/）获取资源
     * @param searchName 美剧名
     * @return 返回搜索结果
     */
    public Search searchFrom(String searchName) {
        String addpre = "https://www.ttkmj.net";//地址前缀
        Map<String,String> parseUrlList = new LinkedHashMap<>();
        Search mjSearch = new Search();
        try {
            //获取搜索页面的html内容
            Document doc = Jsoup.connect("https://www.ttkmj.net/?s="+searchName)
                    .get();
            //从html页面获取每一个结果的a标签，并且得到图片，简介，名称
            Elements e = doc.select("#subject_list > div > ul > li> div.info > h4 > a");
            mjSearch.setImgUrl(e.get(0).select("img").attr("data-src"));
            mjSearch.setBriefContent(searchName);
            mjSearch.setSearchName(searchName);
            for(Element element:e){
                //map的key为某一结果的URL，value为某一结果的名称
                parseUrlList.put(element.attr("href"),element.text());
            }
            mjSearch.setParseUrlList(parseUrlList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mjSearch;
    }

    @Override
    public Search searchFrom(String chooseSearchNet, String searchName, String parseUrl) {
        return null;
    }

    /**
     * 得到每一部美剧的剧集
     * @param url 选择搜索结果中的美剧获得的url
     * @return 返回一个map key为剧集的地址 value为第几集
     */
    public Map<String,String> getEpisodes(String url){
        Map<String,String> map = new LinkedHashMap<>();
        try {
            Document doc = Jsoup.connect(url).get();
            //获取的元素是每一集的url
            //#article > div:nth-child(13) > a:nth-child(1) #article > div:nth-child(10)
            //#article > div:nth-child(7) > a:nth-child(1)  #article > div:nth-child(7)
            Elements elements = doc.select("div.episode_list").select("a");

            for (Element e:elements){
                //Document doc1 = Jsoup.connect(e.attr("href")).get();
                //Elements select = doc1.select("#article > iframe");
                map.put(e.attr("href"),e.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
    /**
     * 从传进来的url获取播放器的地址
     * @param url 传进来的是播放页面的地址
     * @return 返回播放器的url地址
     */
    public String getPlayUrl(String url){
        String res = "";
        try {
            Document doc = Jsoup.connect(url).get();
            //获取的标签是播放器的地址
            Elements elements = doc.select("#article > iframe");
            res = elements.attr("src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 将搜索的字符串中文转成gb2312编码的格式
     * @param string
     * @return
     * @throws UnsupportedEncodingException
     */
    public String gb2312eecode(String string) throws UnsupportedEncodingException {
        StringBuffer gbkStr = new StringBuffer();
        byte[] gbkDecode = string.getBytes("gb2312");
        for (byte b : gbkDecode) {
            gbkStr.append("%"+Integer.toHexString(b & 0xFF).toUpperCase());
        }
        return gbkStr.toString();
    }

    /**
     * 返回搜素结果
     * @param searchType 搜索类型
     * @param searchName 搜索美剧名
     * @return
     */
    public Search getSearch(String searchType,String searchName){
        Search search = searchFrom(searchName);
        return search;
    }

    /**
     * 得到最新的美剧
     * @return
     */
    @Override
    public List<Movie> getLastestMovie() {
        List<Movie> movieList = new ArrayList<>();
        try {
            Elements href = null;
            Document doc = Jsoup.connect("https://www.ttkmj.net/t/2019")
                    .get();
            String docHtml = doc.html();
            Elements e = doc.select("#subject_list > ul > li");
            for(int i=0;i<8;i++){
                Movie movie = new Movie();
                String imgUrl = e.get(i).select(".lazy").attr("data-src");
                movie.setMovieImgUrl(imgUrl.substring(0,imgUrl.length()-12)+".jpg");
                String movieName = e.get(i).select("h2").select("a").text();
                movie.setMovieName(movieName.substring(0,movieName.length()-7));
                movie.setMovieHref(e.get(i).select("h2").select("a").attr("href"));
                movieList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
