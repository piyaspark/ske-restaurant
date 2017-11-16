import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * User interface for a menu.txt and ordering system, using console interface.
 *
 * @author Piyaphol Wiengperm #6010545846
 */

public class MySkeRestaurant {
    private static Scanner inp = new Scanner(System.in);
    private static String[] menuItems;
    private static Double[] menuPrices;

    /**
     * @return input in string.
     */
    public static String getString(String prompt) {
        System.out.print(prompt);
        return inp.nextLine();
    }

    /**
     * @return input in integer.
     */
    public static int getInt(String prompt) {
        System.out.print(prompt);
        return inp.nextInt();
    }

    /**
     * Check if an input is a number.
     *
     * @return boolean for condition.
     */
    public static boolean isNumber(String choose) {
        try {
            Integer.parseInt(choose);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Add the menu that user selected with quantity of the menu.
     */
    public static void addToOrder(int order, int[] orders) {
        String QtyInput = getString("Enter Quantity: ");
        if (isNumber(QtyInput)) {
            int Qty = Integer.parseInt(QtyInput);
            orders[order - 1] += Qty;
            System.out.println("[ Added to order! ]");
        } else {
            System.out.println("[ Invalid choice! ]");
        }
    }

    /**
     * Compute total price of every menu in orders.
     *
     * @return total price in double.
     */
    public static double computeTotal(int[] orders) {
        double totalPrice = 0;
        for (int i = 0; i < menuItems.length; i++) {
            totalPrice += orders[i] * menuPrices[i];
        }
        return totalPrice;
    }

    /**
     * Print orders with table format.
     */
    public static void printOrder(int[] orders, double totalPrice) {
        System.out.print("\n+------------ Menu ------------+-- Qty --+-- Price --+\n");

        for (int i = 0; i < menuItems.length; i++) {
            if (orders[i] != 0)
                System.out.printf("| %-29s|  %3d    | %9.2f |\n", menuItems[i], orders[i], menuPrices[i] * orders[i]);
        }
        System.out.printf("+------------------------------+---------+-----------+\n");
        System.out.printf("| Total                                  | %9.2f |\n", totalPrice);
        System.out.printf("+----------------------------------------+-----------+\n\n");

    }

    /**
     * Edit quantity of the order that user selected.
     * it won't do if quantity that user input is zero.
     *
     * @return array of orders.
     */
    public static int[] editOrder(int[] orders, int choice) {
        orders[choice - 1] = 0;
        int change = getInt("Enter Quantity: ");
        if (change == 0) {
            System.out.println("[ Invalid amount(not zero) ]");
            return orders;
        } else {
            orders[choice - 1] += change;
            System.out.println("[ Your order already changed ]");
        }
        return orders;
    }

    /**
     * Cancel the order that user selected.
     *
     * @return array of orders.
     */
    public static int[] cancelOrder(int[] orders, int choice) {
        orders[choice - 1] = 0;
        return orders;
    }

    /**
     * Calculate VAT(Value added tax) from total.
     *
     * @return VAT in double.
     */
    public static double VAT(double totalPrice) {
        double vat = 0;
        vat += (totalPrice / 100) * 5;
        return vat;
    }

    /**
     * Print receipt for user's payment with order number.
     *
     */
    public static void receipt(int[] orders, double totalPrice, int orderNum) {
        System.out.println("\n		        # SKE Steak House #	    		\n");
        System.out.printf("		             Order: %-3d  	    		\n", orderNum);
        printOrder(orders, totalPrice);
        double vat = VAT(totalPrice);
        System.out.printf("   VAT (included 5 percent)                %9.2f\n", vat);
        System.out.printf("   Payment                                 %9.2f\n", totalPrice + vat);
        RestaurantManager.recordOrder(orderNum, orders, totalPrice, vat);
        for (int i = 0; i < orders.length; i++) orders[i] = 0;
    }

    /**
     * Show all menu that user can select.
     *
     */
    public static void showMenu() {
        System.out.printf("\n--------Welcome to SKE Steak House---------\n");
        for (int i = 0; i < menuItems.length; i++) {
            System.out.printf("%d. %-30s%9.2f\n", i + 1, menuItems[i],
                    menuPrices[i]);
        }
        System.out.println("\n");
        System.out.println("[e] Edit order");
        System.out.println("[p] Print order");
        System.out.println("[c] Review order and Checkout");
        System.out.println("[x] Cancel order");
        System.out.println("[q] Exit the program");
        System.out.println("[?] Show menu list again\n");

    }

    /**
     * Program system.
     *
     */
    public static void runSkeRestaurant() throws IOException {
        menuItems = RestaurantManager.getMenuItems();
        menuPrices = RestaurantManager.getMenuPrices();
        showMenu();
        int[] orders = new int[menuItems.length];
        while (true) {
            String choose = getString("Enter your choice: ");
            switch (choose) {
                case "e":
                    int choice = getInt("Enter order you want to change: ");
                    editOrder(orders, choice);
                    inp.nextLine();
                    break;
                case "p":
                    double totalPrice = computeTotal(orders);
                    System.out.println("\nYour current orders...");
                    printOrder(orders, totalPrice);
                    break;
                case "c":
                    double payment = computeTotal(orders);
                    int orderNum = RestaurantManager.checkLastOrderNum();
                    receipt(orders, payment, orderNum);
                    System.out.println("\n   +================= Thank you ==================+\n");
                    continue;
                case "x":
                    int cancelOrder = getInt("Enter order you want to cancel: ");
                    cancelOrder(orders, cancelOrder);
                    System.out.println("[ Your order already canceled ]");
                    inp.nextLine();
                    break;
                case "q":
                    System.out.println("Bye!");
                    System.exit(1);
                case "?":
                    showMenu();
                    break;
                default:
                    if (isNumber(choose)) {
                        int order = Integer.parseInt(choose);
                        if (order <= menuItems.length) {
                            addToOrder(order, orders);
                        } else System.out.println("[ Invalid choice! ]");
                    } else {
                        System.out.println("[ Invalid choice! ]");
                    }
                    break;
            }


        }
    }


    public static void main(String[] args) throws IOException {
        RestaurantManager.init();
        runSkeRestaurant();

    }
}
