package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyFileExtenstionTest {

    @Test
    void saveAndLoadCorgi() {
        try{
            CSVReader ch = new CSVReader("csvTest1.csv");
            CSVReader ch_rowprob  = new CSVReader("csvTest6.csv");
            ch.readALL(0);
            ch.readField();
            ch_rowprob.readField();
            ch_rowprob.readALL(0);
            DataTable dt  = DataTableTransformer.transform(ch);
            ArrayList<String> dt_reverse = DataTableTransformer.reverseTransform(dt);
            DataTable dt2  = DataTableTransformer.transform(ch_rowprob);
            Chart chart = new Pie(dt);
            ArrayList<Chart> charts = new ArrayList<>();
            charts.add(chart);
            ArrayList<DataTable> dts = new ArrayList<>();
            dts.add(dt);
            MyFileExtenstion.CorgiObj corgi = new MyFileExtenstion.CorgiObj(dts,charts);
            MyFileExtenstion mfe = new MyFileExtenstion();
            mfe.saveCorgi("extensionTest.corgi",corgi);
            MyFileExtenstion.CorgiObj corlFileNotFound = mfe.loadCorgi("extensionTest_.corgi");
            MyFileExtenstion.CorgiObj corl = mfe.loadCorgi("extensionTest.corgi");
            assertEquals(corl.getDts().get(0),dt);
            assertEquals(corl.getCharts().get(0),chart);
            corgi.setName("corgi_1");
            assertEquals(corgi.getName(),"corgi_1");
//            String [] fields = ch.getFields().
//                    toArray(new String[ch.getFields().size()]);
//            String [] data = ch.getData().
//                    toArray(new String[ch.getData().size()]);
//            int fl = fields.length;
//            int dl = data.length;
//            String [] result = new String[fl+dl];
//            System.arraycopy(fields, 0, result, 0, fl);
//            System.arraycopy(data, 0, result, fl, dl);
//            assertEquals(new ArrayList<>(Arrays.asList(result)),dt_reverse);
        }
        catch (ChartException ce){
            System.out.print("chart gg");
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("IO Exception in FileExtension");
        }
        catch(ClassNotFoundException efe) {
            System.out.println("Corgi, chart or dataTable class not found! ");
        }
    }

}