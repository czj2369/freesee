package movie.demo;

import movie.domain.Search;
import movie.service.DealMovieUrlImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        String searchName = "生活大爆炸";
        String addpre = "https://www.meijutt.com/";//地址前缀
        try {
            //获取搜索页面的html内容
            Document doc = Jsoup.connect("https://www.meijuxz.com/videoplay/1207-1-1.html")
                    .get();
            //从html页面获取每一个结果的a标签，并且得到图片，简介，名称
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
