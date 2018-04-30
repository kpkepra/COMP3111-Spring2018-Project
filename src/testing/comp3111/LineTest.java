package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.comp3111.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    DataTable dataTable;

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

        Chart testLine = new Line(dataTable);
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
}