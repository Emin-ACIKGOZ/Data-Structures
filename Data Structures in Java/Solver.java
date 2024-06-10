import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
// Made by Emin Salih Açıkgöz. Student ID: 22050111032
/**
 * Represents a system that allows us to solve operations from a txt file.
 */
public class Solver {
    private Stack<Character> Operators;//Operator Stack
    private Stack<Double> Operands; //Operand Stack

    /**
     * Constructor for the Solver.
     * Initializes operand and operator stacks.
     */
    public Solver() {
        this.Operands = new Stack<>();
        this.Operators = new Stack<>();
    }

    /**
     * Checks if the file exists, if it is not found the file is created.
     *
     * @param filePath The path of the file to be checked.
     */
    private void checkFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("No file of path: " + filePath + " was found.");
                System.out.println("Creating file...");
            }
        } catch (IOException e) {
            System.out.println("Error: an IO exception has occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Formats a line of text to make processing it easier.
     *
     * @param line The line of text to be formatted.
     */
    private String formatLine(String line) {
        // Inserts spaces before and after operators to allow splitting
        line = line.replaceAll("([+\\-*/])", " $1 ").trim();

        // Handles edge case where negative numbers at the beginning (i.e. -10 + 5 or -100)
        if (line.startsWith("-")) {
            line = "0 " + line;
        }

        // Removes extra whitespace to make splitting possible and prevent some edge cases
        line = line.replaceAll("\\s+", " ").trim();

        return line;
    }

    /**
     * Returns true if the string is a valid Operator, false otherwise.
     *
     * @param s The string to be examined.
     */
    private static boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }

    /**
     * Returns true if the string is a valid Operand, false otherwise.
     *
     * @param s The string to be examined.
     */
    private static boolean isNumber(String s) {
        boolean hasDecimalPoint = false;

        if (s.isEmpty()) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                continue;
            }

            if (c == '.') {
                if (hasDecimalPoint) {
                    return false;
                }
                hasDecimalPoint = true;
                continue;
            }

            return false;
        }

        return true;
    }

    /**
     * Returns the given Operator's precedence.
     *
     * @param a The Operator to be examined.
     */
    private static int getPrecedence(char a) {
        return switch (a) {
            case '#' -> 0;
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    /**
     * Returns the result of the given operands and operator.
     *
     * @param num1 The first number.
     * @param num2 The second number.
     * @param sign The operator that dictates the operation to be performed.
     */
    private static double solveOperation(double num1, double num2, char sign) {
        return switch (sign) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            default -> Double.NaN;
        };
    }

    /**
     * Calculates the value of the equation provided in the line by using the two stacks the class contains.
     * Using the logic of stacks the numbers are mounted to the number stack and the operators to the operator stack.
     * The precedence of the operators is checked when attempting to perform a calculation
     * in order to allow the operations with a higher precedence to be performed first.
     * when we finish reading the line we do the remaining processes in order and print the resulting value.
     *
     * @param line The line to be solved.
     */
    private void solveLine(String line) {
        String[] data = line.split(" ");
        Operators.push('#');
        boolean expectingOperator = false;

        for (String s : data) {
            if (isNumber(s)) {

                if (expectingOperator) { //True if two numbers occur back to back, which is an invalid state.
                    System.out.println("Error: Expected an operator token, received a number.");
                    return;
                }
                Operands.push(Double.parseDouble(s));
                expectingOperator = true; // Set to true as we expect an operator to occur next.

            } else if (isOperator(s)) {
                char symbol = s.charAt(0);

                if (!expectingOperator) { // True if two operators occur back to back, which is an invalid state.
                    System.out.println("Error: Expected a number token, received an operator.");
                    return;
                }

                //Solve popped operations if they have a higher or equal precedence to the symbol.
                while (!Operators.isEmpty() && getPrecedence(symbol) <= getPrecedence(Operators.peek())) {
                    char op = Operators.pop();
                    double num2 = Operands.pop();
                    double num1 = Operands.pop();
                    double result = solveOperation(num1, num2, op);
                    Operands.push(result);
                }

                Operators.push(symbol);
                expectingOperator = false; // Reset the flag as a number is expected.

            } else {
                //This block is entered if the string observed is not a number nor is an operator.
                System.out.println("Error: Received token is neither an operator nor a number.");
                return;
            }
        }


        //The operator stack is never empty here, but isEmpty is added for safety.
        if (Operators.isEmpty()) {
            System.out.println("Error: Expected operators, received empty stack.");
            return;
        }

        //Process the remaining operators under the dummy operator is encountered.
        while (Operators.peek() != '#') {
            char op = Operators.pop();

            try {//Handles edge cases where there are not enough numbers to match to operators (Invalid syntax)
                double num2 = Operands.pop();
                double num1 = Operands.pop();
                double result = solveOperation(num1, num2, op);
                Operands.push(result);
            } catch (NullPointerException e) {
                System.out.println("Error: Input contains extra operators or is missing numbers.");
                return;
            }
        }
        Operators.pop();//Removes the dummy Operator
        System.out.println(Operands.pop()); //Displays the result
    }

    /**
     * Clears the stacks to ensure no garbage data remains for the next operation.
     */
    private void clearStacks() {
        this.Operands = new Stack<>();
        this.Operators = new Stack<>();
    }

    /**
     * A test method that allows the caller to examine the output of specific expressions
     *
     * @param expression The expression to be tested.
     */
    public static void testExpression(String expression) {
        Solver solver = new Solver();
        System.out.print("Expression: " + expression + " Output: ");
        solver.solveLine(solver.formatLine(expression));
        System.out.println(" ");
    }

    /**
     * Reads each line of the specified file and displays the output of each line.
     *
     * @param filePath The path of the target file.
     */
    private void processFile(String filePath) {
        File file = new File(filePath);

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = formatLine(reader.nextLine()); //Reads the next line and formats it.
                solveLine(line);
                clearStacks(); //To ensure that we do not have remaining data from the last line.
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Processes the lines of a given file.
     *
     * @param filePath The path of the file.
     */
    public void process(String filePath) {
        checkFile(filePath);
        processFile(filePath);
    }

    /**
     * Processes the lines of a default file.
     */
    public void process() {
        process("infix.txt");
    }

    /**
     * Demonstrates how to use this class.
     *
     * @param args Arguments.
     */
    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.process();
        Solver.testExpression("-1");

    }
}
