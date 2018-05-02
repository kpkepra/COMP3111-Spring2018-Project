package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PieTest {
    Pie testPie;
    DataTable dataTable;

    @BeforeEach
    void init() {
        dataTable = new DataTable();
    }

    @Test
    void testIsLegal_Legal() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", textCol);

        Chart testPie = new Pie(dataTable);
    }

    @Test
    void testIsLegal_Illegal_NoText() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        dataTable.addCol("numCol", numCol1);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_NoNum() throws DataTableException, ChartException {
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        dataTable.addCol("textCol", textCol);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_Empty() throws DataTableException, ChartException {
        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_NegativeValue() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("textCol", textCol);
        dataTable.addCol("numCol", numCol1);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testGetText() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol", textCol);

        testPie = new Pie(dataTable);

        assertEquals("textCol", testPie.getText());
    }

    @Test
    void testGetNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("textCol", textCol);

        testPie = new Pie(dataTable);

        assertEquals("numCol1", testPie.getNum());
    }

    @Test
    void testGetTextCols_OneText_OneNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", textCol);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("textCol");

        assertEquals(tempMap, testPie.getTextCols());
    }

    @Test
    void testGetTextCols_OneText_TwoNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol", textCol);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("textCol");

        assertEquals(tempMap, testPie.getTextCols());
    }

    @Test
    void testGetTextCols_TwoText_OneNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", textCol);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("numCol");

        assertEquals(tempMap, testPie.getNumCols());
    }

    @Test
    void testGetNumCols_OneText_TwoNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol", textCol);

        testPie = new Pie(dataTable);

        ArrayList<String> tempMap = new ArrayList<String>();
        tempMap.add("numCol1");
        tempMap.add("numCol2");

        assertEquals(tempMap, testPie.getNumCols());
    }

    @Test
    void testGetNumCols_TwoText_OneNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setText("RandomInput");

        assertEquals("textCol1", testPie.getText());
    }

    @Test
    void testSetText_NumColumn() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

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
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn textCol1 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        DataColumn textCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"D", "E", "F"});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("textCol1", textCol1);
        dataTable.addCol("textCol2", textCol2);

        testPie = new Pie(dataTable);

        testPie.setNum("textCol2");

        assertEquals("numCol1", testPie.getNum());
    }
}