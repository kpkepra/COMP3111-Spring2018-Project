package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {
    File inputFile;
    Scanner sc;

    public CSVReader(String fileName){
        inputFile = new File(fileName);

        try{
            sc = new Scanner(inputFile);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        finally {
            sc.close();
        }
    }

    public ArrayList<String> readRow(){
        if(sc.hasNextLine()){
            String line = sc.nextLine();
            String [] rowData = line.split(",");
            return new ArrayList(Arrays.asList(rowData));
        }
        else{
            System.out.print("Error: There's no more line");
            return null;
        }

    }


    public ArrayList<String> readCol(){
        return null;
    }

    public ArrayList<String> readALL(){
        ArrayList<String> rowData = new ArrayList<>();
        sc.useDelimiter(",|\r\n");
        while (sc.hasNext()) {
            String current = sc.next();
            rowData.add(current);
        }
        return rowData;
    }


    public static void main(String[] args){
        CSVReader ch = new CSVReader("csvTest1.csv");
        ArrayList<String> datas = ch.readALL();
        System.out.print(datas);
    }
}
