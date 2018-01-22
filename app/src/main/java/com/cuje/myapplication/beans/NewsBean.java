package com.cuje.myapplication.beans;

/**
 * Created by CUJE on 2017/4/10.
 */

public class NewsBean {
    /**
     * pubDate : 2017-02-27 20:19:52
     * havePic : true
     * title : 悬疑爱情片《亲亲姐妹劫》在常熟开机 暑期上映
     * channelName : 电影最新
     * imageurls : [{"height":0,"width":0,"url":"http://img1.gtimg.com/ent/pics/hv1/158/141/2189/142375838.jpg"}]
     * desc : 腾讯娱乐讯悬疑爱情电影《亲亲姐妹劫》日前在苏州常熟开机，据悉，电影全程将在常熟取景，定于今年暑期档上映。在其演员阵容方面，不仅有刘俐儿、泺焱、林豪华、李北岳等年轻演员挑大梁，电视剧《潜伏》中戴笠的扮演者姚刚也将加盟该片。据了解，该片由内地著名制片人、导演龚桦担任总制片，龚桦是土生土长的常熟人，电影
     * source : 电影新闻
     * channelId : 5572a10ab3cdc86cf39001ec
     * link : http://ent.qq.com/a/20170227/062816.htm
     * html : <p><img src='http://img1.gtimg.com/ent/pics/hv1/158/141/2189/142375838.jpg' /></p><p>腾讯娱乐讯 悬疑爱情</p><p>《亲亲姐妹劫》日前在苏州常熟开机，据悉，电影全程将在常熟取景，定于今年暑期档上映。在其演员阵容方面，不仅有刘俐儿、泺焱、林豪华、李北岳等年轻演员挑大梁，</p><p>据了解，该片由内地著名制片人、导演龚桦担任总制片，龚桦是土生土长的常熟人，电影《亲亲姐妹劫》就龚桦是为常熟量身定制的。龚桦，北京电影学院制片专业毕业，大学本科学历，获学士学位，现为制片人、导演、中国电影家协会会员。早期，他在珠江电影制片厂从事制片及导演工作，曾在《叶剑英》、《游子吟》、《第一枪》、《生死边缘》、《出航的日子》、《康南海传奇》、《蓝雾之谜》、《护士长日记》、</p><p>近年来，龚桦创办了广州华玺文化传播有限公司，独立制作了很多电影、电视剧，在各卫视和地方台热播，深受广大观众喜爱，包括四十集电视连续剧《天玺人生》、二十五集电视连续剧《对手》、百集电视栏目剧《夜倾情》、百集系列</p><p>《家园》、三十集电视连续剧《水乡寻梦》、院线电影《在澳门说我愿意》等。</p><p>谈及《亲亲姐妹劫》来常熟拍摄的初衷，制片人龚桦表示：“常熟地处江南水乡，素有‘江南福地’的美誉，选择来常熟拍摄，一方面是方便采景，因为片中我们设置了很多优美的场景，二是因为常熟是我的家乡，我希望能在电影中能尽量多的展现常熟，也为家乡的旅游宣传做一点贡献。”</p><p>“作为一个常熟人，很多年前就有这样的愿望，想要回常熟拍电影，很高兴终于有了这样的机会。”龚桦透露，“常熟是我的家乡，我对常熟也比较了解，在接下来的电影拍摄中一定会非常顺手，比如哪里有什么景可以拍，可以写个什么剧情配合，可以说这部电影是为常熟量身定制的。”</p><p>龚桦表示，电影将于今年暑期档登陆全国院线，还将在央视电影频道、各大视频网站与观众见面。对这部电影，龚桦也是充满信心，“因为是在常熟投拍的第一部电影，我们希望能一炮打响，所以选择了较为符合市场的纯商业片。青春悬疑这样的类型电影有一定的粉丝群，对票房很有信心。”据龚桦透露，目前已经在苏州当地成立了影视公司，“第二部电影是一个喜剧，已经在筹备当中，希望今后能挑战各种类型的电影，全方位的展现常熟。”</p>
     */

    private String pubDate;
    private boolean havePic;
    private String title;
    private String channelName;
    private String desc;
    private String source;
    private String channelId;
    private String link;
    private String html;
    private String imageurls;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(String havePic) {
        if (havePic.equals("true")){
            this.havePic = true;
        }else {
            this.havePic = false;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String  getImageurls() {return imageurls;}

    public void setImageurls(String imageurls) {
        this.imageurls = imageurls;
    }

}