package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyFileExtenstionTest {

    @Test
    void saveAndLoadCorgi() throws ChartException, IOException, ClassNotFoundException,DataTableException{
        CSVReader ch = new CSVReader("resources/csvTest1.csv");
        CSVReader ch_rowprob  = new CSVReader("resources/csvTest6.csv");
        ch.readALL(0);
        ch.readField();
        ch_rowprob.readField();
        ch_rowprob.readALL(0);
        DataTable dt  = DataTableTransformer.transform(ch);
        ArrayList<String> dt_reverse = DataTableTransformer.reverseTransform(dt);
        DataTableTransformer dtt = new DataTableTransformer();
        assertThrows(DataTableException.class, () -> {DataTableTransformer.transform(ch_rowprob);});
        Chart chart = new Pie(dt);
        ArrayList<Chart> charts = new ArrayList<>();
        charts.add(chart);
        ArrayList<DataTable> dts = new ArrayList<>();
        dts.add(dt);
        MyFileExtenstion.CorgiObj corgi = new MyFileExtenstion.CorgiObj(dts,charts, 0);
        MyFileExtenstion mfe = new MyFileExtenstion();
        mfe.saveCorgi("resources/extensionTest.corgi",corgi);
        MyFileExtenstion.CorgiObj corlFileNotFound = mfe.loadCorgi("resources/extensionTest_.corgi");
        MyFileExtenstion.CorgiObj corl = mfe.loadCorgi("resources/extensionTest.corgi");
        assertEquals(corl.getDts().get(0),dt);
        assertEquals(corl.getCharts().get(0),chart);
        corgi.setName("corgi_1");
        assertEquals(corgi.getName(),"corgi_1");
        assertEquals(corgi.getIndex(),0);
    }
}