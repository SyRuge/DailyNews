package com.xcx.dailynews.bean;

import java.util.List;

/**
 * Created by xcx on 2016/11/2.
 */

public class TopicBean {


    public List<TopicDetailBean> T1348647909107;

    public static class TopicDetailBean {
        public int replyCount;
        public String digest;
        public String title;
        public String lmodify;
        public String boardid;
        public int votecount;
        public String skipType;
        public String source;
        public String imgsrc;
        public String ptime;
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/27a40661459d48a5949b27e6b1cbcff020161102080528.jpeg
         */

        public List<ImgextraBean> imgextra;

        public static class ImgextraBean {
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
            return "TopicDetailBean{" +
                    "replyCount=" + replyCount +
                    ", digest='" + digest + '\'' +
                    ", title='" + title + '\'' +
                    ", lmodify='" + lmodify + '\'' +
                    ", boardid='" + boardid + '\'' +
                    ", votecount=" + votecount +
                    ", skipType='" + skipType + '\'' +
                    ", source='" + source + '\'' +
                    ", imgsrc='" + imgsrc + '\'' +
                    ", ptime='" + ptime + '\'' +
                    ", imgextra=" + imgextra +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TopicBean{" +
                "T1348647909107=" + T1348647909107 +
                '}';
    }
}
