import junit.framework.TestCase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;

public class CrawlerTest extends TestCase {

    public void testCheckPageForTerms() {
        Document document = null;
        ArrayList<String> terms = new ArrayList<>();
        terms.add("Tesla");
        terms.add("Mask");
        terms.add("Gigafactory");
        terms.add("Elon Musk");
        Crawler crawler = new Crawler("https://en.wikipedia.org/wiki/Elon_Musk", terms,8,10);
        try {
            String url = "https://en.wikipedia.org/wiki/Elon_Musk";
            document = Jsoup.connect(url).get();
            String content = document.body().text();
            Link link = new Link(url, content);
            Assert.assertEquals(145, link.getHitByTerm("Tesla"));
            Assert.assertEquals(1, link.getHitByTerm("Mask"));
            Assert.assertEquals(6, link.getHitByTerm("Gigafactory"));
            Assert.assertEquals(248, link.getHitByTerm("Elon Musk"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}