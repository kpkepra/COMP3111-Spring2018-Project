package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CSVReader {
    File inputFile;
    ArrayList<String> data;
    ArrayList<String> fields;
    int numCol;
    int numRow;

    public CSVReader(String fileName){
        inputFile = new File(fileName);
        data = new ArrayList<>();
        fields = new ArrayList<>();
    }

    public int getNumCol() {
        return numCol;
    }

    public ArrayList<String> readRow(){
        ArrayList<String> row = new ArrayList<>();
        try{
            Scanner sc = new Scanner(inputFile);
            if(sc.hasNextLine()){
                String line = sc.nextLine();
                String [] rowData = line.split(",");
                row = new ArrayList<>(Arrays.asList(rowData));
                if(numCol ==0) numCol = row.size();
                else if(numCol != row.size()){
                    System.out.println("Error: Input File does not have " +
                            "same col number for each row");
                }
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


    public void readALL(){
        try {
            Scanner sc = new Scanner(inputFile);
            sc.useDelimiter(",|\r\n");
            //read the first line in file and construct the data fields
            fields = readRow();
            while (sc.hasNext()) {
                String current = sc.next();
                data.add(current);
            }

            for(int i = 0; i < data.size();i++) {
                if (data.get(i).equals("")) {
                    System.out.println("NumCol" + numCol);
                    int colNum = i % numCol;
                    System.out.println("colNum" + colNum);
                    System.out.println("dataSize" + data.size());
                    // this will fail if there's only fields without data
                    missingDataHandler(isNumericRow(data.get(colNum + numCol)), i);
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private  static boolean isNumericRow(String data){
        try
        {
            Double.parseDouble(data);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private void missingDataHandler(boolean isNumeric,int index){
        if(isNumeric){
            System.out.println("Encounter empty data, " +
                    "please enter [1-3] to choose a fill-in method");
            System.out.println("[1]: Fill with 0 \n" +
                    "[2]: Fill with mean \n" +
                    "[3]: Fill with median");
            Scanner scSysIn = new Scanner(System.in);
            int command = scSysIn.nextInt();
            switch (command){
                case 1:
                    System.out.println("replace with 0");
                    data.set(index,"0");
                    break;
                case 2:
                    System.out.println("replace with mean");
                    data.set(index,String.valueOf(getMean(getCol(index))));
                    break;
                case 3:
                    System.out.println("replace with median");
                    data.set(index,String.valueOf(getMedian(getCol(index))));
                    break;
                default:
                    System.out.println("Invalid Input");
            }

        }
    }

    private ArrayList<String> getCol(int index){
       ArrayList<String> curCol = new ArrayList<>();
       for(int i = numCol; i < data.size();i++){
           if(i%numCol == index %numCol && i != index){
               curCol.add(data.get(i));
           }
       }
       return curCol;
    }

    private static double getMean(ArrayList<String> col){
        double sum = 0.0;
        for(int i = 0; i < col.size(); i++){
            sum += Double.parseDouble(col.get(i));
        }
        return sum/col.size();
    }

    private static double getMedian(ArrayList<String> col){
        double median;
        int midIndex = col.size()/2;
        Collections.sort(col);
        if(col.size()%2==0){
            median = (Double.parseDouble(col.get(midIndex)) +
                    Double.parseDouble(col.get(midIndex-1)))/2;
        }
        else{
            median = Double.parseDouble(col.get(midIndex));
        }
        return median;
    }


    public static void main(String[] args){
        CSVReader ch = new CSVReader("csvTest1.csv");
        ch.readALL();
        ArrayList<String> rowData = ch.readRow();
        System.out.print(ch.data);
        System.out.print(rowData);
        CSVWriter writer = new CSVWriter("output.csv");
        writer.writeArray(ch.data,ch.getNumCol());
        writer.close();
    }
}
