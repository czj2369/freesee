package movie.service;

import movie.domain.Movie;
import movie.domain.Search;

import java.util.List;
import java.util.Map;

/**
 * 处理不同类型的视频url的接口
 */
public interface DealUrlService {
    /**
     * 从网站搜索得到搜索结果（剧集类)
     * @param searchName 搜索名
     * @return 搜索结果
     */
    public Search searchFrom(String searchName);

    /**
     * 从网站搜索得到搜索结果（电影类)
     * @param chooseSearchNet
     * @param searchName
     * @param parseUrl
     * @return
     */
    public Search searchFrom(String chooseSearchNet,String searchName, String parseUrl);

    /**
     * 如果是剧集的话，需要得到每一集的url地址
     * @param url 传入能够搜索到剧集的url地址
     * @return 剧集map key为每一集的url地址，value为每一集名称
     */
    public Map<String,String> getEpisodes(String url);

    /**
     * 对外的接口方法
     * 从播放界面得到播放器的地址（PS：在watch.html页面中使用）
     * @param url 播放界面的url地址
     * @return 播放器的地址（一般是iframe中的src属性）
     */
    public String getPlayUrl(String url);

    /**
     * 对外的接口方法，使用这个方法可以得到经过处理的搜索界面的内容
     * 1、如果是电影的话，得到的是处理过的Search对象
     * 2、如果是剧集的话，得到的是未处理的Search对象，从方法searchFrom的到的Search对象
     * @param searchType 搜索的类型，在搜索框的下拉框可以选择 vqq=电影，MJjuji=美剧，RJjuji=日剧，HJjuji=韩剧
     * @param searchName 搜索名称
     * @return
     */
    public Search getSearch(String searchType,String searchName);

    /**
     * 获取最新电影(剧目)信息
     */
    public List<Movie> getLastestMovie();
}
