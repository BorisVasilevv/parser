import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URLEncoder;
import java.io.IOException;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String query="hotdog"; //scan.next();
        String urlDecodeQuery=URLEncoder.encode(query, StandardCharsets.UTF_8);
        String queryToAvito = "https://www.avito.ru/all?q=" + urlDecodeQuery;
        Integer amountElems;
        String  userQuery;
        //https://www.avito.ru/all?q=&p=4
        ArrayList<Element> elements = new ArrayList<>();
        try {
            Document firstPage=Jsoup.connect(queryToAvito).get();
            Elements upElems = firstPage.getElementsByClass("page-title-root-cK8oN js-page-title");

            Elements resultElems = firstPage.getElementsByClass("iva-item-root-_lk9K photo-slider-slider-S15A_ iva-item-list-rfgcH iva-item-redesign-rop6P iva-item-responsive-_lbhG items-item-My3ih items-listItem-Gd1jN js-catalog-item-enum");

            elements.addAll(resultElems);

            Elements tempElems = upElems.select("div");
            amountElems = Integer.parseInt(tempElems.select("span").text());
            userQuery = tempElems.select("h1").text();
            for(int i=2;elements.stream().count()<amountElems;i++){
                Document tempPage=Jsoup.connect(queryToAvito + String.format("&p=%d", i)).get();

            }
            //System.out.println( firstPage.title());
        }
        catch (IOException e){
            System.out.println(e);
        }



    }
}