import java.net.*;

public class URLdepthPair {

    String url;
    int depth;

    public URLdepthPair(String url, int depth){
        this.url = url;
        this.depth = depth;
    }



    // http://aaa.bbb.ccc.com/asdf/asdf/sadf.aspx?blah


    // aaa.bbb.ccc.com
    public String getHost() throws MalformedURLException{
        return new URL(url).getHost();
    }

    public String getPath() throws MalformedURLException{
        return new URL(url).getPath();
    }

}
