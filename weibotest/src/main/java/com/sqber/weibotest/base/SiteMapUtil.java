package com.sqber.weibotest.base;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * /时间必须是 2005-05-10T17:33:30+08:00 或者 2005-05-10，见 https://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd
 * /*
 * <xsd:enumeration value="always"/>
 * <xsd:enumeration value="hourly"/>
 * <xsd:enumeration value="daily"/>
 * <xsd:enumeration value="weekly"/>
 * <xsd:enumeration value="monthly"/>
 * <xsd:enumeration value="yearly"/>
 * <xsd:enumeration value="never"/>
 * SiteMapUtil siteMapUtil = new SiteMapUtil();
 * siteMapUtil.addSitemapUrl("http://www.sqber.com", "1.0", "2021-05-28", "daily");
 * siteMapUtil.addSitemapUrl("http://www.sqber.com", "0.9", "", "weekly");
 * siteMapUtil.addSitemapUrl("http://www.sqber.com", "0.8", "2021-05-28", "daily");
 * String content = siteMapUtil.getSiteMapContent();
 * siteMapUtil.writeTxtFile(content, "sitemap.xml", false, false);
 */
public class SiteMapUtil {

    public static final String ALWAYS = "always";
    public static final String HOURLY = "hourly";
    public static final String DAILY = "daily";
    public static final String WEEKLY = "weekly";
    public static final String MONTHLY = "monthly";
    public static final String YEARLY = "yearly";


    private StringBuilder urlBuilder = new StringBuilder();

    // 参考：https://gitee.com/leimingyun/wangmarket_plugin_sitemap/blob/master/src/main/java/com/xnx3/wangmarket/plugin/sitemap/Plugin.java
    public String getSiteMapContent() {

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<urlset xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        //网站首页
        sb.append(urlBuilder.toString());

        //增加xml的末尾闭合标签
        sb.append("</urlset>");
        //生成 sitemap.xml
        //AttachmentUtil.putStringFile(/sitemap.xml", sb.toString());
        return sb.toString();
    }


    /**
     * SiteMap生成的url项
     *
     * @param loc      url
     * @param priority 权重，如 1.00 、 0.5
     * @return url标签的xml
     */
    public void addSitemapUrl(String loc, String priority, String lastmod, String changefreq) {
        if (loc.indexOf("http:") == -1) {
            loc = "http://" + loc;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\t<url>\n");
        builder.append("\t\t<loc>" + loc + "</loc>\n");

        if (StringUtils.hasText(lastmod)) {
            builder.append("\t\t<lastmod>" + lastmod + "</lastmod>\n");
        }

        if (StringUtils.hasText(changefreq)) {
            builder.append("\t\t<changefreq>" + changefreq + "</changefreq>\n");
        }

        builder.append("\t\t<priority>" + priority + "</priority>\n");
        builder.append("\t</url>\n");

        urlBuilder.append(builder.toString());
    }

    public void writeTxtFile(String content, String filePath, boolean isUnicode, boolean append) throws Exception {
        FileOutputStream o = null;
        try {
            File fileName = new File(filePath);
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            o = new FileOutputStream(fileName, append);
            if (isUnicode) {
                o.write(content.getBytes("GBK"));
            } else {
                o.write(content.getBytes("UTF-8"));
            }
            o.close();
        } finally {
            if (o != null) {
                o.close();
            }
        }
    }
}
