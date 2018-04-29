package core.comp3111;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class DataTableTransformer {
    static CSVReader  tcsv = new CSVReader();
    public static DataTable transform(CSVReader csv){
        tcsv = csv;
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
        dt.printTable();
        return toNumericCol(dt);
    }


    //test in CSVReader main
    private static DataTable toNumericCol(DataTable dt){
        for(DataColumn col : dt.getDc().values()){
            if(col.isNumericCol()){
                col.stringToNumericType();
            }
        }
        return dt;
    }
}
