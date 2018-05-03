package core.comp3111;

import java.io.*;
import java.util.ArrayList;

import ui.comp3111.Listbox;


/**
 * MyFileExtension - MyFileExtension provides load and save features to our
 * self-defined fileType .corgi extension
 * Each of the .corgi file has one Corgi object,
 * in the corgi object there is an ArrayList of DataTable and an ArrayList of charts
 * @author Wu Yun Ju
 */
public class MyFileExtenstion {

    /**
     * CorgiObj - CorgiObj is an inner class of myFileExtension
     * stores the DataTables and Charts that the users want to save together
     * in the .corgi file
     * the name variable store the name of .corgi file
     */
    public static class CorgiObj implements Serializable{
        private ArrayList<DataTable> dts;
        private ArrayList <Chart> charts;
        private int displayIndex;
        private String name;
        public CorgiObj(){
            dts = new ArrayList<>();
            charts = new ArrayList<>();
        }

        /**
         * Construct - create a CorgiObj that store an ArrayList of dataTable and
         * an ArrayList of charts
         *
         * @param dts
 *              - The ArrayList of dataTables that will be stored in .corgi file
         * @param charts
         *      - The ArrayList of charts that will be stored in .corgi file
         */
        public CorgiObj(ArrayList<DataTable> dts, ArrayList<Chart> charts, int i){
            this.dts = dts;
            this.charts = charts;
            this.displayIndex = i;
        }

        /**
         * Returns the ArrayList of DataTables
         * @return the ArrayList of DataTables
         */
        public ArrayList<DataTable> getDts() {
            return dts;
        }

        /**
         * Returns the ArrayList of Charts
         * @return the ArrayList of Charts
         */
        public ArrayList<Chart> getCharts() {
            return charts;
        }
        
        /**
         * Returns the current index of displayed table
         * @return the current index of displayed table
         */
        public int getIndex() {
        	return displayIndex;
        }

        /**
         * Sets the name of the .corgi file
         */
        public void setName(String fileName) {
        	name = fileName;
        }

        /**
         * returns the name of the .corgi file
         * @return the name of the .corgi file
         */
        public String getName() {
        	return name;
        }
    }


    /**
     * load the .corgi file and return back the CorgiObj
     * @param fileName
     *      -the fileName of the file that is about to be loaded
     * @return CorgiObj
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public CorgiObj loadCorgi(String fileName) throws IOException,ClassNotFoundException{
        CorgiObj corgi = new CorgiObj();
        try {
            FileInputStream fileIn = new FileInputStream("resources/" + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            corgi = (CorgiObj) in.readObject();
            corgi.setName(fileName);
            in.close();
            fileIn.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find the Corgi file");
        }
        return corgi;
    }


    /**
     * save the CorgiObj into the .corgi file
     * @param fileName
     *      -the fileName of the file that is about to be loaded
     * @return true if the CorgiObj is saved successfully false otherwise
     * @throws IOException
     */
    public boolean saveCorgi(String fileName,CorgiObj corgi) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(corgi);
        out.close();
        fileOut.close();
        System.out.println("Serialized data is saved in " + fileName);
        return true;
    }

}

