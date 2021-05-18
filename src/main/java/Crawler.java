import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Crawler {
    private static final int MAX_DEPTH = 8;
    private static final int MAX_VISITED_PAGES = 100;
    private final String seed;
    private HashSet<String> visitedLinks;
    private HashMap<Link, Integer> links;
    public static ArrayList<String> terms;

    public Crawler(String seed, ArrayList<String> termsList) {
        visitedLinks = new HashSet<>();
        links = new HashMap<>();
        terms = termsList;
        this.seed = seed;
    }

    public void checkPageForTerms(String url, String content, ArrayList<String> terms) {
        Link link = new Link(url, content);
        links.put(link, link.getTotalHits());
    }

    public void crawlPage() {
        String url=seed;
        Queue<Link> queue = new ArrayDeque<>();
        if (visitedLinks.contains(url)) {
            return;
        }
        queue.add(new Link(url, 0));
        while (!queue.isEmpty()) {
            Link current = queue.poll();
            if (current.getDepth() <= MAX_DEPTH && visitedLinks.size() < MAX_VISITED_PAGES) {
                visitedLinks.add(current.getUrl());
                try {
                    Document document = Jsoup.connect(current.getUrl()).ignoreHttpErrors(true).get();
                    String content = document.body().text();
                    checkPageForTerms(current.getUrl(), content, terms);
                    Elements linksOnPage = document.select("a[href]");
                    int depth = current.getDepth() + 1;
                    if (depth < MAX_DEPTH) {
                        for (Element page : linksOnPage) {
                            queue.add(new Link(page.attr("abs:href"), depth));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HashMap<Link, Integer> getLinks() {
        return links;
    }

    public ArrayList<String> getTerms() {
        return terms;
    }

}
