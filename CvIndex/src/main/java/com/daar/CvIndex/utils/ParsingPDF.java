package com.daar.CvIndex.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.daar.CvIndex.models.CV;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class ParsingPDF {
    private static int cpt=0;

    /**
     * Main reservé aux test de la classe
     * @param args
     */
    public static void main(String[] args) {

        String cv = "CV_Ruiwen.pdf";
        File file = new File ("src/main/java/com/daar/CvIndex/utils/ressources/"+cv);
        PDDocument doc;

        try {

            doc = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            if(text.trim().equals("")) {
                System.out.println("VIDE");
            }
            String[] l = text.split("\n");
            doc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static CV parse(String name, String FirstName, String mail, String tel,File file) throws IOException{
        PDDocument doc;
        CV moncv;

        doc = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(doc);
        if(text.trim().equals("")) {
            throw new IOException("Format non valide");

        }
        String[] l = text.split("\n");

        ArrayList<String> competences= getCompetences(l);
        ArrayList<String> allkeyword = getAllKeyWord(l);

        moncv= new CV(String.valueOf(cpt++),
                FirstName,
                name,
                getAge(l),
                mail,
                tel,
                competences,
                allkeyword);

        doc.close();
        return moncv;
    }

    public static CV parseDocx(String name, String FirstName, String mail, String tel,File file) throws IOException{
        CV moncv=null;
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String text = extractor.getText();
            if(text.trim().equals("")) {
                throw new IOException("Format non valide");
            }

            String[] l = text.split("\\s");
            Arrays.stream(l).forEach(System.out::println);
            ArrayList<String> competences= getCompetences(l);
            ArrayList<String> allkeyword = getCompetences(l);

            moncv= new CV(String.valueOf(cpt++),
                    FirstName,
                    name,
                    getAge(l),
                    mail,
                    tel,
                    competences,
                    allkeyword);

            fis.close();

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return moncv;


    }

    private static ArrayList<String> getAllKeyWord(String [] lignes){
        ArrayList<String> res= new ArrayList<>();

        // La recherche dans le pdf
        for(String ligne : lignes){
            for(String mot : ligne.split(" |/|-|\\(|\\)|,")){

                try{
                    if(mot.toLowerCase(Locale.ENGLISH).equals("c#".toLowerCase(Locale.ENGLISH)))
                        res.add("csharp");
                    else
                    if(mot.toLowerCase(Locale.ENGLISH).equals("c++".toLowerCase(Locale.ENGLISH)))
                        res.add("cpp");
                    else
                    if(!mot.toLowerCase(Locale.ENGLISH).matches("| |à|de|je|un|et|une|en|d'un|d'une|le|la|avec|:|\\|"))
                        res.add(mot.toLowerCase(Locale.ENGLISH));
                }catch (IllegalArgumentException e){
                    continue;
                }
            }
        }
        return (ArrayList) res.stream().distinct().collect(Collectors.toList());
    }


    private static ArrayList<String> getCompetences(String [] lignes) throws FileNotFoundException {
        HashSet<String> allCompetences= CompetenceFileToHashSet();
        ArrayList<String> competences= new ArrayList<>();
        for(String ligne : lignes){
            for(String mot : ligne.split(" |/|-|\\(|\\)|,")){

                try{
                    if(allCompetences.contains(mot.toLowerCase(Locale.ENGLISH))){
                        if(mot.toLowerCase(Locale.ENGLISH).equals("c#".toLowerCase(Locale.ENGLISH)))
                            competences.add("csharp");
                        else
                        if(mot.toLowerCase(Locale.ENGLISH).equals("c++".toLowerCase(Locale.ENGLISH)))
                            competences.add("cpp");
                        else
                            competences.add(mot.toLowerCase(Locale.ENGLISH));
                    }
                }catch (IllegalArgumentException e){
                    continue;
                }
            }
        }
        return (ArrayList) competences.stream().distinct().collect(Collectors.toList());
    }

    private static int getAge(String [] lignes){
        Pattern p= Pattern.compile("([0-9]{2}) *ans");
        for(String ligne : lignes){
            Matcher matcher = p.matcher(ligne);
            if(matcher.find())
                return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }


    private static HashSet<String> CompetenceFileToHashSet() throws FileNotFoundException {
        File file = new File("src/main/java/com/daar/CvIndex/utils/Competences");
        HashSet<String> res= new HashSet<>();

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            res.add(line.toLowerCase(Locale.ENGLISH));
        }

        return res;
    }

}
