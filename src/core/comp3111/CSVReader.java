package core.comp3111;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * CSVReader - CSVReader is a class that can read .csv file. After reading the CSV file
 * All the information stored into two different ArrayList<String> data and fields.
 * Fields store all the data title and data store the data itself all with type String.
 * @author Wu Yun Ju
 */
public class CSVReader {
    private File inputFile;
    private ArrayList<String> data;
    private ArrayList<String> fields;
    private int numCol;
    private int numRow;


    /**
     * Construct - Create an empty CSV reader with data and fields all point to null
     */
    public CSVReader() {
    }

    /**
     * Construct - Create an empty CSV reader with data and fields all point to null
     * @param fileName
     *      - Create the File with this file name
     */
    public CSVReader(String fileName) {
        inputFile = new File(fileName);
        data = new ArrayList<>();
        fields = new ArrayList<>();
    }

    /**
     * Returns the number of column in CSV file
     * @return the number of column in the csv file
     */
    public int getNumCol() {
        return numCol;
    }
    
    /**
     * Returns the input file.
     * @return the input file.
     */
    public File getFile() {
    	return inputFile;
    }

    /**
     * Returns the data(excluding the titles in CSV) of CSV file
     * @return the data(excluding the titles in CSV) of CSV file
     */
    public ArrayList<String> getData() {
        return data;
    }

    /**
     * Returns the titles of CSV file
     * @return the titles of CSV file
     */
    public ArrayList<String> getFields() {
        return fields;
    }

    /**
     * Read the titles of the CSV file and decide how many data columns
     * this csv file have.
     */
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

    /**
     * Read the data of the CSV file. Missing data will be handle through the call
     * of missing data handler.
     * @param command
     *          - there will be 3 different ways to handle the missing data
     *          0: fill the data with 0
     *          1: fill the data with mean
     *          2: fill the data with median
     */
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

    /**
     * check if the current column with String data is actually a numeric row.
     * @param data
     *      -the first non-empty data in the current column
     * @return true if this column is numeric false otherwise
     */
    private static boolean isNumericCol(String data) {
        try {
            Double.parseDouble(data);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /**
     * Handle the missing data in the csv file according to the command.
     * This will only handle missing numeric data, non-numeric data will be
     * blank (Type String ,val:"")
     * @param isNumeric
     *      -if this column is numeric then perform handling else do nothing
     * @param index
     *      -the index of the missing data in the ArrayList data
     * @param command
     *      - the way to handle missing data as specified in readAll
     */
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


    /**
     * Returns the column of the csv file for data at position index in the
     * data Arraylist (excluding this data)
     * @param index
     *      -the current data position the in data Arraylist
     * @return
     *      -the String Array of all other data in the same column of this current data
     */
    private ArrayList<String> getColWithoutIndex(int index){
       ArrayList<String> curCol = new ArrayList<>();
       for(int i = 0; i < data.size();i++){
           if(i%numCol == index %numCol && i != index){
               curCol.add(data.get(i));
           }
       }
       return curCol;
    }

    /**
     * Returns the column of the csv file of the specified colNum
     * @return the column of the csv file of the specified colNum
     */
    public ArrayList<String> getCol(int colNum) {
        ArrayList<String> curCol = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i % numCol == colNum) {
                curCol.add(data.get(i));
            }
        }
        return curCol;
    }

    /**
     * Returns the mean(Double) of the numeric column
     * @return tthe mean(Double) of the numeric column
     */
    private static double getMean(ArrayList<String> col) {
        double sum = 0.0;
        for (int i = 0; i < col.size(); i++) {
            sum += Double.parseDouble(col.get(i));
        }
        return sum / col.size();
    }

    /**
     * Returns the meadian (Double) of the numeric column
     * @return tthe meadian (Double) of the numeric column
     */
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
