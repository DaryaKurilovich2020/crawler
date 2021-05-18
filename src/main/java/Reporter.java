import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Reporter {
    private Crawler crawler;

    public Reporter(Crawler crawler) {
        this.crawler = crawler;
    }

    public void makeAllStatReport() throws IOException {
        FileWriter csvWriter = new FileWriter("allStat.csv");
        csvWriter.append("#link");
        for (String term : crawler.getTerms()) {
            csvWriter.append(",");
            csvWriter.append(term);
        }
        csvWriter.append("\n");
        for (Link link : crawler.getLinks().keySet()) {
            csvWriter.append(link.getUrl());
            for (String term : crawler.getTerms()) {
                csvWriter.append(",");
                csvWriter.append(link.getHitByTerm(term).toString());
            }
            csvWriter.append("\n");
        }
        csvWriter.close();
    }

    public void makeTop10Report() throws IOException {
        FileWriter csvWriter = new FileWriter("top10Stat.csv");
        csvWriter.append("#link");
        for (String term : crawler.getTerms()) {
            csvWriter.append(",");
            csvWriter.append(term);
        }
        csvWriter.append(",");
        csvWriter.append("totalHits");
        csvWriter.append("\n");

        LinkedHashMap sortedLinks = crawler.getLinks().entrySet().stream()
                .sorted(Map.Entry.<Link, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        Set keySet = sortedLinks.entrySet();
        Iterator iterator = keySet.iterator();
        int i = 0;
        while (iterator.hasNext() && i < 10) {
            Map.Entry item = (Map.Entry) iterator.next();
            Link link = (Link) item.getKey();
            csvWriter.append(link.getUrl());
            for (String term : crawler.getTerms()) {
                csvWriter.append(",");
                csvWriter.append(link.getHitByTerm(term).toString());
            }
            csvWriter.append(",");
            csvWriter.append(link.getTotalHits() + "");
            if (i != 9) {
                csvWriter.append("\n");
            }
            i++;
        }
        csvWriter.close();
    }
}