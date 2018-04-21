package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVWriter{
    PrintWriter pw;
    StringBuilder sb;
    public CSVWriter(String fileName) {
        try{
            pw = new PrintWriter(new File(fileName));
            sb = new StringBuilder();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void writeArray(ArrayList<String> words,int colNum){
        int count = 0;
        for(String word:words){
            sb.append(word);
            sb.append(",");
            count++;
            //current row is full change to next row
            if(count == colNum){
                //delete the last, and change to new row
                sb.deleteCharAt(sb.length()-1);
                sb.append("\r");
                count = 0;
            }
        }

        pw.print(sb.toString());
    }
    public void close(){
        pw.close();
    }
}
