import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ContactManagementSystem {
    private final BST bst; // Binary search tree to store contacts
    private final String csvFilePath; // Path to the CSV file containing contacts

    /**
     * Constructs a contact management system with the specified CSV file path.
     * @param csvFilePath the path to the CSV file
     */
    public ContactManagementSystem(String csvFilePath) {
        this.bst = new BST();
        this.csvFilePath = csvFilePath;
    }

    /**
     * Constructs a contact management system with a default CSV file path.
     * The default CSV file path is "contacts.csv".
     */
    public ContactManagementSystem() {
        this("contacts.csv");
    }

    /**
     * Loads contact details from a CSV file and populates the binary search tree (BST).
     */
    private void loadFromCSV() {
        System.out.println("Reading contact details from file...\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contactData = line.split("\\s*,\\s*");
                if (contactData.length == 4) {
                    // Insert data into the binary search tree
                    bst.insert(contactData[0].trim(), contactData[1].trim(), contactData[2].trim(), contactData[3].trim());
                } else {
                    // Print error message for invalid data
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            // Print error message for file reading error
            System.err.println("Error reading CSV file.");
        }
    }

    /**
     * Saves contact details to a CSV file.
     */
    private void saveToCSV() {
        // Get contact data as a list
        List<String> contactData = bst.toList();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            // Write each contact data to the CSV file
            for (String data : contactData) {
                writer.write(data);
                writer.newLine();
            }
            System.out.println("Contact details saved to CSV file.");
        } catch (IOException e) {
            // Print error message for file writing error
            System.err.println("Error writing to CSV file.");
        }
    }

    /**
     * Displays a console menu and receives user input.
     */
    public void run() {
        System.out.println("Welcome to the contact management system!");
        this.loadFromCSV();
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {

            System.out.println("""
                    Please select an option:
                    1. Display contacts (Preorder)
                    2. Display contacts (Inorder)
                    3. Display contacts (Postorder)
                    4. Search for a contact
                    5. Delete a contact
                    6. Exit""");

            int option;
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                option = -1;
                scanner.next(); // Consume invalid input
            }
            scanner.nextLine();
            switch (option) {
                case 1:
                    // Handle option 1 (Display contacts - Preorder)
                    System.out.println("Displaying contacts (Preorder):");
                    bst.printPreOrder();
                    break;
                case 2:
                    // Handle option 2 (Display contacts - Inorder)
                    System.out.println("Displaying contacts (Inorder):");
                    bst.printInOrder();
                    break;
                case 3:
                    // Handle option 3 (Display contacts - Postorder)
                    System.out.println("Displaying contacts (Postorder):");
                    bst.printPostOrder();
                    break;
                case 4:
                    // Handle option 4 (Search for a contact)
                    System.out.println("Enter the name of the contact you want to search for: ");
                    bst.search(scanner.nextLine());
                    break;
                case 5:
                    // Handle option 5 (Delete a contact)
                    System.out.println("Enter the last name of the contact you want to delete: ");
                    bst.delete(scanner.nextLine());
                    break;
                case 6:
                    exit = true; // Set exit to true to terminate the loop
                    break;
                default:
                    System.out.println("Please enter a valid number (1-6).");
            }
            System.out.println();
        }
        System.out.println("Exiting and saving contact details to file...");
        this.saveToCSV();
        scanner.close(); // Close the scanner after use
    }


    public static void main(String[] args) {
        ContactManagementSystem cms = new ContactManagementSystem();
        cms.run();
    }
}
