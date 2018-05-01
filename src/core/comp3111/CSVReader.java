package core.comp3111;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CSVReader {
    private File inputFile;
    private ArrayList<String> data;
    private ArrayList<String> fields;
    private int numCol;
    private int numRow;

    public CSVReader() {
    }

    public CSVReader(String fileName) {
        inputFile = new File(fileName);
        data = new ArrayList<>();
        fields = new ArrayList<>();
    }

    public int getNumCol() {
        return numCol;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getFields() {
        return fields;
    }


    public void readField() {
        ArrayList<String> row = new ArrayList<>();
        try {
            Scanner sc = new Scanner(inputFile);
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] rowData = line.split(",");
                row = new ArrayList<>(Arrays.asList(rowData));
                numCol = row.size();

            } else {
                System.out.print("Error: This is an empty file");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Existed");
        }
        fields = row;
    }


    public void readALL(int command) {
        try {

            data.clear();
            Scanner sc = new Scanner(inputFile);
            String lineSeparator = System.getProperty("line.separator");
            sc.useDelimiter(",|" + lineSeparator);
            //read the first line in file and construct the data fields
            readField();
            int count = 0;
            while (sc.hasNext()) {
                String current = sc.next();
                //Skip the fields get only data
                if (count++ < numCol) {
                    continue;
                }
                data.add(current);
            }

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).equals("")) {
                    System.out.println("NumCol" + numCol);
                    int colNum = (i % numCol);
                    System.out.println("colNum" + colNum);
                    System.out.println("dataSize" + data.size());
                    // this will fail if there's only fields without data
                    int nextNum = colNum;
                    try {
                        while (data.get(nextNum).equals("")) {
                            nextNum += numCol;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("This column is empty");
                        return;
                    }
                    missingDataHandler(isNumericCol(data.get(nextNum)), i, command);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: No such File!");
            return;
        }

    }

    private static boolean isNumericCol(String data) {
        try {
            Double.parseDouble(data);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void missingDataHandler(boolean isNumeric, int index, int command) {
        if (isNumeric) {
            System.out.println("Encounter empty data, " +
                    "please enter [0-2] to choose a fill-in method");
            System.out.println("[0]: Fill with 0 \n" +
                    "[1]: Fill with mean \n" +
                    "[2]: Fill with median");
            switch (command) {
                case 0:
                    System.out.println("replace with 0");
                    data.set(index, "0");
                    break;
                case 1:
                    System.out.println("replace with mean");
                    data.set(index, String.valueOf(getMean(getColWithoutIndex(index))));
                    break;
                case 2:
                    System.out.println("replace with median");
                    data.set(index, String.valueOf(getMedian(getColWithoutIndex(index))));
                    break;
                default:
                    System.out.println("Invalid Input");
            }

        }
    }

    private ArrayList<String> getColWithoutIndex(int index){
       ArrayList<String> curCol = new ArrayList<>();
       for(int i = 0; i < data.size();i++){
           if(i%numCol == index %numCol && i != index){
               curCol.add(data.get(i));
           }
       }
       return curCol;
    }

    public ArrayList<String> getCol(int colNum) {
        ArrayList<String> curCol = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i % numCol == colNum) {
                curCol.add(data.get(i));
            }
        }
        return curCol;
    }


    private static double getMean(ArrayList<String> col) {
        double sum = 0.0;
        for (int i = 0; i < col.size(); i++) {
            sum += Double.parseDouble(col.get(i));
        }
        return sum / col.size();
    }

    private static double getMedian(ArrayList<String> col) {
        double median;
        int midIndex = col.size() / 2;
        Collections.sort(col);
        if (col.size() % 2 == 0) {
            median = (Double.parseDouble(col.get(midIndex)) +
                    Double.parseDouble(col.get(midIndex - 1))) / 2;
        } else {
            median = Double.parseDouble(col.get(midIndex));
        }
        return median;
    }
}
