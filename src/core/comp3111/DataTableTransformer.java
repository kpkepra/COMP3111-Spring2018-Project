package core.comp3111;

import java.util.ArrayList;
import java.util.Map;

public class DataTableTransformer {
    static CSVReader tcsv = new CSVReader();
    public static DataTable transform(CSVReader csv){
        tcsv = csv;
        DataTable dt = new DataTable();
        ArrayList<String> fields = csv.getFields();
        for(int i = 0; i < csv.getNumCol();i++) {
            DataColumn col = new DataColumn(DataType.TYPE_STRING,csv.getCol(i).toArray());
            try {
                dt.addCol(fields.get(i), col);
            }catch (DataTableException dte){
                System.out.println("Some row has wrong number of columns");
            }
        }
        dt.printTable();
        return toNumericCol(dt);
    }

    public static ArrayList<String> reverseTransform(DataTable dt){
        ArrayList<String> data = new ArrayList<>();
        Map dc = dt.getDc();
        for(Object key: dc.keySet()){
            data.add((String)key);
        }
        for(Object col: dc.values()){
            Object[] vals = ((DataColumn)col).getData();
            for(Object val:vals){
                if(val instanceof String)data.add((String) val);
                else{
                    data.add(val.toString());
                }
            }
        }
        return data;
    }

    private static DataTable toNumericCol(DataTable dt){
        for(DataColumn col : dt.getDc().values()){
            if(col.isNumericCol()){
                col.stringToNumericType();
            }
        }
        return dt;
    }

//    public static void main(String[] args){
//        CSVReader csv = new CSVReader("csvTest1.csv");
//        csv.readField();
//        csv.readALL(0);
//        DataTable dt = DataTableTransformer.transform(csv);
//        System.out.print(DataTableTransformer.reverseTransform(dt));
//    }
}
