package core.comp3111;

import java.io.*;
public class MyFileExtenstion {
    private CorgiObj c = new CorgiObj();

    public boolean loadCorgi( String fileName){
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            c = (CorgiObj) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        catch (ClassNotFoundException c) {
            System.out.println("class not found!");
            c.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean saveCorgi(){
        //TODO
        return true;
    }

    /*for testing only*/
    static  TestObj t = new TestObj();
    public static void main(String args[]) {
        try {
            //write an object
            FileOutputStream fileOut =
                    new FileOutputStream("extensionTest1.corgi");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(t);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in extensionTest1.corgi");

            //load an object
            FileInputStream fileIn = new FileInputStream("extensionTest1.corgi");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            t = (TestObj) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            System.out.println("class not found!");
            c.printStackTrace();
        }
        System.out.println(t.num);
        t.f();
    }

}
class TestObj implements Serializable{

    int num;
    private int privateNum;
    TestObj(){
        num = 123;
        privateNum = 456;
    }
    public void f(){
        System.out.print(privateNum);
    }
}

class CorgiObj implements Serializable{

}
