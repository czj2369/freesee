package movie.demo;

import movie.dao.UserMapper;
import movie.domain.Search;
import movie.domain.User;
import movie.service.DealMovieUrlImpl;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainTest {

    public static void main(String[] args) {
//        String searchName = "绿豆";
//        String addpre = "https://www.meijutt.com/";//地址前缀
//       try {
//           //获取搜索页面的html内容
//           Document doc = Jsoup.connect("http://www.2hanju.com/")
//                   .get();
//           //从html页面获取每一个结果的a标签，并且得到图片，简介，名称
//           Elements select = doc.select("#all > div> ul > a");
//           for(Element e:select){
//              if(e.attr("title").contains(searchName)){
//                  //System.out.println(e);
//              }
//           }
//           Document doc1 = Jsoup.connect("http://www.2hanju.com/hanju/2185.html").get();
//           System.out.println(doc1);
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
        try {
            Document doc = Jsoup.connect("https://www.rijutv.com/").get();
            Elements e = doc.select(".m-item > a > img");
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
