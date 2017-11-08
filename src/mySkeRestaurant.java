import java.io.IOException;
import java.util.*;

/**
 * User interface for a menu.txt and ordering system, using console interface.
 *
 * @author Piyaphol Wiengperm #6010545846
 */

public class mySkeRestaurant {
    public static Scanner inp = new Scanner(System.in);

    public static String getString(String prompt) { //Return String.
        System.out.print(prompt);
        return inp.nextLine();
    }

    public static int getInt(String prompt) { //Return Integer.
        System.out.print(prompt);
        return inp.nextInt();
    }

    public static boolean isNumber(String choose) { //Check if an input is a number.
        try {
            Integer.parseInt(choose);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static void addToOrder(int order, int[] orders) { //Add the menu that user selected with quantity of the menu.
        int QtyInput = getInt("Enter Quantity: ");
        orders[order - 1] += QtyInput;
    }

    public static double computeTotal(int[] orders) { //Compute total price of every menu in orders.
        double totalPrice = 0;
        for (int i = 0; i < RestaurantManager.menuItem.length; i++) {
            totalPrice += orders[i] * RestaurantManager.menuPrice[i];
        }
        return totalPrice;
    }

    public static void printOrder(int[] orders, double totalPrice) { //Print orders with table format.
        System.out.print("+------------ Menu ------------+-- Qty --+-- Price --+\n");

        for (int i = 0; i < RestaurantManager.menuItem.length; i++) {
            if (orders[i] != 0)
                System.out.printf("| %-29s|  %3d    | %9.2f |\n", RestaurantManager.menuItem[i], orders[i], RestaurantManager.menuPrice[i] * orders[i]);
        }
        System.out.printf("+------------------------------+---------+-----------+\n");
        System.out.printf("| Total                                  | %9.2f |\n", totalPrice);
        System.out.printf("+----------------------------------------+-----------+\n");

    }

    public static int[] editOrder(int[] orders, int choice) { //Edit quantity of the order that user selected.
        orders[choice - 1] = 0;
        int change = getInt("Enter Quantity: ");
        if (change == 0) {
            System.out.println("[ Invalid amount(not zero) ]");
            return orders;
        } else orders[choice - 1] += change;
        return orders;
    }

    public static int[] cancelOrder(int[] orders, int choice) { //Cancel the order that user selected.
        orders[choice - 1] = 0;
        return orders;
    }

    public static double VAT(double totalPrice) { //Calculate VAT.
        double vat = 0;
        vat += (totalPrice / 100) * 5;
        return vat;
    }

    public static void Receipt(int[] orders, double totalPrice) { //Print receipt for user's payment.
        System.out.println("\n		        # SKE Steak House #	    		\n");
        printOrder(orders, totalPrice);
        double vat = VAT(totalPrice);
        System.out.printf("   VAT (included 5 percent)                %9.2f     \n", vat);
        System.out.printf("   Payment                                 %9.2f     \n", totalPrice + vat);

    }

    public static void showMenu() { //Show all menu that user can select.
        System.out.printf("\n--------Welcome to SKE Steak House---------\n");
        for (int i = 0; i < RestaurantManager.menuItem.length; i++) {
            System.out.printf("%d. %-30s%9.2f\n", i + 1, RestaurantManager.menuItem[i],
                    RestaurantManager.menuPrice[i]);
        }
        System.out.println("\n");
        System.out.println("[e] Edit order");
        System.out.println("[p] Print order");
        System.out.println("[c] Review order and Checkout");
        System.out.println("[x] Cancel order");
    }

    public static void runSkeRestaurant() { //Program system.
        showMenu();
        int[] orders = new int[RestaurantManager.menuItem.length];
        while (true) {
            String choose = getString("Enter your choice: ");
            switch (choose) {
                case "e":
                    int choice = getInt("Enter order you want to change: ");
                    editOrder(orders, choice);
                    System.out.println("[ Your order already changed ]");
                    inp.nextLine();
                    break;
                case "p":
                    double totalPrice = computeTotal(orders);
                    printOrder(orders, totalPrice);
                    break;
                case "c":
                    double payment = computeTotal(orders);
                    Receipt(orders, payment);
                    System.out.println("\n   +================= Thank you ==================+");
                    System.exit(1);
                case "x":
                    int cancelOrder = getInt("Enter order you want to cancel: ");
                    cancelOrder(orders, cancelOrder);
                    System.out.println("[ Your order already canceled ]");
                    inp.nextLine();
                    break;
                default:
                    if (isNumber(choose)) {
                        int order = Integer.parseInt(choose);
                        if (order <= RestaurantManager.menuItem.length) {
                            addToOrder(order, orders);
                            System.out.println("[ Added to order! ]");
                            inp.nextLine();
                        } else System.out.println("[ Invalid choice! ]");
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
