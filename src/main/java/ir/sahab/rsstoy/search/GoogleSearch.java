package ir.sahab.rsstoy.search;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleSearch {
    public static String searchQuery(String searchQuery) throws IOException, GeneralSecurityException {
        String cx = "002845322276752338984:vxqzfa86nqc"; //Your search engine

        //Instance Customsearch
        Customsearch cs = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                .setApplicationName("MyApplication")
                .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer("AIzaSyAneW98bm6LO1VnUMKshDENzBKHLKJaQNo"))
                .build();

        //Set search parameter
        Customsearch.Cse.List list = cs.cse().list(searchQuery).setCx(cx);

        //Execute search
        Search result = list.execute();
        if (result.getItems()!=null){
            for (Result ri : result.getItems()) {
                //Get title, link, body etc. from search
                System.out.println(ri.getTitle() + ", " + ri.getLink());
            }
        }
        return result.getItems().get(0).getLink();
    }

}
