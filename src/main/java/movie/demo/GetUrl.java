package movie.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
//https://jx.618g.com/?url=https://v.qq.com/x/cover/zcw1fntf0nh8z79/x0027u7i1ip.html
public class GetUrl {
    public static void main(String[] args) {
        try {

            Document doc = Jsoup.connect("https://jx.618g.com/?url=http://v.qq.com/u/history/ ")
                    //.header("referer","https://api.yingxiangbao.cn/?url=https://www.iqiyi.com/v_19rqq17jbc.html")
                    //.header("upgrade-insecure-requests","1")
                    .get();
            //System.out.println(doc);
            Elements href = doc.select("#player");
            for (Element e:href) {
                System.out.println(e);
                String subUrl = "https://api.smq1.com/?url="+e.attr("src");
                System.out.println(subUrl);
                subUrl = subUrl.substring(40);
                System.out.println(subUrl);
            }
            //System.out.println(href);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String praseUrlForM3u8(String url){
        String subUrl = "";
        try {

            Document doc = Jsoup.connect(url)
                    //.header("referer","https://api.yingxiangbao.cn/?url=https://www.iqiyi.com/v_19rqq17jbc.html")
                    //.header("upgrade-insecure-requests","1")
                    .get();
            //System.out.println(doc);
            Elements href = doc.select("#player");

            for (Element e:href) {
                subUrl = "https://api.smq1.com/?url="+e.attr("src");
                subUrl = subUrl.substring(40);
                //System.out.println(subUrl);
            }
            //System.out.println(href);
            return subUrl;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return subUrl;
    }
}
