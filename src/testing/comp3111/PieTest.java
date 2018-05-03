package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PieTest {
    Pie testPie;
    DataTable dataTable;
    DataColumn numCol1;
    DataColumn numCol2;
    DataColumn textCol1;
    DataColumn textCol2;

    @BeforeEach
    void init() {
        dataTable = new DataTable();
        numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Double[]{1.0d,2.0d,3.0d});
        numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Double[]{1.3d,12.7d,11.3d});
        textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});
    }

    @Test
    void testIsLegal_Legal() throws DataTableException, ChartException {
        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", textCol1);

        Chart testPie = new Pie(dataTable);
    }

    @Test
    void testIsLegal_Illegal_NoText() throws DataTableException, ChartException {
        dataTable.addCol("numCol", numCol1);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_NoNum() throws DataTableException, ChartException {
        dataTable.addCol("textCol", textCol1);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_Empty() throws DataTableException, ChartException {
        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_NegativeValue() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Double[]{-1d,2d,3d});

        dataTable.addCol("textCol", textCol1);
        dataTable.addCol("numCol", numCol1);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testGetText() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol", textCol1);

        testPie = new Pie(dataTable);

        assertEquals("textCol", testPie.getText());
    }

    @Test
    void testGetNum() throws DataTableException, ChartException {

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol", textCol1);

        testPie = new Pie(dataTable);

        assertEquals("numCol1", testPie.getNum());
    }

    @Test
    void testGetTextCols_OneText_OneNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", textCol1);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("textCol");

        assertEquals(tempMap, testPie.getTextCols());
    }

    @Test
    void testGetTextCols_OneText_TwoNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol", textCol1);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("textCol");

        assertEquals(tempMap, testPie.getTextCols());
    }

    @Test
    void testGetTextCols_TwoText_OneNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("textCol1");
        tempMap.add("textCol2");

        assertEquals(tempMap, testPie.getTextCols());
    }

    @Test
    void testGetTextCols_TwoText_TwoNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol1);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("textCol1");
        tempMap.add("textCol2");

        assertEquals(tempMap, testPie.getTextCols());
    }

    @Test
    void testGetNumCols_OneText_OneNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", textCol1);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("numCol");

        assertEquals(tempMap, testPie.getNumCols());
    }

    @Test
    void testGetNumCols_OneText_TwoNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol", textCol1);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("numCol1");
        tempMap.add("numCol2");

        assertEquals(tempMap, testPie.getNumCols());
    }

    @Test
    void testGetNumCols_TwoText_OneNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("numCol1");

        assertEquals(tempMap, testPie.getNumCols());
    }

    @Test
    void testGetNumCols_TwoText_TwoNum() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("numCol1");
        tempMap.add("numCol2");

        assertEquals(tempMap, testPie.getNumCols());
    }

    @Test
    void testSetText_NonExistant() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setText("RandomInput");

        assertEquals("textCol1", testPie.getText());
    }

    @Test
    void testSetText_NumColumn() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setText("numCol2");

        assertEquals("textCol1", testPie.getText());
    }

    @Test
    void testSetText_StringColumn() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setText("textCol2");

        assertEquals("textCol2", testPie.getText());
    }

    @Test
    void testSetNum_NonExistant() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setNum("RandomInput");

        assertEquals("numCol1", testPie.getNum());
    }

    @Test
    void testSetNum_NumColumn() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setNum("numCol2");

        assertEquals("numCol2", testPie.getNum());
    }

    @Test
    void testSetNum_StringColumn() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setNum("textCol2");

        assertEquals("numCol1", testPie.getNum());
    }

    @Test
    void testSetNum_Equals_NotEqual() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol1", textCol1);

        DataTable testingDataTable = new DataTable();
        testingDataTable.addCol("numCol2", numCol2);
        testingDataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);
        Pie testingPie = new Pie(testingDataTable);

        assertNotEquals(testingPie, testPie);
    }

    @Test
    void testSetNum_Equals_Equal() throws DataTableException, ChartException {
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol1", textCol1);

        DataTable testingDataTable = new DataTable();
        testingDataTable.addCol("numCol1", numCol1);
        testingDataTable.addCol("textCol1", textCol1);

        testPie = new Pie(dataTable);
        Pie testingPie = new Pie(testingDataTable);

        assertEquals(testPie, testingPie);
    }
}