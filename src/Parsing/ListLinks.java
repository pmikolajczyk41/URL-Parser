package Parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ListLinks {
    public static List<String> list(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements links = doc.select("a[href]");
        List<String> stringList = new LinkedList<>();

        for( Element link : links ){
            String next = link.attr("abs:href");
            if(next.equals("")) continue;
            stringList.add(next);
        }

        return stringList;
    }

}
