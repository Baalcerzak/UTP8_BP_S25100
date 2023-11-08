package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelData {

    private List<String> fileNames;

    private List<String> offersDescriptions = new ArrayList<>();

    private File dataDir;

    public TravelData(File dataDir) {
        this.dataDir = dataDir;
    }

    public List<String> getOffersDescriptionsList(String locale, String dateFormat) {

        String [] tab = locale.split("_");
        Locale locale2 = Locale.forLanguageTag(tab[0]+"-"+tab[1]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);


        List<String >offersDescriptions2 = new ArrayList<>();
        listFilesForFolder(dataDir);
        for(String file : fileNames){
            addData(file, offersDescriptions2, simpleDateFormat, locale2);
        }
        offersDescriptions.addAll(offersDescriptions2);

        return offersDescriptions2;

    }

    private void addData(String name, List<String> offersDescriptions2, SimpleDateFormat simpleDateFormat, Locale locale){
        try {
            File myObj = new File("./"+dataDir.getName()+"/"+name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String [] tab = data.split("\t");

                //ResourceBundle bundle1 = ResourceBundle.getBundle(tab[1], locale);
                ResourceBundle bundle2 = ResourceBundle.getBundle("resources.bundle", locale);
                Date dataF2 = simpleDateFormat.parse(tab[2]);
                Date dataT2 = simpleDateFormat.parse(tab[3]);
                String dataF = simpleDateFormat.format(dataF2);
                String dataT = simpleDateFormat.format(dataT2);


                byte[] bytes = tab[1].getBytes(StandardCharsets.UTF_16);
                String utf8EncodedString = new String(bytes, StandardCharsets.UTF_16);
                String []tab2 = utf8EncodedString.split(" ");
                String res="";
                if(tab[1].equals("Włochy")){
                       res =    bundle2.getString("Wlochy");
                }else
                    res = bundle2.getString(tab2[0]);

                if(res.equals("W?ochy"))
                    res = "Włochy";
                data = tab[0] +"\t" + res +"\t" + dataF+"\t" + dataT+"\t" + bundle2.getString(tab[4]) +"\t" + tab[5] +"\t" + tab[6];
                offersDescriptions2.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void listFilesForFolder(final File folder) {
        fileNames = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                fileNames.add(fileEntry.getName());
            }
        }
    }

    public List<String> getOffersDescriptions() {
        return offersDescriptions;
    }
}
