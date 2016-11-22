package com.xcx.dailynews.bean;

import java.util.List;

/**
 * Created by xcx on 2016/11/1.
 */

public class NewsBean {


    public List<Bean> T1348649654285;

    public static class Bean {
        public int replyCount;
        public String ltitle;
        public String digest;
        public String title;
        public String lmodify;
        public String url_3w;
        public int votecount;
        public String url;
        public String source;
        public String imgsrc;
        public String ptime;

        @Override
        public String toString() {
            return "Bean{" +
                    "replyCount=" + replyCount +
                    ", ltitle='" + ltitle + '\'' +
                    ", digest='" + digest + '\'' +
                    ", title='" + title + '\'' +
                    ", lmodify='" + lmodify + '\'' +
                    ", url_3w='" + url_3w + '\'' +
                    ", votecount=" + votecount +
                    ", url='" + url + '\'' +
                    ", source='" + source + '\'' +
                    ", imgsrc='" + imgsrc + '\'' +
                    ", ptime='" + ptime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "T1348649654285=" + T1348649654285 +
                '}';
    }
}
