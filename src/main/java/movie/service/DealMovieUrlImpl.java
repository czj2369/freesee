package movie.service;

import movie.domain.Movie;
import movie.domain.Search;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@PropertySource(value = "parseurl.properties")
public class DealMovieUrlImpl implements DealUrlService{
    /**
     * parseUrl 为解析前缀url，可以在parseurl.properties文件设置
     */
    private static String parseUrl;
    @Value("${movie.parse.url.isM3u8}")
    public void setParseUrl(String parseUrl) {
        DealMovieUrlImpl.parseUrl = parseUrl;
    }

    /**
     * prefixUrl 为无法得到解析的m3u8地址后，跳转到另一个解析地址，可以在parseurl.properties里面设置
     */
    private static String prefixUrl;
    @Value("${movie.parse.url.notmM3u8}")
    public void setPrefixUrl(String url){
        DealMovieUrlImpl.prefixUrl = url;
    }

    @Override
    public Search searchFrom(String searchName) {
        return null;
    }

    /**
     * 搜索视频，在不同的视频网站搜索，并将搜索出来的地址拼接成解析后的地址
     * @param chooseSearchNet 参数为搜索的网站，如腾讯视频，爱奇艺，优酷，etc
     * @param searchName 参数为搜索的内容
     * @param parseUrl 参数为解析的前缀地址 测试可用的地址为https://jx.618g.com/?url=
     * @return 返回值为处理后的url，前面是解析的
     */
    public Search searchFrom(String chooseSearchNet,String searchName, String parseUrl){
        String prefix = "https://v.qq.com/x/search/?q=";//搜索的前缀,默认是腾讯视频搜索
        if(chooseSearchNet.equals("vqq")){
            prefix = "https://v.qq.com/x/search/?q=";
        }else if(chooseSearchNet.equals("iqiyi")){
            prefix = "https://so.iqiyi.com/so/q_";
        }else if(chooseSearchNet.equals("youku")){
            prefix = "https://so.youku.com/search_video/q_";
        }
        Search search = new Search();
        try {
            Connection connect = Jsoup.connect(prefix+searchName);
            if(chooseSearchNet.equals("youku")){
                connect.header("cookie","__ysuid=1568523622533zkL; UM_distinctid=16d3a4de2c42e3-075c8ff9dc4f6-4f4f082e-e1000-16d3a4de2c5394; cna=nOADFvh+xiACAXQauIPPD1EJ; __ayft=1568641247024; __aysid=15686412470255t1; __ayscnt=1; ctoken=-Zlsma190ScSsi9XGim3nXVC; CNZZDATA1277958921=1715726255-1568638729-https%253A%252F%252Fwww.youku.com%252F%7C1568638729; _uab_collina=156864125414957373100197; P_ck_ctl=1D8270FADB583425EDFC2283BB312C2A; __arpvid=1568643040222hTm7rs-1568643040260; __aypstp=6; __ayspstp=6; isg=BKSkE_qNpWitsdEk_lWXIta6daJWlcnBCVxa4b7HEm-aaUQz50xNN9jbK4FUqgD_");
            }
            Document doc = connect.get();
            search.setImgUrl("http:"+doc.select("body > div.search_container > div.wrapper " +
                    "> div:nth-child(1) > div:nth-child(2) > div._infos > div > a > img").attr("src"));
            search.setSearchName(searchName);
            search.setBriefContent(doc.select("body > div.search_container > div.wrapper > " +
                    "div:nth-child(1) > div:nth-child(2) > div._infos > div > div > div.info_item.info_item_desc > span.desc_text").text());
            Elements href = dealDoc(chooseSearchNet,doc);//根据页面搜索情况不同分情况处理标签
            if(href != null && href.size()>0){
                Map<String,String> urlList = new HashMap<>();
                for (Element e:href) {
                    if(!doc.html().contains("c-inline-play")){
                        String str1 = e.attr("href");
                        String title = (e.attr("title")!=null && e.attr("title")!="")?e.attr("title"):searchName;
                        urlList.put(str1,title);
                    }else{
                        String str1 = e.attr("href");
                        String title = (e.select("em").text()!=null && e.select("em").text()!="")?e.select("em").text():searchName;
                        urlList.put(str1,title);
                    }
                }
                search.setParseUrlList(urlList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return search;
    }

    /**
     * 第二步，从获取的解析地址中获取其中的m3u8的视频地址
     * @param searchUrl 传进来的拼接完的解析地址
     * @return 返回获取到的m3u8地址
     */
    public static String parseUrl2m3u8(String searchUrl,String prefixUrl){
        String res = "";
        try {
            Document doc = Jsoup.connect(searchUrl).get();
            Elements href = doc.select("#player");
            for (Element e:href) {
                String str1 = e.attr("src");
                if(str1.contains("m3u8")){
                    res = str1.substring(17);
                }else{
                    res = prefixUrl + str1.substring(28);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 解析搜索界面的标签，得到视频的真实地址
     * @param chooseSearchNet 选择搜索的网站
     * @param doc 解析得到的Document，即需要在这个doc中提取出href地址
     * @return
     */
    private static Elements dealDoc(String chooseSearchNet,Document doc) {
        String docHtml = doc.html();
        Elements res = null;
        if(docHtml.contains("c-inline-play")){
            res = doc.select("body > div.search_container > div.wrapper > " +
                    "div:nth-child(1) > div:nth-child(2) > div._infos > div > h2 > a");
        }else if(docHtml.contains("mod_figures mod_figure_h")){
            res = doc.select("body > div.search_container > div.wrapper > div:nth-child(1) > div.result_item.result_item_v > " +
                    "div.result_video_fragment > div > ul > li> a");
        }else{
            res = doc.select("body > div.search_container > div.wrapper > div:nth-child(1) > " +
                    "div.result_series.result_intention > div.mod_figures.mod_figure_v > ul > li> strong > a");
        }
//        if(chooseSearchNet.equals("vqq")){
//            if(docHtml.contains("inline-telelist")){
//                res = doc.select("body > div.search_container > div.wrapper > div:nth-child(1) >" +
//                        " div:nth-child(2) > div._playlist > div > div > div > div> a");
//            }else if(docHtml.contains("inline-series-list")){
//                res = doc.select("body > div.search_container > div.wrapper > div:nth-child(1) > " +
//                        "div.result_item.result_item_v > div._playlist > div > div > div > span > div > a");
//            } else if(docHtml.contains("c-inline-play")){
//                res = doc.select("body > div.search_container > div.wrapper > div:nth-child(1) > " +
//                        "div.result_item.result_item_v > div._playlist > div > a");
//            }
//        } else if(chooseSearchNet.equals("iqiyi")){
//            if(docHtml.contains("info_item_bottom")){
//                res = doc.select("body > div.page-search > div.container.clearfix " +
//                        "> div.search_result_main > div > div.mod_search_result > div > ul " +
//                        "> li> div > div.info_item_bottom > div> div > a");
//            }
//        }else if(chooseSearchNet.equals("youku")){
//            res = doc.select("#bpmodule-main > div.sk-result-list > div:nth-child(1) > div.mod-main > div.mod-play.mod-film-play > div > a.btn.btn-play.active");
//        }
        return res;
    }

    @Override
    public Map<String, String> getEpisodes(String url) {
        return null;
    }

    @Override
    public String getPlayUrl(String url) {
        String res = "";
        try {
            Document doc = Jsoup.connect(url).get();
            //获取的标签是播放器的地址
            if(!doc.html().contains("c-inline-play")){
                Elements elements = doc.select("body > div:nth-child(3) > div.site_container.container_detail_top > " +
                        "div > div > div > div._playsrc > div:nth-child(3) > a:nth-child(1)");
                if(elements.attr("href")!=""){
                    res = prefixUrl+elements.attr("href");
                }else{
                    res = prefixUrl+url;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 返回搜素结果
     * @param chooseSearchNet 搜索的视频网站地址
     * @param searchName 搜索名称
     * @return
     */
    public Search getSearch(String chooseSearchNet,String searchName){
        //搜索得到结果封装成Search对象
        Search search = searchFrom(chooseSearchNet,searchName, parseUrl);
 //       Map<String,String> parseUrlList = search.getParseUrlList();
//        Map<String,String> afterChange = new HashMap<>();

        //如果可以获得视频的m3u8地址，则进行转换，如果不行则直接解析播放
//        for (Map.Entry<String,String> e:parseUrlList.entrySet()) {
//            afterChange.put(parseUrl2m3u8(e.getKey(),prefixUrl),e.getValue());
//        }

        //search.setParseUrlList(afterChange);
        return search;
    }

    /**
     * 首页获取最新电影信息
     * @return
     */
    public List<Movie> getLastestMovie(){
        List<Movie> movieList = new ArrayList<>();
        try {
            Elements href = null;
            Document doc = Jsoup.connect("https://v.qq.com/channel/movie")
                    .get();
            String docHtml = doc.html();
            Elements e = doc.select("#movie_v3_new > div.mod_bd.cf._quickopen._quickopen_duration > div > div> a");
            for(int i=0;i<8;i++){
                Movie movie = new Movie();
                movie.setMovieImgUrl("http:"+e.get(i).select("img").attr("src"));
                movie.setMovieName(e.get(i).select("img").attr("alt"));
                movie.setMovieHref(e.get(i).attr("href"));
                movieList.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }
}
