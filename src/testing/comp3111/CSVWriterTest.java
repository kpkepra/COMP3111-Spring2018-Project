package testing.comp3111;

import core.comp3111.CSVWriter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CSVWriterTest {
    @Test
    public void writeAndClose() {
        try
        {
            CSVWriter cw = new CSVWriter("resources/csvTest1_w.csv");
            ArrayList<String> words = new ArrayList<>();
            words.add("Name");
            words.add("Judy");
            words.add("Kepra");
            words.add("Adrian");
            cw.writeArray(words,1);
            cw.close();
        }
        catch(FileNotFoundException fe){
            fe.printStackTrace();
        }

    }
}