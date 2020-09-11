package org.Suey.Bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Sueybot
{
    public static void main( String[] args )
    {
        try{

            Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
            System.out.println(doc);
        }
        catch(IOException e){
            System.err.println("error pas pu se connecter");    }


    }
}
