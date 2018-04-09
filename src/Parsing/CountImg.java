package Parsing;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class CountImg {
    private static Integer size;

    private synchronized static void increaseSize(Integer add){
        size += add;
    }

    public static Pair<Integer, Integer> count(String url) throws IOException, InterruptedException {
        size = 0;
        Document doc = Jsoup.connect(url).get();
        HashSet<String> Images = new HashSet<>();

        Elements Imgs = doc.select("img");
        for (Element img : Imgs) {
            String address = img.absUrl("src");
            Images.add(address);
        }

        List<Thread> threadList = new LinkedList<>();
        for(String address : Images){
            Thread thread = new Thread(new Connector(address));
            thread.start();
            threadList.add(thread);
        }

        for(Thread thread : threadList)
            thread.join();

        return new Pair<Integer, Integer>(Images.size(), size);
    }

    private static class Connector implements Runnable{
        private String link;

        Connector(String link){
            this.link = link;
        }

        @Override
        public void run() {
            URLConnection conn = null;
            try {
                conn = new URL(link).openConnection();
                conn.addRequestProperty("user-agent", "");
                increaseSize(conn.getContentLength());
                conn.getInputStream().close();
            } catch (IOException e) {
                System.out.println("Parsing: Connection error");
                //e.printStackTrace();
            }
        }
    }
}
