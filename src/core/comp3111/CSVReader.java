package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {
    File inputFile;
    public CSVReader(String fileName){
        inputFile = new File(fileName);
    }

    public ArrayList<String> readRow(){
        return null;
    }


    public ArrayList<String> readCol(){
        return null;
    }

    public ArrayList<String> readALL(){
        ArrayList<String> rowData = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String current = scanner.next();
                rowData.add(current);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return rowData;
    }

    public static void printData(ArrayList<String> datas){
        for(String data:datas){
            System.out.print(data+" ");
        }
    }

    public static void main(String[] args){
        CSVReader ch = new CSVReader("csvTest1.csv");
        ArrayList<String> datas = ch.readALL();
        CSVReader.printData(datas);
    }
}
