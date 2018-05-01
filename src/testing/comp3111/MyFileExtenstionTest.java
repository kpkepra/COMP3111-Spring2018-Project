package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyFileExtenstionTest {

    @Test
    void saveAndLoadCorgi() {
        try{
            CSVReader ch = new CSVReader("csvTest1.csv");
            ch.readALL(0);
            ch.readField();
            DataTable dt  = DataTableTransformer.transform(ch);
            Chart chart = new Pie(dt);
            ArrayList<Chart> charts = new ArrayList<>();
            charts.add(chart);
            MyFileExtenstion.CorgiObj corgi = new MyFileExtenstion.CorgiObj(dt,charts);
            MyFileExtenstion mfe = new MyFileExtenstion();
            mfe.saveCorgi("extensionTest.corgi",corgi);
            MyFileExtenstion.CorgiObj corlFileNotFound = mfe.loadCorgi("extensionTest_.corgi");
            MyFileExtenstion.CorgiObj corl = mfe.loadCorgi("extensionTest.corgi");
            assertEquals(corl.getDt(),dt);
            assertEquals(corl.getCharts().get(0),chart);
        }
        catch (ChartException ce){
            System.out.print("chart gg");
        }
        catch(IOException ioe){
            System.out.println("IO Exception in FileExtension");
        }
        catch(ClassNotFoundException efe) {
            System.out.println("Corgi, chart or dataTable class not found! ");
        }
    }

}