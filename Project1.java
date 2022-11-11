
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.NoSuchElementException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
import java.nio.charset.StandardCharsets;

public class Project1 {

    static FactoryImpl a = new FactoryImpl();
    private static String pnf = "Product not found.";
    private static String iob = "Index out of bounds.";
    private static String fie = "Factory is empty.";

    public static void main(String[] args) throws IOException {
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            File file = new File(args[1]);
            FileWriter myWriter = new FileWriter(file.getAbsoluteFile(), StandardCharsets.UTF_16);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                FunctionChecker(data, myWriter);
            }

            myReader.close();
            myWriter.close();

        } catch (FileNotFoundException e) {
        }

    }

    private static void FunctionChecker(String data, FileWriter fw) throws IOException {

        String[] arrData = data.split(" ");

        if (arrData[0].equals("A")) {
            int index = Integer.parseInt(arrData[1]);
            int id = Integer.parseInt(arrData[2]);
            int value = Integer.parseInt(arrData[3]);
            Product temp = new Product(id, value);
            try {
                a.add(index, temp);
            } catch (IndexOutOfBoundsException e) {
                fw.write(iob);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("U")) {
            int index = Integer.parseInt(arrData[1]);
            int value = Integer.parseInt(arrData[2]);
            try {
                fw.write(a.update(index, value).toString());
                fw.write("\n");
            } catch (NoSuchElementException e) {
                fw.write(pnf);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("G")) {
            int index = Integer.parseInt(arrData[1]);
            try {

                fw.write((a.get(index).toString())); // write the output product
                fw.write("\n");
            } catch (IndexOutOfBoundsException e) {
                fw.write(iob);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("FD")) {
            fw.write(Integer.toString(a.filterDuplicates())); // converted to string??
            fw.write("\n");
        }
        if (arrData[0].equals("RL")) {
            try {
                fw.write(a.removeLast().toString());
                fw.write("\n");
            } catch (NoSuchElementException e) {
                fw.write(fie);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("RF")) {
            try {
                fw.write(a.removeFirst().toString());
                fw.write("\n");
            } catch (NoSuchElementException e) {
                fw.write(fie);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("AL")) {
            int id = Integer.parseInt(arrData[1]);
            int value = Integer.parseInt(arrData[2]);
            Product temp = new Product(id, value);

            a.addLast(temp);
        }
        if (arrData[0].equals("P")) {
            fw.write(a.printAll());
            fw.write("\n");

        }
        if (arrData[0].equals("R")) {
            a.reverse();
            fw.write(a.printAll());
            fw.write("\n");

        }
        if (arrData[0].equals("F")) {
            int id = Integer.parseInt(arrData[1]);
            try {
                fw.write(a.find(id).toString()); // trying to convert return Product to string version
                fw.write("\n");
            } catch (NoSuchElementException e) {
                fw.write(pnf);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("RI")) {
            int index = Integer.parseInt(arrData[1]);
            try {
                fw.write(a.removeIndex(index).toString());
                fw.write("\n");
            } catch (IndexOutOfBoundsException e) {
                fw.write(iob);
                fw.write("\n");
            }

        }
        if (arrData[0].equals("AF")) {
            int id = Integer.parseInt(arrData[1]);
            int value = Integer.parseInt(arrData[2]);
            Product temp = new Product(id, value);
            a.addFirst(temp);
        }
        if (arrData[0].equals("RP")) {
            int value = Integer.parseInt(arrData[1]);
            try {
                fw.write(a.removeProduct(value).toString());
                fw.write("\n");
            } catch (NoSuchElementException e) {
                fw.write(pnf);
                fw.write("\n");
            }

        }

    }

    public void printLinkedList() {
        Holder temp = a.getFirst();
        if (temp != null) {
            System.out.println(temp.toString());
            temp = temp.getNextHolder();
        }

    }

}
