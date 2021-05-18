import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link {
    private final String url;
    private LinkedHashMap<String, Integer> terms;
    private String content;
    private final ArrayList<String> termsToSearch;
    private int totalHits;
    private int depth;

    public String getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public Link(String url, int depth) {
        this.url = url;
        this.depth = depth;
        termsToSearch = Crawler.terms;
    }

    public Link(String url, String content) {
        this.url = url;
        this.content = content;
        termsToSearch = Crawler.terms;
    }

    private void findTerms() {
        terms = new LinkedHashMap<>();
        for (String toSearch : termsToSearch) {
            terms.put(toSearch, 0);
        }
        int counter = 0;
        for (String term : termsToSearch) {
            Pattern p = Pattern.compile(term);
            Matcher m = p.matcher(content);
            String termLowerCase = term.toLowerCase();
            Pattern p2 = Pattern.compile(termLowerCase);
            Matcher m2 = p2.matcher(content);
            while (m.find() || m2.find()) {
                counter++;
            }
            terms.put(term, counter);
            counter = 0;
        }
        for (Integer value : terms.values()) {
            totalHits += value;
        }
    }

    public int getTotalHits() {
        if (terms == null) {
            findTerms();
        }
        return totalHits;
    }

    public int getHitByTerm(String term) {
        if (terms == null) {
            findTerms();
        }
        return terms.get(term);
    }
}
