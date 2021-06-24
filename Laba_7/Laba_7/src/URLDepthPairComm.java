import java.util.LinkedList;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDepthPairComm {
    public static final String URL_PREFIX = "http://";

    public String URL;
    public int depth;

    public URLDepthPairComm (String URL, int depth){
        this.URL=URL;
        this.depth=depth;
    }


    public String getHost() throws MalformedURLException {
        URL host = new URL(URL);
        return host.getHost();
    }
    public String getPath() throws MalformedURLException {
        URL path = new URL(URL);
        return path.getPath();
    }
    public int getDepth() {
        return depth;
    }
    public String getURL() {
        return URL;
    }

    public static boolean check(LinkedList<URLDepthPairComm> resultLink, URLDepthPairComm pair) {
        boolean isAlready = true;
        for (URLDepthPairComm c : resultLink)
            if (c.getURL().equals(pair.getURL()))
                isAlready=false;
        return isAlready;
    }
}