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

    public static String[] getMenuItems(int i, String lineSplit) { //Add menu items from file.
        menuItem[i] = lineSplit;
        return menuItem;
    }

    public static Double[] getMenuPrices(int i, String lineSplit) { //Add menu prices from file.
        menuPrice[i] = Double.parseDouble(lineSplit);
        return menuPrice;
    }

    public static void recordOrder() { //Record order for manager to manage.
        String outputfile = "salesLog.txt";
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputfile);
        } catch (FileNotFoundException ex) {
            System.out.println("Couldn't open output file " + outputfile);
            return;
        }

        PrintStream writeFile = new PrintStream(out);
        writeFile.close();
    }

    public static void init() throws IOException { //Read menu from file for using.
        FileReader file = new FileReader("data/menu.txt");
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
