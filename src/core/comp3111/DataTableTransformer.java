package core.comp3111;

import java.util.ArrayList;
import java.util.Map;



/**
 * DataTableTransformer - DataTableTransformer is a class transform between
 * ArrayList and DataTable
 * @author Wu Yun Ju
 */
public class DataTableTransformer {
    static CSVReader tcsv = new CSVReader();


    /**
     * Take in the CSVReader with the ArrayList data and fields and transform the
     * Two ArrayList into DataTable
     * @param  csv
     *      -a CSVReader that has already called csv.readFields() and csv.ReadAll()
     *      and store the .csv file content in its data and fields variables.
     * @return the transformed dataTable
     */
    public static DataTable transform(CSVReader csv) throws DataTableException{
        tcsv = csv;
        DataTable dt = new DataTable();
        ArrayList<String> fields = csv.getFields();
        for(int i = 0; i < csv.getNumCol();i++) {
            DataColumn col = new DataColumn(DataType.TYPE_STRING,csv.getCol(i).toArray());
            dt.addCol(fields.get(i), col);

        }
        return toNumericCol(dt);
    }


    /**
     * Take the DataTable and transform it into ArrayList of String
     * @param  dt
     *      dataTable that needs to be transformed into String ArrayList
     * @return the transformed dataTable
     */
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

    /**
     * Take the DataTable and transform all the data in numeric col to Number Type
     * @param  dt
     *      dataTable that stored all data in String
     * @return the transformed dataTable
     */
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
