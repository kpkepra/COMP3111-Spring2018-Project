package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieTest {
    DataTable dataTable;

    @BeforeEach
    void init() {
        dataTable = new DataTable();
    }

    @Test
    void testIsLegal_Legal() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        dataTable.addCol("numCol", numCol1);
        dataTable.addCol("textCol", numCol2);

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
        DataColumn numCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});
        dataTable.addCol("textCol", numCol2);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_Empty() throws DataTableException, ChartException {
        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }

    @Test
    void testIsLegal_Illegal_NegativeValue() throws DataTableException, ChartException {
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, new Number[]{-1,2,3});
        DataColumn numCol2 = new DataColumn(DataType.TYPE_STRING, new String[]{"A", "B", "C"});

        dataTable.addCol("textCol", numCol2);
        dataTable.addCol("numCol", numCol1);

        assertThrows(ChartException.class, () -> {new Pie(dataTable);});
    }
}