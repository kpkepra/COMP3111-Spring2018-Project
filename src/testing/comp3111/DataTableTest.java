package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class DataTableTest {
    DataColumn testDataColumn;
    DataTable dataTable;

    @BeforeEach
    void init() {
        dataTable = new DataTable();
        testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1, 2, 3});
    }

    @Test
    void testGetNumRow_Empty() {
        assertEquals(0, dataTable.getNumRow());
    }

    @Test
    void testGetNumRow_NonEmpty() throws DataTableException {
        dataTable.addCol("testColumn", new DataColumn());

        assertEquals(0, dataTable.getNumRow());
    }

    @Test
    void testGetCol_NonExistent() throws DataTableException {
        dataTable.addCol("testNumberColumn", testDataColumn);

        DataColumn dataColumn = dataTable.getCol("testStringColumn");

        assertEquals(null, dataColumn);
    }

    @Test
    void testAddCol_AlreadyExists() throws DataTableException {
        dataTable.addCol("testNumberColumn", testDataColumn);

        assertThrows(DataTableException.class, () -> dataTable.addCol("testNumberColumn", new DataColumn()));
    }

    @Test
    void testAddCol_RowNotEqual() throws DataTableException {
        dataTable.addCol("Column1", testDataColumn);

        assertThrows(DataTableException.class, () -> dataTable.addCol("Column2", new DataColumn(DataType.TYPE_NUMBER, new Number[]{1, 2, 3, 4})));
    }

    @Test
    void testRemoveCol_SuccessfulRemove() throws DataTableException {
        dataTable.addCol("Column", testDataColumn);

        dataTable.removeCol("Column");

        assertEquals(0, dataTable.getNumRow());
    }

    @Test
    void testRemoveCol_NonExistent() throws DataTableException {
        assertThrows(DataTableException.class, () -> dataTable.removeCol("Column"));
    }

    @Test
    void testContainsColumn_Exist() throws DataTableException {
        dataTable.addCol("Column", testDataColumn);
        assertEquals(true, dataTable.containsColumn("Column"));
    }

    @Test
    void testContainsColumn_NonExistent() throws DataTableException {
        dataTable.addCol("Column123", testDataColumn);
        assertEquals(false, dataTable.containsColumn("Column"));
    }

    @Test
    void testGetNumCol_Empty() {
        assertEquals(0, dataTable.getNumCol());
    }

    @Test
    void testGetNumCol_NonEmpty() throws DataTableException {
        dataTable.addCol("testColumn", new DataColumn());

        assertEquals(1, dataTable.getNumCol());
    }

    @Test
    void testGetColNames_Empty() throws DataTableException {
        boolean equality_check = Arrays.deepEquals(new String[0], dataTable.getColNames());
        assertEquals(true, equality_check);
    }

    @Test
    void testGetColNames_NonEmpty() throws DataTableException {
        dataTable.addCol("testColumn", testDataColumn);
        String[] column_contents = {"testColumn"};

        boolean equality_check = Arrays.deepEquals(column_contents, dataTable.getColNames());
        assertEquals(true, equality_check);
    }

    @Test
    void testRandomSplit_PercentEmpty() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setPercentSplit(new float[0]);

        assertThrows(DataTableException.class, () -> {dataTable.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentLessThan100() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setPercentSplit(new float[]{30.5f, 69.4f});

        assertThrows(DataTableException.class, () -> {dataTable.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentMoreThan100() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setPercentSplit(new float[]{30.5f, 69.6f});

        assertThrows(DataTableException.class, () -> {dataTable.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentEqual100() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setPercentSplit(new float[]{30.5f, 69.5f});

        dataTable.randomSplit();
    }

    @Test
    void testFilterData_NullColumn() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setNumberFilter("30.0");
        dataTable.setOperatorFilter("<");

        Throwable exception = assertThrows(DataTableException.class, () -> {dataTable.filterData();});

        assertEquals("DataTableException: Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_NullNumber() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setOperatorFilter("<");

        Throwable exception = assertThrows(DataTableException.class, () -> {dataTable.filterData();});

        assertEquals("DataTableException: Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_NullOperator() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("30.0");

        Throwable exception = assertThrows(DataTableException.class, () -> {dataTable.filterData();});

        assertEquals("DataTableException: Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_ColumnFilterNoMatch() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("a");
        dataTable.setNumberFilter("30.0");
        dataTable.setOperatorFilter("<");

        Throwable exception = assertThrows(DataTableException.class, () -> {dataTable.filterData();});

        assertEquals("DataTableException: Failed to format column data", exception.getMessage());
    }

    @Test
    void testFilterData_NumberFilterStringInput() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("a");
        dataTable.setOperatorFilter("<");

        Throwable exception = assertThrows(DataTableException.class, () -> {dataTable.filterData();});

        assertEquals("DataTableException: Failed to format number", exception.getMessage());
    }

    @Test
    void testFilterData_LessThanEqual_NoDataFiltered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("3.00");
        dataTable.setOperatorFilter("<=");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_LessThanEqual_Filtered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("2.99");
        dataTable.setOperatorFilter("<=");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[]{1, 2};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_LessThan_NoDataFiltered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("3.01");
        dataTable.setOperatorFilter("<");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_LessThan_Filtered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("3.00");
        dataTable.setOperatorFilter("<");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[]{1, 2};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThanEqual_NoDataFiltered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("1.00");
        dataTable.setOperatorFilter(">=");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThanEqual_Filtered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("1.01");
        dataTable.setOperatorFilter(">=");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThan_NoDataFiltered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("0.99");
        dataTable.setOperatorFilter(">");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_MoreThan_Filtered() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("1.00");
        dataTable.setOperatorFilter(">");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_Equal_NoMatch() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("0.99");
        dataTable.setOperatorFilter("==");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[0];

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_Equal_OneMatch() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("1.00");
        dataTable.setOperatorFilter("==");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[]{1};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_NotEqual_NoMatch() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("0.99");
        dataTable.setOperatorFilter("!=");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = dataTable.getCol("column").getData();

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }

    @Test
    void testFilterData_NotEqual_OneMatch() throws DataTableException {
        dataTable.addCol("column", testDataColumn);
        dataTable.setColumnFilter("column");
        dataTable.setNumberFilter("1.00");
        dataTable.setOperatorFilter("!=");

        Object[] filtered = dataTable.filterData().get("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }
}