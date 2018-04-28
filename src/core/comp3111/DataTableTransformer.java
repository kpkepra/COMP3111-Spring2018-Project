package core.comp3111;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class DataTableTransformer {
    public static DataTable transform(CSVReader csv){
        DataTable dt = new DataTable();
        ArrayList<String> fields = csv.getFields();
        for(int i = 0; i < csv.getNumCol();i++) {

            DataColumn col = new DataColumn("String",csv.getCol(i).toArray());
            try {
                dt.addCol(fields.get(i), col);
            }catch (DataTableException dte){
                dte.printStackTrace();
            }
        }
        return dt;
    }
    //test in CSVReader main
}
