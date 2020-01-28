package com.medicalproject.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫工具类
 * @author 许鹤铭
 */
public class CrawlerUtils {

    /**
     * 获取爬虫药品信息
     * @param medicineName 药品名称
     * @return 药品信息
     */
    public static List<String> getMedicineInfo(String medicineName) {
        String url = "http://drugs.dxy.cn/search/drug.htm?keyword=" + medicineName;
        List<String> list = null;
        Document docConnect = null;
        Document docParse = null;
        try {
            docConnect = Jsoup.connect(url)
                    .header("Host", "drugs.dxy.cn")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .timeout(2000)
                    .ignoreContentType(true)
                    .get();
            Elements div = docConnect.getElementsByClass("m49 result");

            docParse = Jsoup.parse(div.html());
            Element element = docParse.select("a").first();
            String detailUrl = "http:"+element.attr("href");
            list = getContent(detailUrl);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取药品详细内容
     * @param url 详细页url
     * @return 药品详细内容
     */
    private static List<String> getContent(String url){
        List<String> list = null;
        Document docConnect = null;
        Document docParse = null;
        String str = url.split("/")[4].substring(0, 5);
        try{
            list = new ArrayList<>();
            docConnect = Jsoup.connect(url)
                    .header("Host", "drugs.dxy.cn")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .timeout(2000)
                    .ignoreContentType(true)
                    .get();
            Elements div = docConnect.getElementsByClass("m49 detail detail1");

            docParse = Jsoup.parse(div.html());
            Elements elements = docParse.select("dd");
            list.add(elements.get(0).text());
            list.add(elements.get(1).text());
            list.add(docParse.getElementById(str+"_3").text());
            list.add(docParse.getElementById(str+"_4").text());
            list.add(docParse.getElementById(str+"_14").text());
            list.add(docParse.getElementById(str+"_14_detail").nextElementSibling().nextElementSibling().text());
            list.add(docParse.getElementById(str+"_13").text());
            list.add(docParse.getElementById(str+"_6").text());
            list.add(docParse.getElementById(str+"_27_detail").nextElementSibling().nextElementSibling().text());
            list.add(docParse.getElementById(str+"_27_detail").nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().text());
            list.add(docParse.getElementById(str+"_27_detail").nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling().text());

            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
