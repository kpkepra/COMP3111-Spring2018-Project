package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader {
    File inputFile;

    public CSVReader(String fileName){
        inputFile = new File(fileName);


    }

    public ArrayList<String> readRow(){
        ArrayList<String> row = new ArrayList<>();
        try{
            Scanner sc = new Scanner(inputFile);
            if(sc.hasNextLine()){
                String line = sc.nextLine();
                String [] rowData = line.split(",");
                row = new ArrayList<>(Arrays.asList(rowData));
            }
            else{
                System.out.print("Error: There's no more line");
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return row;
    }


    public ArrayList<String> readCol(){
        return null;
    }

    public ArrayList<String> readALL(){
        ArrayList<String> rowData = new ArrayList<>();
        try {
            Scanner sc = new Scanner(inputFile);
            sc.useDelimiter(",|\r\n");
            while (sc.hasNext()) {
                String current = sc.next();
                rowData.add(current);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return rowData;
    }


    public static void main(String[] args){
        CSVReader ch = new CSVReader("csvTest1.csv");
        ArrayList<String> datas = ch.readALL();
        ArrayList<String> rowData = ch.readRow();
        System.out.print(datas);
        System.out.print(rowData);
    }
}
