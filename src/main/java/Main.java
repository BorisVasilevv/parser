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
        String query=scan.nextLine();
        String urlDecodeQuery=URLEncoder.encode(query, StandardCharsets.UTF_8);
        String queryToAvito = "https://www.avito.ru/all?q=" + urlDecodeQuery;
        ArrayList<Element> resultSetProducts = new ArrayList<>();
        try {
            Integer amountElems=0;
            Integer pageCount=2;
            int i=0;
            do {
                String s =queryToAvito + String.format("&p=%d", ++i);
                System.out.println(s);
                Document page=Jsoup.connect(s).get();
                Elements tempResultElems = page.getElementsByClass("iva-item-root-_lk9K photo-slider-slider-S15A_ iva-item-list-rfgcH iva-item-redesign-rop6P iva-item-responsive-_lbhG items-item-My3ih items-listItem-Gd1jN js-catalog-item-enum");
                resultSetProducts.addAll(tempResultElems);
                if (i==1){
                    Elements elemCount = page.getElementsByClass("page-title-count-wQ7pG");
                    String maxPageNumber= page.getElementsByClass("styles-module-item-kF45w styles-module-item_size_s-Tvz95 styles-module-item_last-vIJIa styles-module-item_link-_bV2N").attr("aria-label").split(" ")[1];
                    pageCount = Integer.parseInt(maxPageNumber);
                    amountElems = Integer.parseInt(elemCount.select("span").text().replaceAll(" ",""));
                }
            }while (i<=pageCount);

            System.out.printf( "Запрос: \"%s\" выдал %d результатов\n",query, amountElems);
        }
        catch (IOException e){
            System.out.println(e);
        }
        System.out.println(resultSetProducts.size());
    }
}