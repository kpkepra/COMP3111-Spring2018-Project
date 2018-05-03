package core.comp3111;

import java.io.Serializable;

/**
 * Chart - An abstract object which can be implemented in either Line or Pie form. The subclasses of this object can be
 * used to create a chart in the application. It stores the table which will be used in the chart (data).
 *
 * @author apsusanto
 *
 */
public abstract class Chart implements Serializable {
    protected DataTable data;

    /**
     * Check whether the DataTable fulfills the requirement to create a pie chart.
     */
    protected abstract boolean isLegal();

    /**
     * Get the DataTable object which is the table in the chart.
     *
     * @return DataTable The table which contains all the data of the chart.
     */
    public DataTable getData() { return data; }

    /**
     * Set the table of the chart.
     *
     * @param input
     *             - The new DataTable which will replace the current table of the chart.
     */
    public void setData(DataTable input) {
        DataTable temp = data;
        data = input;

        if (!isLegal()) {
            data = temp;
        }
    }
}