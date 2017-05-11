package com.xcx.dailynews.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xcx on 2017/4/11.
 */
@Entity
public class CollectBean {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "url")
    private String url;
    @Property(nameInDb = "channelid")
    private String channelId;
    @Property(nameInDb = "position")
    private int Position;
    @Property(nameInDb = "source")
    private String source;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "digest")
    private String digest;
    @Property(nameInDb = "lmodify")
    private String lmodify;
    @Generated(hash = 1144066903)
    public CollectBean(Long id, String url, String channelId, int Position,
            String source, String title, String digest, String lmodify) {
        this.id = id;
        this.url = url;
        this.channelId = channelId;
        this.Position = Position;
        this.source = source;
        this.title = title;
        this.digest = digest;
        this.lmodify = lmodify;
    }
    @Generated(hash = 420494524)
    public CollectBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public int getPosition() {
        return this.Position;
    }
    public void setPosition(int Position) {
        this.Position = Position;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDigest() {
        return this.digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getLmodify() {
        return this.lmodify;
    }
    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }


}
