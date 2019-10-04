package movie.demo;

import movie.domain.MjSearch;
import movie.domain.Search;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class GetUrlForVqq {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String addpre = "https://www.meijutt.com";//地址前缀
        String searchName = "生活大爆炸";
        Map<String,String> parseUrlList = new LinkedHashMap<>();
        MjSearch mjSearch = new MjSearch();
        List<String> imgUrlList = new LinkedList<>();
        List<String> briefContentList = new LinkedList<>();
        List<String> searchNameList = new LinkedList<>();
        try {
            Document doc = Jsoup.connect("https://www.meijutt.com/search/index.asp?searchword="+gb2312eecode(searchName))
                    .get();
            Elements e = doc.select("body > div.warp > div.list3_cn_box > div> div > div > a");
            for(Element element:e){
                parseUrlList.put(addpre+element.attr("href"),element.attr("title"));
                searchNameList.add(element.attr("title"));
                briefContentList.add(element.attr("title"));
                imgUrlList.add(element.select("img").attr("src"));
            }
            mjSearch.setParseUrlList(parseUrlList);
            mjSearch.setBriefContent(briefContentList);
            mjSearch.setImgUrl(imgUrlList);
            mjSearch.setSearchName(searchNameList);
            System.out.println(mjSearch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String gb2312eecode(String string) throws UnsupportedEncodingException {
        StringBuffer gbkStr = new StringBuffer();
        byte[] gbkDecode = string.getBytes("gb2312");
        for (byte b : gbkDecode) {
            gbkStr.append("%"+Integer.toHexString(b & 0xFF).toUpperCase());
        }
        return gbkStr.toString();
    }


    /**
     * String addpre = "https://www.meijutt.com";//地址前缀
     Map<String,String> parseUrlList = new LinkedHashMap<>();
     MjSearch mjSearch = new MjSearch();
     List<String> imgUrlList = new LinkedList<>();
     List<String> briefContentList = new LinkedList<>();
     List<String> searchNameList = new LinkedList<>();
     try {
     Document doc = Jsoup.connect("https://www.meijutt.com/search/index.asp?searchword="+gb2312eecode(searchName))
     .get();
     Elements e = doc.select("body > div.warp > div.list3_cn_box > div> div > div > a");
     for(Element element:e){
     parseUrlList.put(addpre+element.attr("href"),element.attr("title"));
     searchNameList.add(element.attr("title"));
     briefContentList.add(element.attr("title"));
     imgUrlList.add(element.select("img").attr("src"));
     }
     mjSearch.setParseUrlList(parseUrlList);
     mjSearch.setBriefContent(briefContentList);
     mjSearch.setImgUrl(imgUrlList);
     mjSearch.setSearchName(searchNameList);

     } catch (IOException e) {
     e.printStackTrace();
     }
     return mjSearch;
     */
}
