package com.xcx.dailynews;

/**
 * Created by xcx on 2016/10/27.
 * 网络数据的Api
 */

public class ApiConstants {


    public static final String NETEAST_HOST = "http://c.m.163.com/";
    public static final String PHOTO_URL = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/";
    public static final String END_URL = "-20.html";
    public static final String ENDDETAIL_URL = "/full.html";
    //  public static final String LOGIN_URL = "http://120.24.76.152/Login/LoginServlet";
    public static final String LOGIN_URL = "http://192.168.191.1/Login/LoginServlet";
    public static final String SELECT_COLLECT_URL = "http://192.168.191.1/Login/LoginServlet";
    public static final String INSERT_COLLECT_URL = "http://192.168.191.1/Login/InsertServlet";
    public static final String DELETE_COLLECT_URL = "http://192.168.191.1/Login/LoginServlet";
    public static final String UPDATE_COLLECT_URL = "http://192.168.191.1/Login/UpdateServlet";
  //  public static final String SIGN_UP_URL = "http://120.24.76.152/Login/SignUpServlet";
    public static final String SIGN_UP_URL = "http://192.168.191.1/Login/SignUpServlet";
 //   public static final String CHECK_VERSION_URL = "http://120.24.76.152/Login/CheckVersionServlet";
    public static final String CHECK_VERSION_URL = "http://192.168.191.1/Login/CheckVersionServlet";

    //真正的新闻详情的URL
    public static final String TRUE_NEWS_DETAIL_URL = "http://3g.163.com/touch/article/";

    // 新闻详情
    public static final String NEWS_DETAIL = NETEAST_HOST + "nc/article/";

    // 头条TYPE
    public static final String HEADLINE_TYPE = "headline";
    // 房产TYPE
    public static final String HOUSE_TYPE = "house";
    // 其他TYPE
    public static final String OTHER_TYPE = "list";

    //    // 北京
    //    public static final String LOCAL_TYPE = "local";
    //    // 北京的Id
    //    public static final String BEIJING_ID = "5YyX5Lqs";
    public static final String[] allChannel = {"头条", "精选", "科技", "财经", "军事", "体育", "足球", "娱乐",
            "电影", "汽车", "笑话", "游戏", "时尚", "情感", "电台", "NBA", "数码", "移动", "彩票", "教育", "论坛"};

    // 头条id
    public static final String HEADLINE_ID = "T1348647909107";
    // 精选
    public static final String CHOICE_ID = "T1370583240249";
    // 房产id
    public static final String HOUSE_ID = "5YyX5Lqs";
    // 足球
    public static final String FOOTBALL_ID = "T1399700447917";
    // 娱乐
    public static final String ENTERTAINMENT_ID = "T1348648517839";
    // 体育
    public static final String SPORTS_ID = "T1348649079062";
    // 财经
    public static final String FINANCE_ID = "T1348648756099";
    // 科技
    public static final String TECH_ID = "T1348649580692";
    // 电影
    public static final String MOVIE_ID = "T1348648650048";
    // 汽车
    public static final String CAR_ID = "T1348654060988";
    // 笑话
    public static final String JOKE_ID = "T1350383429665";
    // 游戏
    public static final String GAME_ID = "T1348654151579";
    // 时尚
    public static final String FASHION_ID = "T1348650593803";
    // 情感
    public static final String EMOTION_ID = "T1348650839000";
    // 电台
    public static final String RADIO_ID = "T1379038288239";
    // nba
    public static final String NBA_ID = "T1348649145984";
    // 数码
    public static final String DIGITAL_ID = "T1348649776727";
    // 移动
    public static final String MOBILE_ID = "T1351233117091";
    // 彩票
    public static final String LOTTERY_ID = "T1356600029035";
    // 教育
    public static final String EDUCATION_ID = "T1348654225495";
    // 论坛
    public static final String FORUM_ID = "T1349837670307";
    // 旅游
    public static final String TOUR_ID = "T1348654204705";
    // 手机
    public static final String PHONE_ID = "T1348649654285";
    // 博客
    public static final String BLOG_ID = "T1349837698345";
    // 社会
    public static final String SOCIETY_ID = "T1348648037603";
    // 家居
    public static final String FURNISHING_ID = "T1348654105308";
    // 暴雪游戏
    public static final String BLIZZARD_ID = "T1397016069906";
    // 亲子
    public static final String PATERNITY_ID = "T1397116135282";
    // CBA
    public static final String CBA_ID = "T1348649475931";
    // 消息
    public static final String MSG_ID = "T1371543208049";
    // 军事
    public static final String MILITARY_ID = "T1348648141035";

    /**
     * 视频 http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html
     */
    public static final String Video = "nc/video/list/";
    public static final String VIDEO_CENTER = "/n/";
    public static final String VIDEO_END_URL = "-10.html";
    // 热点视频
    public static final String VIDEO_HOT_ID = "V9LG4B3A0";
    // 娱乐视频
    public static final String VIDEO_ENTERTAINMENT_ID = "V9LG4CHOR";
    // 搞笑视频
    public static final String VIDEO_FUN_ID = "V9LG4E6VR";
    // 精品视频
    public static final String VIDEO_CHOICE_ID = "00850FRB";

    /**
     * 天气预报url
     */
    public static final String WEATHER_HOST = "http://wthrcdn.etouch.cn/";

    /**
     * 新浪图片新闻
     */
    public static final String SINA_PHOTO_HOST = "http://gank.io/api/";

    // 精选列表
    public static final String SINA_PHOTO_CHOICE_ID = "hdpic_toutiao";
    // 趣图列表
    public static final String SINA_PHOTO_FUN_ID = "hdpic_funny";
    // 美图列表
    public static final String SINA_PHOTO_PRETTY_ID = "hdpic_pretty";
    // 故事列表
    public static final String SINA_PHOTO_STORY_ID = "hdpic_story";

    // 图片详情
    public static final String SINA_PHOTO_DETAIL_ID = "hdpic_hdpic_toutiao_4";

    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
    public static String getType(String id) {
        switch (id) {
            case HEADLINE_ID:
                return HEADLINE_TYPE;
            case HOUSE_ID:
                return HOUSE_TYPE;
            default:
                break;
        }
        return OTHER_TYPE;
    }
/*
        *//**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     *//*
        public static String getHost(int hostType) {
            String host;
            switch (hostType) {
                case HostType.NETEASE_NEWS_VIDEO:
                    host = NETEAST_HOST;
                    break;
                case HostType.GANK_GIRL_PHOTO:
                    host = SINA_PHOTO_HOST;
                    break;
                case HostType.NEWS_DETAIL_HTML_PHOTO:
                    host = "http://kaku.com/";
                    break;
                default:
                    host = "";
                    break;
            }
            return host;
        }*/


}
