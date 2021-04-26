package info.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class checkCSV {
    public static boolean checkValid(File file){
        ArrayList<String>cont = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp=reader.readLine())!=null){
                cont.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String[] csvContents =cont.toArray(new String[cont.size()]);
        for (int i = 1; i < csvContents.length; i++) {
            if(!(duplicateSeparator(csvContents[i])&&checkFormat(csvContents[i]))){
                return false;
            }
        }
        return true;
    }

    private static boolean duplicateSeparator(String line){
        for (int i = 0; i <line.length() ; i++) {
            if(line.charAt(i)==','&&(i+1<line.length()&&line.charAt(i+1)==',')){
                return false;
            }
        }
        return true;
    }

    private static boolean checkFormat(String line){
        String[] words = line.split(Character.toString(','));
        for (int i = 0; i < words.length ; i++) {
            boolean flag = true;
            //check:surround ""
            if(words[i].charAt(0)!='"'||words[i].charAt(words[i].length()-1)!='"'){
                return false;
            }

            String temp = words[i].substring(1,words[i].length()-1);

            //check: " format
            for (int j = 0; j <temp.length() ; j++) {
                if(temp.charAt(j)=='"'){
                    if(j+1>=temp.length()||temp.charAt(j+1)!='"'){
                        flag = false;
                        break;
                    }
                    j++;
                }
            }
            if(!flag){
                return false;
            }
        }
        return true;

    }
}
