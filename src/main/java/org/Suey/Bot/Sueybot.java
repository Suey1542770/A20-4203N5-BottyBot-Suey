package org.Suey.Bot;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Sueybot
{
    public static void main( String[] args ){
        /**
         *verification du nombre d'argument
         */
        System.out.println("Bonjour Suey \n");

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


            /**
            * vérifie que les chemin du dossier choisi est valid
            */
            File file = new File(repo);
            if (!file.exists()){
                System.out.println("le dossier n'exist pas");
            }
            else if(!file.canWrite()){
                System.out.println("le dossier ne permet pas l'écriture");
            }
            else if(!file.canRead()){
                System.out.println("le dossier n'est pas accessible");
            }
            else{
                /**
                 * Tous les arguments ont été verifier. ont part donc le programs principal
                 */
                System.out.println("tout va bien, explorons");

                webbot(profondeur, lien, repo);
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


        String[][] listURL = new String[profondeur+1][];
        listURL[0] = new String[]{url};
        Set<String> listEmail = new HashSet<>();
        String[] UrlUtiliser = {};
        List<String> list =new ArrayList<>();
        String[][] retour = new String[2][];

        int nbpage =0;

        for (int i=0;i<profondeur;i++)
        {
            UrlUtiliser = concat(UrlUtiliser, listURL[i]);
            list = Arrays.asList(UrlUtiliser);
             retour = WebReader(listURL[i],list);
            listURL[i+1] = retour[0];
            for (String s: retour[1])
            {
                listEmail.add(s);
            }
            nbpage = nbpage + Integer.parseInt(retour[2][0]);

        }


        //convertie le et en list
        List<String> emails = Arrays.asList(listEmail.toArray(new String[listEmail.size()]));


        Collections.sort(emails,String.CASE_INSENSITIVE_ORDER);
        System.out.println("\nNombre de page visit: " + nbpage);
        System.out.println("Nombre de courriels extraits (en ordre alphabetique) : " + listEmail.size());
        for (String s:listEmail) {System.out.println("\t"+s);}

    }


    public static String[][] WebReader(String[] url, List<String> blacklist) {


        String[] urlList = new String[0];
        Set<String> emails = new HashSet<String>();
        int nbpageexplorer =0;

            for (String l: url)
            {
                String[] email = {};
                String[] linktext ={};
                try {

                    Document doc = Jsoup.connect(l).get();
                    System.out.println("Exploration de >> " + l);
                    Elements links = doc.select("a");
                    Element[] link = links.toArray(new Element[links.size()]);
                    linktext = new String[link.length];

                    Pattern cherche = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
                    Matcher trouve = cherche.matcher(doc.text());
                    while(trouve.find()){ emails.add(trouve.group()); }
                    nbpageexplorer++;

                    for (int i=0;i<link.length;i++){
                        if (blacklist.contains(link[i].attr("href")) || blacklist.contains(link[i].absUrl("href"))){

                        }
                        else if (link[i].absUrl("href").equals("")){
                            linktext[i] = link[i].attr("href");
                        }
                        else{
                        linktext[i] = link[i].absUrl("href");
                        }

                    }
                } catch (MalformedURLException e) {
                    System.err.println("URL mal formee " + l);

                } catch (IOException e) {
                    System.err.println("page inaccesssible "+l);
                }
                catch (IllegalArgumentException e) {
                    if (l != null){
                        System.err.println("URL mal formee " + l);
                    }
                }


                urlList = concat(urlList,linktext);


        }
            String[] nbpage = {Integer.toString(nbpageexplorer)};
            String[] emailList = emails.toArray(new String[emails.size()]);
            String[][] result = {urlList,emailList,nbpage};
            return result;
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
