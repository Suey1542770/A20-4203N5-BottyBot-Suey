package org.Suey.Bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;


public class Sueybot
{
    public static void main( String[] args ){
        /**
         *verification du nombre d'argument
         */
        System.out.println("Bonjour Suey");

        if  (args.length==3){

            /**
            * Verification que le premier arguments est bien un nombre entier positif
            */
            int profondeur = Integer.parseInt(args[0]);
            String lien = args[1];
            String repo = args[2];


            try {
                if (profondeur < 0 ){
                    throw new NumberFormatException();
                }
            }
            catch(NumberFormatException e) {
                System.err.println("le premier argument doit etre un chiffre entier positif");
            }


            /**
            * Verification que le deuxième arguments est un lien Url valid et qu'une connection est possible
            */

            try {
                URL url = new URL(lien);
                URLConnection conn = url.openConnection();
                conn.connect();
            } catch (MalformedURLException e) {
                System.err.println("L'URL donner dans le deuxième argument n'a pas une forme valid");
            } catch (IOException e) {
                System.err.println("La connection au lien donner dans le deuxième argument na pas pu être établie");
            }



            File file = new File(repo);
            if (file.exists()){
                /**
                 * Tous les arguments ont été verifier. ont part donc le programs principal
                 */
                System.out.println("tout va bien, explorons");

                webbot(profondeur, lien, repo);
            }
            else{
                System.out.println("le dossier n'exist pas");
            }

        }
        else{
            System.err.println("il n'y a pas le bon nombre d'arguments");
                System.out.println("Se programme prend trois argument dans l'ordre qui suit: \n" +
                        "   - Um chiffre entier positif qui correspondant a la profonder a explorer \n" +
                        "   - Une chaine de character qui correspondant a un URL d'un site web valid \n" +
                        "   - une chaine de charateur qui correspond au chemin du répertoire où écrire les copies locales des fichiers explorés");
    }


    }

    public  static void webbot(int profondeur,String url, String repo)
    {
        System.out.println("Exploration de >> " + url);

        String[][] listURL = new String[profondeur+1][];
        listURL[0] = new String[]{url};
        WebReader(listURL[0]);
        for (int i=1;i<profondeur;i++)
        {

        for (String link: listURL[i])
        {

        }

}
        

    }

    public static String[] WebReader(String[] url) {
        try {
            String[] newlist= new String[0];
            for (String link: url)
            {
                Document doc = Jsoup.connect(link).get();



            }


            for (int i = 0; i < links.size(); i++) {
                newList[i] = link[i].absUrl("href");

            }
            return newList;
        } catch (MalformedURLException e) {
            System.err.println("L'URL donner dans le deuxième argument n'a pas une forme valid");
            return new String[0];
        } catch (IOException e) {
            System.err.println("error pas pu se connecter");
            return new String[0];
        }

    }

    //copy deux table de String forme une seul table avec les deux ancients
    public static String[] concat(String[] un, String[] deux)
    {

        String[] total = new String[un.length+deux.length];
        System.arraycopy(un, 0, total, 0, un.length);
        System.arraycopy(deux, 0, total, un.length, deux.length);

        return total;
    }
}
