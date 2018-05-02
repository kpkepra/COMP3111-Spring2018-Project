package core.comp3111;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * CSVWriter - CSVWriter is a class that can write the Arraylist of fields and data
 * to the .csv file.
 * Notes: the dataTable needs to be transform to an Arraylist of String
 * using DataTableTransformer first.
 * @author Wu Yun Ju
 */
public class CSVWriter{
    private PrintWriter pw;
    private StringBuilder sb;

    /**
     * Construct
     * - Create an CSV writer that write to the specified Filename
     * @param fileName
     *      -specified the csv filename that we want to write to.
     * @throws FileNotFoundException
     */
    public CSVWriter(String fileName) throws FileNotFoundException {
            pw = new PrintWriter(new File(fileName));
            sb = new StringBuilder();
    }

    /**
     * write the String Array to the .csv file
     * @param words
     *      -the String Array that will be written into the CSV file
     * @param colNum
     *      -specified how many columns are there in the CSV file
     */
    public void writeArray(ArrayList<String> words,int colNum){
        int count = 0;
        int numOfDataRow = (words.size()-colNum)/colNum;
        int cur_row = 0;
        String lineSep = System.getProperty("line.separator");
        //read the fields
        for(int i = 0; i < colNum; i++){
            sb.append(words.get(i));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(lineSep);
        int index = colNum;
        for(int i = 0; i < numOfDataRow; i++){
            while(true) {
                    sb.append(words.get(index));
                    sb.append(",");
                    count++;
                    index += numOfDataRow;
                    //current row is full change to next row
                    if (count == colNum) {
                        //delete the last, and change to new row
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append(lineSep);
                        count = 0;
                        cur_row++;
                        index = colNum + cur_row;
                        break;
                    }
                }
            }
        pw.print(sb.toString());
        }

    /**
     * Close the printWriter whenever done writing to the .csv
     */
    public void close(){
        pw.close();
    }
}
