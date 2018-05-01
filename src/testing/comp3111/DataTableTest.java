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
}