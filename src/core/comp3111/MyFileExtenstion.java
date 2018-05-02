package core.comp3111;

import java.io.*;
import java.util.ArrayList;


/**
 * MyFileExtension - MyFileExtension provides load and save features to our
 * self-defined fileType .corgi extension
 * Each of the .corgi file has one Corgi object,
 * in the corgi object there is an ArrayList of DataTable and an ArrayList of charts
 */
public class MyFileExtenstion {
    public static class CorgiObj implements Serializable{
        private ArrayList<DataTable> dts;
        private ArrayList <Chart> charts;
        private String name;
        public CorgiObj(){
            dts = new ArrayList<>();
            charts = new ArrayList<>();
        }
        public CorgiObj(ArrayList<DataTable> dts,ArrayList<Chart> charts){
            this.dts = dts;
            this.charts = charts;
        }

        public ArrayList<DataTable> getDts() {
            return dts;
        }
        public ArrayList<Chart> getCharts() {
            return charts;
        }
        public void setName(String fileName) {
        	name = fileName;
        }
        public String getName() { 
        	return name;
        }
    }

    public CorgiObj loadCorgi(String fileName) throws IOException,ClassNotFoundException{
        CorgiObj corgi = new CorgiObj();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
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

    public boolean saveCorgi(String Filename,CorgiObj corgi) throws IOException{

        FileOutputStream fileOut = new FileOutputStream(Filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(corgi);
        out.close();
        fileOut.close();
        System.out.println("Serialized data is saved in extensionTest2.corgi");
        return true;
    }

}

