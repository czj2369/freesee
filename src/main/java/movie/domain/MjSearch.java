package movie.domain;

import java.util.List;
import java.util.Map;

/**
 * 美剧搜索结果实体类
 */
public class MjSearch {
    private List<String> searchName;
    private Map<String,String> parseUrlList;//播放页面列表
    private List<String> briefContent;//简介
    private List<String> imgUrl;//图片地址
    private Map<String,String> episodesList;//每一部的集数

    public void setSearchName(List<String> searchName) {
        this.searchName = searchName;
    }

    public List<String> getSearchName() {
        return searchName;
    }

    public List<String> getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(List<String> briefContent) {
        this.briefContent = briefContent;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
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
        return "MjSearch{" +
                "searchName='" + searchName + '\'' +
                ", parseUrlList=" + parseUrlList +
                ", briefContent=" + briefContent +
                ", imgUrl=" + imgUrl +
                '}';
    }
}
