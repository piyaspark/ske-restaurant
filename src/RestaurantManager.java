/**
 * System for manager to change menu or record order.
 *
 * @author Piyaphol Wiengperm #6010545846
 */

import java.io.*;
import java.util.*;

public class RestaurantManager {
    public static ArrayList<String> listRead = new ArrayList<>();
    public static String[] menuItem;
    public static Double[] menuPrice;
    public static int orderNum;
    public static String orderNumString;

    public static String[] getMenuItems(int i, String lineSplit) { //Add menu items from file.
        menuItem[i] = lineSplit;
        return menuItem;
    }

    public static Double[] getMenuPrices(int i, String lineSplit) { //Add menu prices from file.
        menuPrice[i] = Double.parseDouble(lineSplit);
        return menuPrice;
    }

    public static PrintStream recordOrder() { //Record order for manager to manage.
        String outputfile = "src/data/salesLog.txt";
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputfile, true);
        } catch (FileNotFoundException ex) {
            System.out.println("Couldn't open output file " + outputfile);
            return null;
        }
        PrintStream writeFile = new PrintStream(out);
        return writeFile;
    }

    public static int checkLastOrderNum() throws IOException { //Check the last order number and return.
        FileReader file = new FileReader("src/data/salesLog.txt");
        BufferedReader reader = new BufferedReader(file);
        String readLine = reader.readLine();
        while (readLine != null) {
            if (readLine.startsWith("=")) {
                orderNumString = "0";
            } else {
                if (readLine.startsWith("Order")) {
                    String[] lineSplit = readLine.trim().split(": ");
                    orderNumString = lineSplit[1];
                }
            }
            readLine = reader.readLine();
        }
        orderNum = Integer.parseInt(orderNumString);
        return orderNum + 1;
    }

    public static void init() throws IOException { //Read menu from file for using.
        FileReader file = new FileReader("src/data/menu.txt");
        BufferedReader reader = new BufferedReader(file);
        String readLine = reader.readLine();
        while (readLine != null) {
            if (!readLine.startsWith("#")) listRead.add(readLine);
            readLine = reader.readLine();
        }

        menuItem = new String[listRead.size()];
        menuPrice = new Double[listRead.size()];

        for (int i = 0; i < listRead.size(); i++) {
            String line = listRead.get(i);
            String[] lineSplit = line.split(";");

            menuItem = getMenuItems(i, lineSplit[0]);
            menuPrice = getMenuPrices(i, lineSplit[1]);
        }
    }
}
