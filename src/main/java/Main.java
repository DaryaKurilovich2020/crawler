import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> terms = new ArrayList<>();
        terms.add("Tesla");
        terms.add("Mask");
        terms.add("Gigafactory");
        terms.add("Elon Musk");
        Crawler crawler = new Crawler( "https://en.wikipedia.org/wiki/Elon_Musk",terms,8,100);
        crawler.crawlPage();
        Reporter reporter = new Reporter(crawler);
        try {
            reporter.makeTop10Report();
            reporter.makeAllStatReport();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
