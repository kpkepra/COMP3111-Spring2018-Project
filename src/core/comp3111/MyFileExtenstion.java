package core.comp3111;

import java.io.*;
import java.util.ArrayList;

public class MyFileExtenstion {

    public CorgiObj loadCorgi(String fileName){
        CorgiObj corgi = new CorgiObj();
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            corgi = (CorgiObj) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find the Corgi file");

        } catch (IOException i) {
            System.out.println("Cannot find the Corgi file");
        }
        catch (ClassNotFoundException c) {
            System.out.println("class not found!");
            c.printStackTrace();
        }
        return corgi;
    }

    public boolean saveCorgi(String Filename,CorgiObj corgi){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(Filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(corgi);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in extensionTest2.corgi");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
        return true;
    }

    /*for testing only*/
//    static  TestObj t = new TestObj();
//    public static void main(String args[]) {
//        CSVReader ch = new CSVReader("csvTest1.csv");
//        ch.readALL(0);
//        ch.readField();
//        System.out.println(ch.getData());
//        System.out.println(ch.getFields());
//        DataTable dt  = DataTableTransformer.transform(ch);
//        try {
//            Chart chart = new Pie(dt);
//            ArrayList<Chart> charts = new ArrayList<>();
//            charts.add(chart);
//            CorgiObj corgi = new CorgiObj(dt,charts);
//            MyFileExtenstion mfe = new MyFileExtenstion();
//            mfe.saveCorgi("extensionTest2.corgi",corgi);
//            CorgiObj corl = mfe.loadCorgi("extensionTest2.corgi");
//            corl.printAndDisplayCharts();
//        }
//        catch (ChartException ce){
//            System.out.print("chart gg");
//        }


//        try {
//            //write an object
//            FileOutputStream fileOut =
//                    new FileOutputStream("extensionTest1.corgi");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(t);
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is saved in extensionTest1.corgi");
//
//            //load an object
//            FileInputStream fileIn = new FileInputStream("extensionTest1.corgi");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            t = (TestObj) in.readObject();
//            in.close();
//            fileIn.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//        catch (ClassNotFoundException c) {
//            System.out.println("class not found!");
//            c.printStackTrace();
//        }
//        System.out.println(t.num);
//        t.f();
//    }

}

//
//class TestObj implements Serializable{
//
//    int num;
//    private int privateNum;
//    TestObj(){
//        num = 123;
//        privateNum = 456;
//    }
//    public void f(){
//        System.out.print(privateNum);
//    }
//}

class CorgiObj implements Serializable{
    DataTable dt;
    ArrayList <Chart> charts;
    public CorgiObj(){
        dt = new DataTable();
        charts = new ArrayList<>();
    }
    public CorgiObj(DataTable dt,ArrayList<Chart> charts){
        this.dt = dt;
        this.charts = charts;
    }

    public void printAndDisplayCharts(){
        dt.printTable();
        for(Chart chart:charts){
//            chart.display();
        }
    }
}
