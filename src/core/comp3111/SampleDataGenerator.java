package core.comp3111;

import java.util.Random;

/**
 * A helper class to illustrate how to generate data and store it into a
 * DataTable object
 *
 * @author cspeter
 *
 */
public class SampleDataGenerator {

    /**
     * A sample data generation. It illustrates how to use the DataTable class
     * implemented in the base code
     *
     * @return DataTable object
     */
    public static DataTable generateSampleLineData() {

        DataTable t = new DataTable();

        // Sample: An array of integer
        Number[] xvalues = new Integer[] { 1, 2, 3, 4, 5 };
        DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);

        // Sample: Can also mixed Number types
        Number[] yvalues = new Number[] { 30.0, 25, (short) 16, 8.0, (byte) 22 };
        DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

        // Sample: A array of String
        String[] labels = new String[] { "P1", "P2", "P3", "P4", "P5" };
        DataColumn labelsCol = new DataColumn(DataType.TYPE_STRING, labels);

        try {

            t.addCol("X", xvaluesCol);
            t.addCol("Y", yvaluesCol);
            t.addCol("label", labelsCol);

        } catch (DataTableException e) {
            e.printStackTrace();

        }

        return t;
    }

    /**
     * A sample data generation. It illustrates how to use the DataTable class
     * implemented in the base code
     *
     * @return DataTable object
     */
    public static DataTable generateSampleLineDataV2() {
        DataTable t = new DataTable();

        final int num = 100;
        Number[] xvalues = new Integer[num]; // Integer is a subclass of Number
        Number[] yvalues = new Integer[num];
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            xvalues[i] = i; // int: 0..num-1
            yvalues[i] = r.nextInt(500) + 100; // Random int: 100...600
        }

        DataColumn xvaluesCol = new DataColumn(DataType.TYPE_NUMBER, xvalues);
        DataColumn yvaluesCol = new DataColumn(DataType.TYPE_NUMBER, yvalues);

        try {

            t.addCol("X", xvaluesCol);
            t.addCol("Y", yvaluesCol);

        } catch (DataTableException e) {
            e.printStackTrace();

        }

        return t;
    }

    private DataTable generatePieTestData() {
        DataTable data = new DataTable();

        Number[] num = new Integer[] { 213, 67, 36 };
        DataColumn numCol = new DataColumn(DataType.TYPE_NUMBER, num);

        String[] text = new String[] { "Desktop", "Phone", "Tablet" };
        DataColumn textCol = new DataColumn(DataType.TYPE_STRING, text);

        try {
            data.addCol("Text", textCol);
            data.addCol("Num", numCol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private DataTable generateLineTestData() {
        DataTable data = new DataTable();

        Number[] num1 = new Integer[] { 100, 67, 36 };
        DataColumn numCol1 = new DataColumn(DataType.TYPE_NUMBER, num1);

        Number[] num2 = new Integer[] { 123, 33, 72 };
        DataColumn numCol2 = new DataColumn(DataType.TYPE_NUMBER, num2);

        try {
            data.addCol("Num1", numCol1);
            data.addCol("Num2", numCol2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}