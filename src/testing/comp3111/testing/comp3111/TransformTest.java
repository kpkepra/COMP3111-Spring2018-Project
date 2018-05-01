package testing.comp3111;

import core.comp3111.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        transform.setPercentSplit(new float[0]);

        assertThrows(TransformException.class, () -> {transform.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentLessThan100() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setPercentSplit(new float[]{30.5f, 69.4f});

        assertThrows(TransformException.class, () -> {transform.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentMoreThan100() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setPercentSplit(new float[]{30.5f, 69.6f});

        assertThrows(TransformException.class, () -> {transform.randomSplit();});
    }

    @Test
    void testRandomSplit_PercentEqual100() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setPercentSplit(new float[]{30.5f, 69.5f});

        transform.randomSplit();
    }

    @Test
    void testFilterData_NullColumn() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setNumberFilter("30.0");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_NullNumber() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setColumnFilter("column");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_NullOperator() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setColumnFilter("column");
        transform.setNumberFilter("30.0");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Filter parameters are not filled", exception.getMessage());
    }

    @Test
    void testFilterData_ColumnFilterNoMatch() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setColumnFilter("a");
        transform.setNumberFilter("30.0");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Failed to format column data", exception.getMessage());
    }

    @Test
    void testFilterData_NumberFilterStringInput() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setColumnFilter("column");
        transform.setNumberFilter("a");
        transform.setOperatorFilter("<");

        Throwable exception = assertThrows(TransformException.class, () -> {transform.filterData();});

        assertEquals("TransformException: Failed to format number", exception.getMessage());
    }

    @Test
    void testFilterData_LessThanEqual_NoDataFiltered() throws TransformException, DataTableException {
        dataTable.addCol("column", testDataColumn);
        transform.setColumnFilter("column");
        transform.setNumberFilter("3.00");
        transform.setOperatorFilter("<=");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("2.99");
        transform.setOperatorFilter("<=");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("3.01");
        transform.setOperatorFilter("<");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("3.00");
        transform.setOperatorFilter("<");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter(">=");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.01");
        transform.setOperatorFilter(">=");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("0.99");
        transform.setOperatorFilter(">");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter(">");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("0.99");
        transform.setOperatorFilter("==");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter("==");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("0.99");
        transform.setOperatorFilter("!=");

        Object[] filtered = transform.filterData().get("column").getData();
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
        transform.setColumnFilter("column");
        transform.setNumberFilter("1.00");
        transform.setOperatorFilter("!=");

        Object[] filtered = transform.filterData().get("column").getData();
        Object[] original = new Object[]{2, 3};

        boolean testEqual = true;
        for (int i = 0; i < filtered.length; ++i) {
            if (!Objects.equals(filtered[i], original[i])) testEqual = false;
        }
        assertEquals(true, testEqual);
    }
}