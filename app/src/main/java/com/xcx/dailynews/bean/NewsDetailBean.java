package com.xcx.dailynews.bean;

import java.util.List;

/**
 * Created by xcx on 2017/4/11.
 */

public class NewsDetailBean {


    /**
     * newsdetail : {"body":"px","img":[{"ref":"<!--IMG#0-->","pixel":"800*576","alt":"",
     * "src":"http://cms-bucket.nosdn.127.net/catchpic/7/74/74d12baca5fd4123a1174adfaaaf18c6.jpg
     * "}],"docid":"CHOQA88U0001899N","title":"林郑月娥：感谢中央信任支持 将充分落实宪制责任","source":"海外网",
     * "ptime":"2017-04-11 17:36:16"}
     */

    public NewsDetail newsdetail;

    public static class NewsDetail {
        /**
         * body : px
         * img : [{"ref":"<!--IMG#0-->","pixel":"800*576","alt":"","src":"http://cms-bucket
         * .nosdn.127.net/catchpic/7/74/74d12baca5fd4123a1174adfaaaf18c6.jpg"}]
         * docid : CHOQA88U0001899N
         * title : 林郑月娥：感谢中央信任支持 将充分落实宪制责任
         * source : 海外网
         * ptime : 2017-04-11 17:36:16
         */

        public String body;
        public String docid;
        public String title;
        public String source;
        public String ptime;
        public List<ImgBean> img;
        public String html;

        public static class ImgBean {
            /**
             * ref : <!--IMG#0-->
             * pixel : 800*576
             * alt :
             * src : http://cms-bucket.nosdn.127.net/catchpic/7/74/74d12baca5fd4123a1174adfaaaf18c6.jpg
             */

            public String ref;
            public String pixel;
            public String alt;
            public String src;

            @Override
            public String toString() {
                return "ImgBean{" +
                        "ref='" + ref + '\'' +
                        ", pixel='" + pixel + '\'' +
                        ", alt='" + alt + '\'' +
                        ", src='" + src + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "NewsDetail{" +
                    "body='" + body + '\'' +
                    ", docid='" + docid + '\'' +
                    ", title='" + title + '\'' +
                    ", source='" + source + '\'' +
                    ", ptime='" + ptime + '\'' +
                    ", img=" + img +
                    ", html=" + html +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "newsdetail=" + newsdetail +
                '}';
    }
}
