package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TransformTest {
    Transform transform;
    DataTable dataTable;
    DataColumn testDataColumn;

    @BeforeEach
    void init() {
        dataTable = new DataTable();
        testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
        transform = new Transform(dataTable);
    }

    @Test
    void testRandomSplit_PercentEmpty() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setPercentSplit(new float[0]);

        assertThrows(TransformException.class, () -> {transform.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentLessThan100() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setPercentSplit(new float[]{30.5f, 69.4f});

        assertThrows(TransformException.class, () -> {transform.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentMoreThan100() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setPercentSplit(new float[]{30.5f, 69.6f});

        assertThrows(TransformException.class, () -> {transform.randomSplit();});
    }

    @Test
    void testRandomSplit_5050() throws TransformException, DataTableException {
        dataTable = new DataTable();
        testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Double[]{1d,2d,3d,4d,5d,6d,7d,8d,9d,10d});
        dataTable.addCol("column", testDataColumn);

        transform.setDataTable(dataTable);
        transform.setPercentSplit(new float[]{50f, 50f});

        DataTable[] newTables = transform.randomSplit();

        assertEquals(5, newTables[0].getNumRow());
        assertEquals(5, newTables[1].getNumRow());
    }

    @Test
    void testRandomSplit_9010() throws TransformException, DataTableException {
        dataTable = new DataTable();
        testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Double[]{1d,2d,3d,4d,5d,6d,7d,8d,9d,10d});
        dataTable.addCol("column", testDataColumn);

        transform.setDataTable(dataTable);
        transform.setPercentSplit(new float[]{90f, 10f});

        DataTable[] newTables = transform.randomSplit();

        assertEquals(9, newTables[0].getNumRow());
        assertEquals(1, newTables[1].getNumRow());
    }

    @Test
    void testFilterData_NullColumn() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setDataTable(dataTable);
        transform.setNumberFilter("30.0");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Column Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_NullNumber() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Number Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_NullOperator() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("30.0");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Operator Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_ColumnFilterNoMatch() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("a");
        transform.setNumberFilter("30.0");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Failed to format column data", exception.getMessage());
    }

    @Test
    void testFilterData_NumberFilterStringInput() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("a");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Failed to format number", exception.getMessage());
    }

    @Test
    void testFilterData_LessThanEqual_NoDataFiltered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("3.00");
        transform.setOperatorFilter("<=");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_LessThanEqual_Filtered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("2.99");
        transform.setOperatorFilter("<=");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[]{1, 2};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_LessThan_NoDataFiltered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("3.01");
        transform.setOperatorFilter("<");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_LessThan_Filtered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("3.00");
        transform.setOperatorFilter("<");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[]{1, 2};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThanEqual_NoDataFiltered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter(">=");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThanEqual_Filtered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.01");
        transform.setOperatorFilter(">=");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThan_NoDataFiltered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("0.99");
        transform.setOperatorFilter(">");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThan_Filtered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter(">");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_Equal_NoMatch() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("0.99");
        transform.setOperatorFilter("==");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[0];

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_Equal_OneMatch() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter("==");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[]{1};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_NotEqual_NoMatch() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("0.99");
        transform.setOperatorFilter("!=");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_NotEqual_OneMatch() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter("!=");

        Object[] filtered = transform.filterData().getCol("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testGetColumnFilter_Empty() throws TransformException {
        assertEquals(null, transform.getColumnFilter());
    }

    @Test
    void testGetColumnFilter_NonEmpty() throws TransformException {
        transform.setColumnFilter("column");
        assertEquals("column", transform.getColumnFilter());
    }

    @Test
    void testGetOperatorFilter_Empty() throws TransformException {
        assertEquals(null, transform.getOperatorFilter());
    }

    @Test
    void testGetOperatorFilter_NonEmpty() throws TransformException {
        transform.setOperatorFilter("<");
        assertEquals("<", transform.getOperatorFilter());
    }

    @Test
    void testGetNumberFilter_Empty() throws TransformException {
        assertEquals(null, transform.getNumberFilter());
    }

    @Test
    void testGetNumberFilter_NonEmpty() throws TransformException {
        transform.setNumberFilter("1.2");
        assertEquals("1.2", transform.getNumberFilter());
    }

    @Test
    void testGetPercentSplit_Empty() throws TransformException {
        assertEquals(null, transform.getPercentSplit());
    }

    @Test
    void testGetPercentSplit_oneElement() throws TransformException {
        transform.setPercentSplit(new float[]{1.1f});
        assertEquals(true, Arrays.equals(new float[]{1.1f}, transform.getPercentSplit()));
    }

    @Test
    void testGetPercentSplit_twoElement() throws TransformException {
        transform.setPercentSplit(new float[]{1.1f, -1.1f});
        assertEquals(true, Arrays.equals(new float[]{1.1f, -1.1f}, transform.getPercentSplit()));
    }

    @Test
    void testGetDataTable() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setDataTable(dataTable);
        boolean testEquality = true;
        for (String key : dataTable.getColNames()) {
            if (!Arrays.equals(dataTable.getCol(key).getData(), transform.getDataTable().getCol(key).getData())) testEquality = false;
        }

        assertEquals(true, testEquality);
    }

    @Test
    void testSetDataTable() throws DataTableException {
        DataTable testDataTable = new DataTable();
        DataColumn newColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[]{1.1f, 2});
        testDataTable.addCol("newColumn", newColumn);

        transform.setDataTable(testDataTable);

        boolean testEquality = true;
        for (String key : dataTable.getColNames()) {
            if (!Arrays.equals(testDataTable.getCol(key).getData(), transform.getDataTable().getCol(key).getData())) testEquality = false;
        }

        assertEquals(true, testEquality);
    }
}