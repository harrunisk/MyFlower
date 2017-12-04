package com.example.harun.myflower.adapters;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mustafademir on 25.06.2016.
 */
public class PullAndParseXML {

    private String TAG = getClass().getSimpleName();

    // RSS TEN ALACAĞIMIZ BİLGİLERİ TUTACAĞIMIZ DEĞİŞKENLER
    private String title;
    private String link;
    private String description;
    private String imageUrl;
    private String pubDate;
    private List<String> category = new ArrayList<String>();
    private String guid;

    private int READ_TIME_OUT = 12000;
    private int CONNECT_TIME_OUT = 15000;
    private String feedUrl = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    // YAZILARA/POSTLARA AİT DATALARI NESNELER HALİNDE TUTACAĞIMIZ LİSTEMİZ
    private ArrayList<post_model> postList = new ArrayList<post_model>();

    // CONSTRUCTOR
    public PullAndParseXML(String url) {
        this.feedUrl = url;
    }

    // HER BİR YAZIYA AİT BİLGİLERİ BAŞKA SINIFLARDAN ÇAĞIRIP ALMAMIZI SAĞLAYACAK METODLAR
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public List<String> getCategory() {
        return category;
    }

    public String getGuid() {
        return guid;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public ArrayList<post_model> getPostList() {
        return postList;
    }

    /**
     * XML İ AYRIŞTIRIP BİLGİLERİ KAYIT EDEN METOD
     *
     * @param xmlPullParser
     */
    public void parseXML(XmlPullParser xmlPullParser) {
        int event;
        String text = null;
        try {

            post_model postItem = new post_model();
            event = xmlPullParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = xmlPullParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (name.equalsIgnoreCase("item")) {
                            postItem = new post_model();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = xmlPullParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case "item":
                                postList.add(postItem);
                                break;
                            case "title":
                                title = text;
                                postItem.setTitle(text);
                                break;
                            case "link":
                                link = text;
                                postItem.setLink(text);
                                break;
                            case "description":
                                description = text;
                                postItem.setDescription(text);
                               // postItem.setImageUrl("http://webrazzi.com/wp-content/uploads/2016/06/Hover-kamera-318x175.png");
                                ArrayList<String> imageUrls = getImageUrls(text);
                                if (imageUrls.size() > 0) {
                                    imageUrl = imageUrls.get(0);
                                    postItem.setImageUrl(imageUrls.get(0));
                                } else {
                                    imageUrl = "";
                                    postItem.setImageUrl("https://www.sariyerhaberler.com/wp-content/uploads/2017/10/gulhanede-otel-iangini.png");
                                }
                                break;
                            case "pubDate":
                                pubDate = text;
                                postItem.setPubDate(text);
                                break;
                            case "category":
                                category.add(text);
                                postItem.addCategoryItem(text);
                                break;
                            case "guid":
                                guid = text;
                                postItem.setGuid(text);
                                break;
                            default:
                                break;
                        }
                        break;
                }
                event = xmlPullParser.next();
            }

            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * GÖRSEL LİNKİNİ BULAN METODUMUZ
     *
     * @param text
     * @return
     */
    private ArrayList getImageUrls(String text) {

        ArrayList links = new ArrayList();
        String patternString = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String urlStr = matcher.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            if (urlStr.endsWith(".jpg") || urlStr.endsWith(".png") || urlStr.endsWith(".gif"))
                links.add(urlStr);
        }
        if (links.size() > 0)
            Log.i(TAG, "image url : > " + links.get(0).toString());
        return links;
    }

    /**
     * XML İ İNDİREN METOD
     */
    public void downloadXML() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(feedUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setReadTimeout(READ_TIME_OUT);
                    httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);

                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser xmlPullParser = xmlFactoryObject.newPullParser();

                    xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    xmlPullParser.setInput(inputStream, null);

                    parseXML(xmlPullParser);
                    inputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}
