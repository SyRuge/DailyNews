package com.xcx.dailynews.bean;

import java.util.List;

/**
 * 用FastJson解析需要的Bean对象
 * Created by xcx on 2016/11/3.
 */

public class BaseDataBean {


    public BaseDataBean() {

    }

    public int replyCount;
    public String digest;
    public String title;
    public String lmodify;
    public String boardid;
    public int votecount;
    public int imgType;
    public String skipType;
    public String source;
    public String imgsrc;
    public String url;
    public String url_3w;
    /**
     * imgsrc : http://cms-bucket.nosdn.127
     * .net/27a40661459d48a5949b27e6b1cbcff020161102080528.jpeg
     */
    public List<AdsBean> ads;
    public List<ImgextraBean> imgextra;

    public static class AdsBean {

        public AdsBean() {

        }

        public String title;
        public String tag;
        public String imgsrc;
        public String subtitle;
        public String url;

        @Override
        public String toString() {
            return "AdsBean{" +
                    "title='" + title + '\'' +
                    ", tag='" + tag + '\'' +
                    ", imgsrc='" + imgsrc + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class ImgextraBean {

        public ImgextraBean() {

        }

        public String imgsrc;

        @Override
        public String toString() {
            return "ImgextraBean{" +
                    "imgsrc='" + imgsrc + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseDataBean{" +
                "replyCount=" + replyCount +
                ", digest='" + digest + '\'' +
                ", title='" + title + '\'' +
                ", lmodify='" + lmodify + '\'' +
                ", boardid='" + boardid + '\'' +
                ", votecount=" + votecount +
                ", imgType=" + imgType +
                ", skipType='" + skipType + '\'' +
                ", source='" + source + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", url='" + url + '\'' +
                ", url_3w='" + url_3w + '\'' +
                ", ads=" + ads +
                ", imgextra=" + imgextra +
                '}';
    }
}
