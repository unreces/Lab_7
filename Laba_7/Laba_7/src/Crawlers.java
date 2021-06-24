import java.io.*;
import java.util.LinkedList;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawlers {
    public static final String URL_PREFIX = "http://";

    static LinkedList<URLdepthPair> checkedLinks = new LinkedList<>();
    static LinkedList<URLdepthPair> uncheckedLinks = new LinkedList<>();

    public static void printResult(){
        for (URLdepthPair pair : checkedLinks){
            System.out.println("Depth: " + pair.depth + " URL: " + pair.url);
        }
    }

    public static void request(PrintWriter out, URLdepthPair url) throws IOException {
        out.println("GET " + url.getPath() + " HTTP/1.1");
        out.println("Host: " + url.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }

    public static void crawl(String site, int maxDepth) throws IOException {
        uncheckedLinks.add(new URLdepthPair(site, 0));

        while (!uncheckedLinks.isEmpty()) {
            URLdepthPair currentPair = uncheckedLinks.remove(0);

            if (currentPair.depth < maxDepth) {
                Socket socket = new Socket(currentPair.getHost(), 80);
                socket.setSoTimeout(1000);
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    request(out, currentPair);
                    String line = in.readLine();
                    while (line != null) {
                        // System.out.println(line);
                        if (line.contains(URL_PREFIX)) {
                            //StringBuilder link = new StringBuilder();
//                            int index = line.indexOf(URL_PREFIX);
//
//                            while (line.charAt(index) != '"' && line.charAt(index) != ' '){
//                                link.append(line.charAt(index));
//                                index++;
//                            }
                            String url;


                            Pattern pattern = Pattern.compile("http://[\\w_-]+(\\.[\\w_-]+)([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
                            Matcher matcher = pattern.matcher(line);

                            while(matcher.find()){
                                url = line.substring(matcher.start(), matcher.end());
                                URLdepthPair newPair = new URLdepthPair(url, currentPair.depth+1);
                                if (!uncheckedLinks.contains(newPair) && !checkedLinks.contains(newPair) && !newPair.url.equals(currentPair.url)){
                                    uncheckedLinks.add(newPair);
                                }
                            }
                        }
                        line = in.readLine();
                    }
                    socket.close();
                } catch (SocketTimeoutException e) {
                    socket.close();
                }
            }
            checkedLinks.add(currentPair);
        }
        printResult();
    }


    public static void main(String[] args) {

        // http://old.code.mu/tasks/advanced/php/parsing/praktika-po-parsingu-sajtov-initial.html
        // http://www.google.ru/
        // http://www.quizful.net/post/Java-RegExp

        args = new String[]{"http://www.google.ru/", "5"};
        try {
            crawl(args[0], Integer.parseInt(args[1]));
        }  catch (NumberFormatException | IOException e) {
            System.out.println("usage: java Crawler " + args[0] + " " + args[1]);
        }
    }
}
