package movie.domain;

import java.util.List;
import java.util.Map;

public class Search {
    private String searchName;
    private Map<String,String> parseUrlList;//播放页面列表
    private String briefContent;//简介
    private String imgUrl;//图片地址

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Map<String, String> getParseUrlList() {
        return parseUrlList;
    }

    public void setParseUrlList(Map<String, String> parseUrlList) {
        this.parseUrlList = parseUrlList;
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchName='" + searchName + '\'' +
                ", parseUrlList=" + parseUrlList +
                ", briefContent='" + briefContent + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
