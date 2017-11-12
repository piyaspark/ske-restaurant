/**
 * System for manager to change menu or record order.
 *
 * @author Piyaphol Wiengperm #6010545846
 */

import java.io.*;
import java.util.*;

public class RestaurantManager {
    private static String[] menuItem;
    private static Double[] menuPrice;
    private static String MENU_FILE = "src/data/menu.txt";
    private static String RECORD_FILE = "src/data/salesLog.txt";
    public static int orderNum;
    public static String orderNumString;


    /**
     * Add menu items from file to array.
     *
     */
    public static void addMenuItems(int i, String lineSplit) {
        menuItem[i] = lineSplit;

    }

    /**
     * Add menu prices from file to array.
     *
     */
    public static void addMenuPrices(int i, String lineSplit) { //Add menu prices from file.
        menuPrice[i] = Double.parseDouble(lineSplit);

    }

    /**
     * @return array of menu items.
     *
     */
    public static String[] getMenuItems() {
        return menuItem;
    }

    /**
     * @return array of menu prices.
     *
     */
    public static Double[] getMenuPrices() {
        return menuPrice;
    }

    /**
     * Record order for manager to manage.
     * @return writeFile is class PrintStream.
     *
     */
    public static PrintStream recordOrder() {
        String outputfile = RECORD_FILE;
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

    /**
     * Get the last order number and return it.
     * @return  the last order number
     *
     */
    public static int checkLastOrderNum() throws IOException { //Check the last order number and return.
        FileReader file = new FileReader(RECORD_FILE);
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

    /**
     * Read menu from file for using.
     *
     */
    public static void init() throws IOException { //Read menu from file for using.
        FileReader file = new FileReader(MENU_FILE);
        BufferedReader reader = new BufferedReader(file);
        String readLine = reader.readLine();
        ArrayList<String> listRead = new ArrayList<>();
        while (readLine != null) {
            if (!readLine.startsWith("#")) listRead.add(readLine);
            readLine = reader.readLine();
        }

        menuItem = new String[listRead.size()];
        menuPrice = new Double[listRead.size()];

        for (int i = 0; i < listRead.size(); i++) {
            String line = listRead.get(i);
            String[] lineSplit = line.split(";");
            addMenuItems(i, lineSplit[0]);
            addMenuPrices(i, lineSplit[1]);
        }
    }
}
