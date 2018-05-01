package core.comp3111;

import java.io.*;
import java.util.ArrayList;

public class MyFileExtenstion {
    public static class CorgiObj implements Serializable{
        private DataTable dt;
        private ArrayList <Chart> charts;
        private String name;
        public CorgiObj(){
            dt = new DataTable();
            charts = new ArrayList<>();
        }
        public CorgiObj(DataTable dt,ArrayList<Chart> charts){
            this.dt = dt;
            this.charts = charts;
        }

        public DataTable getDt() {
            return dt;
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

