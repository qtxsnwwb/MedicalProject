package com.medicalproject.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;

/**
 * @ClassName：Crawler
 * @Description： 爬虫工具类
 * @author：许鹤铭
 */
public class Crawler {


    public static String getFinalUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .header("Host", "drugs.dxy.cn")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .timeout(5000)
                    .ignoreContentType(true)
                    .get();
            Elements div = doc.getElementsByClass("m49 result");

            Document doc1 = Jsoup.parse(div.html());
            Element div1 = doc1.select("a").first();
            String finalUrl = "http:" + div1.attr("href");
            return finalUrl;
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
            return null;
        }


    }

    public static String getContent(String url2) {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(url2)
                    .get();
            Elements div = doc.getElementsByClass("m49 detail detail1");
            return div.text();
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
            return null;
        }


    }

    public static String getFirstUrl(String name) {
        String nameStr = null;
        try {
            nameStr = new String(URLEncoder.encode(name, "utf-8").getBytes());
        } catch (UnsupportedEncodingException e) {
            System.out.println("不支持中文格式转换");
            e.printStackTrace();
        }
        String url = "http://drugs.dxy.cn/search/drug.htm?keyword=" + nameStr;
        return url;
    }

    public static String crawlerWork(String name) {
        String url1 = getFirstUrl(name);
        String url2 = getFinalUrl(url1);
        String content = getContent(url2);
        return content;
    }

//
//    /**
//     * 测试功能的主函数
//     * @param args
//     */
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        // 爬取的网址
//        long start = System.currentTimeMillis(); // 记录起始时间
//        System.out.println(crawlerWork("阿莫西林"));
//        long end = System.currentTimeMillis();       // 记录结束时间
//        System.out.println(end-start);              // 相减得出运行时间
//    }
}