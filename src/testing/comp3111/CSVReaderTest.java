package testing.comp3111;

import core.comp3111.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {
        CSVReader csv = new CSVReader();
        CSVReader csv_Test1 = new CSVReader("csvTest1.csv");

        @Test
        public void getNumCol() {
            Assertions.assertEquals(csv.getNumCol(), 0);
            csv_Test1.readALL(0);
            Assertions.assertEquals(csv_Test1.getNumCol(),3);
        }

        @Test
        public void getData() {
            assert (csv.getData() == null);
        }

        @Test
        public void getFields() {
            assert (csv.getFields() == null);
        }

        @Test
        public void getCol() {
            ArrayList<String> names = new ArrayList<>();
            String [] names_ = {"Adrian","Kepra","Judy","Jenny"};
            names.addAll(Arrays.asList(names_));
            csv_Test1.readALL(0);
            ArrayList<String> s = csv_Test1.getCol(0);
            Assertions.assertEquals(csv_Test1.getCol(0),names);

        }
    }
