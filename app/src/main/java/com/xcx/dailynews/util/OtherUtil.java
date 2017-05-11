package com.xcx.dailynews.util;

import com.xcx.dailynews.bean.CollectBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xcx on 2016/11/14.
 */

public class OtherUtil {

    /**
     * 拼接json
     */
    public static String getJson(CollectBean bean){
        StringBuilder sb = new StringBuilder();

        String url = bean.getUrl();
        sb.append("{\"url\":\"");
        sb.append(url);
        sb.append("\",");

        String channelid = bean.getChannelId();
        sb.append("\"channelid\":\"");
        sb.append(channelid);
        sb.append("\",");

        int position = bean.getPosition();
        sb.append("\"position\":\"");
        sb.append(position);
        sb.append("\",");

        String source = bean.getSource();
        sb.append("\"source\":\"");
        sb.append(source);
        sb.append("\",");

        String title =  bean.getTitle();
        sb.append("\"title\":\"");
        sb.append(title);
        sb.append("\",");

        String digest = bean.getDigest();
        sb.append("\"digest\":\"");
        sb.append(digest);
        sb.append("\",");

        String lmodify =  bean.getLmodify();
        sb.append("\"lmodify\":\"");
        sb.append(lmodify);
        sb.append("\"}");

        return sb.toString();
    }

    /**
     * 将URL转换成key
     */
    private static String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     * 将Url的字节数组转换成哈希字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
