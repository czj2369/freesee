package movie.domain;

/**
 * 搜索到的电影的实体
 */
public class Movie {
    private String movieName;
    private String movieImgUrl;
    private String movieHref;

    public String getMovieImgUrl() {
        return movieImgUrl;
    }

    public void setMovieImgUrl(String movieImgUrl) {
        this.movieImgUrl = movieImgUrl;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieHref(String movieHref) {
        this.movieHref = movieHref;
    }

    public String getMovieHref() {
        return movieHref;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieName='" + movieName + '\'' +
                ", movieImgUrl='" + movieImgUrl + '\'' +
                ", movieHref='" + movieHref + '\'' +
                '}';
    }
}
