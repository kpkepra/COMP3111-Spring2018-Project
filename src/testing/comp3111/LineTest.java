package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    DataTable dataTable;
    Line testLine;

    @BeforeEach
    void init() {
        dataTable = new DataTable();

    }

    @Test
    void testIsLegal_Legal() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);

        testLine = new Line(dataTable);
    }

    @Test
    void testIsLegal_Illegal_OneNumOneText() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", numCol2);

        assertThrows(ChartException.class, () -> {new Line(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_OneNum() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        dataTable.addCol("numCol", numCol1);

        assertThrows(ChartException.class, () -> {new Line(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_Empty() throws DataTableException, ChartException {
        assertThrows(ChartException.class, () -> {new Line(dataTable);});
    }

    @Test
    void testGetX() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);

        testLine = new Line(dataTable);

        assertEquals("numCol1", testLine.getX());
    }

    @Test
    void testGetY() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);

        testLine = new Line(dataTable);

        assertEquals("numCol2", testLine.getY());
    }

    @Test
    void testGetNumCols_TwoNumColumns() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);

        testLine = new Line(dataTable);

        ArrayList<String> tempCols = new ArrayList<String>();
        tempCols.add("numCol1");
        tempCols.add("numCol2");

        assertEquals(tempCols, testLine.getNumCols());
    }

    @Test
    void testGetNumCols_MoreThanTwoNumColumns() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});
        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);

        testLine = new Line(dataTable);

        ArrayList<String> tempCols = new ArrayList<String>();
        tempCols.add("numCol1");
        tempCols.add("numCol2");
        tempCols.add("numCol3");

        assertEquals(tempCols, testLine.getNumCols());
    }

    @Test
    void testGetNumCols_TwoNumColumns_TextColumns() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        ArrayList<String> tempCols = new ArrayList<String>();
        tempCols.add("numCol1");
        tempCols.add("numCol2");
        tempCols.add("numCol4");

        assertEquals(tempCols, testLine.getNumCols());
    }

    @Test
    void testSetX_NonExistent() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        testLine.setX("RandomInput");

        assertEquals("numCol1", testLine.getX());
    }

    @Test
    void testSetX_StringColumn() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        testLine.setX("numCol3");

        assertEquals("numCol1", testLine.getX());
    }

    @Test
    void testSetX_NumColumn() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        testLine.setX("numCol4");

        assertEquals("numCol4", testLine.getX());
    }

    @Test
    void testSetY_NonExistent() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        testLine.setY("RandomInput");

        assertEquals("numCol2", testLine.getY());
    }

    @Test
    void testSetY_StringColumn() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        testLine.setY("numCol3");

        assertEquals("numCol2", testLine.getY());
    }

    @Test
    void testSetY_NumColumn() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{10.5, 13.4, 7});
        DataColumn numCol3 = new DataColumn(DataType.TYPE_STRING, new String[]{"a", "b", "c"});
        DataColumn numCol4 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-3, -22, -1.37});

        dataTable.addCol("numCol1", numCol1);
        dataTable.addCol("numCol2", numCol2);
        dataTable.addCol("numCol3", numCol3);
        dataTable.addCol("numCol4", numCol4);

        testLine = new Line(dataTable);

        testLine.setY("numCol4");

        assertEquals("numCol4", testLine.getY());
    }
}