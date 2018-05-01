package core.comp3111;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {
        CSVReader csv = new CSVReader();
        CSVReader csv_Test1 = new CSVReader("csvTest1.csv");
        CSVReader csv_Test2 = new CSVReader("csvTest2.csv");
        CSVReader csv_Test3 = new CSVReader("notExisting.csv");
        CSVReader csv_Test4 = new CSVReader("csvTest4.csv");
        CSVReader csv_Test5 = new CSVReader("csvTest5.csv");

        @Test
        public void getNumCol() {
            Assertions.assertEquals(csv.getNumCol(), 0);
            csv_Test1.readALL(0);
            Assertions.assertEquals(csv_Test1.getNumCol(),3);
        }

        @Test
        public void getData() {
            csv_Test1.readALL(1);
            csv_Test1.readALL(2);
            csv_Test1.readALL(3);
            csv_Test3.readALL(0);
            csv_Test4.readALL(0);
            csv_Test5.readALL(1);
            csv_Test5.readALL(2);
            assert (csv.getData() == null);
        }

        @Test
        public void getFields() {
            ArrayList<String> fields = new ArrayList<>();
//              csv_Test1.readField();
//            String [] fields_ = {"Name","ID","ITSC"};
//            fields.addAll(Arrays.asList(fields_));
//            Assertions.assertEquals(csv_Test1.getFields(),fields);
            csv_Test2.readField();
            csv_Test3.readField();
            assert (csv.getFields() == null);
        }

        @Test
        public void getCol() {
            ArrayList<String> names = new ArrayList<>();
            String [] names_ = {"Adrian","Kepra","Judy","Jenny"};
            names.addAll(Arrays.asList(names_));
            csv_Test1.readALL(0);
            Assertions.assertEquals(csv_Test1.getCol(0),names);

        }

    }
