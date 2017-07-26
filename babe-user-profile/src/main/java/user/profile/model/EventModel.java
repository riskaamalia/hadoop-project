package user.profile.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by riskaamalia on 05/07/17.
 */
public class EventModel {

    @SerializedName("logTime")
    private String logTime;

    @SerializedName("userId")
    private String userId;

    @SerializedName("sesId")
    private String sesId;

    @SerializedName("sesBeTm")
    private long sesBeTm;

    @SerializedName("appId")
    private int appId;

    @SerializedName("appVer")
    private String appVer;

    @SerializedName("devMod")
    private String devMod;

    @SerializedName("osv")
    private String osv;

    @SerializedName("icu")
    private String icu;

    @SerializedName("newUsr")
    private int newUsr;

    @SerializedName("author")
    private String author;

    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("net")
    private String net;

    @SerializedName("eventName")
    private String eventName;

    @SerializedName("idItem")
    private long idItem;

    @SerializedName("typeItem")
    private String typeItem;

    @SerializedName("cateItem")
    private String cateItem;

    @SerializedName("pubItem")
    private String pubItem;

    @SerializedName("labelItem")
    private String [] labelItem = null;

    @SerializedName("posItem")
    private String posItem;

    @SerializedName("type")
    private String type;

    @SerializedName("loc")
    private String loc;

    @SerializedName("posId")
    private int posId;

    @SerializedName("value")
    private double value;

    @SerializedName("data")
    private String [] data = null;

    @SerializedName("entities")
    private String [] entities = null;

    @SerializedName("pubDate")
    private String pubDate;

    @SerializedName("imprId")
    private long imprId;

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSesId() {
        return sesId;
    }

    public void setSesId(String sesId) {
        this.sesId = sesId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public int getNewUsr() {
        return newUsr;
    }

    public void setNewUsr(int newUsr) {
        this.newUsr = newUsr;
    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public String getPosItem() {
        return posItem;
    }

    public void setPosItem(String posItem) {
        this.posItem = posItem;
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getImprId() {
        return imprId;
    }

    public void setImprId(long imprId) {
        this.imprId = imprId;
    }

    public String getDevMod() {
        return devMod;
    }

    public void setDevMod(String devMod) {
        this.devMod = devMod;
    }

    public String getOsv() {
        return osv;
    }

    public void setOsv(String osv) {
        this.osv = osv;
    }

    public String getIcu() {
        return icu;
    }

    public void setIcu(String icu) {
        this.icu = icu;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(String typeItem) {
        this.typeItem = typeItem;
    }

    public String getCateItem() {
        return cateItem;
    }

    public void setCateItem(String cateItem) {
        this.cateItem = cateItem;
    }

    public String getPubItem() {
        return pubItem;
    }

    public void setPubItem(String pubItem) {
        this.pubItem = pubItem;
    }

    public String [] getLabelItem() {
        return labelItem;
    }

    public void setLabelItem(String [] labelItem) {
        this.labelItem = labelItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String [] getData() {
        return data;
    }

    public void setData(String [] data) {
        this.data = data;
    }

    public String [] getEntities() {
        return entities;
    }

    public void setEntities(String [] entities) {
        this.entities = entities;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getSesBeTm() {
        return sesBeTm;
    }

    public void setSesBeTm(long sesBeTm) {
        this.sesBeTm = sesBeTm;
    }
}
