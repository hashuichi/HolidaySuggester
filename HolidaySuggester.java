import java.util.Scanner;
import java.io.*;

public class HolidaySuggester {
    public static void main(String[] args) {
        File file = new File("suggestions.txt");
        TreeNode root = readFromFile(file);
        Scanner kb = new Scanner(System.in);
        System.out.println("Welcome to the Post-COVID Holiday Destination Suggester.");
        playAgain(root, kb, file);
    }

    //While loop to check if user wants to play again
    public static void playAgain(TreeNode root, Scanner kb, File file) {
        boolean playAgain = true;
        while (playAgain) {
            playGame(root, kb);
            if ((!askYesNo("Do you want to play again?", kb))) {
                playAgain = false;
                if (askYesNo("Do you want to save the current tree?", kb)) {
                    writeToFile(root, file);
                }
            }
        }
    }

    //Main game loop
    public static void playGame(TreeNode root, Scanner kb) {
        TreeNode current = root;
        do {
            boolean ans = askYesNo(current.toString(), kb);
            if (ans) {
                current = current.getLeft();
                if (current.isLeaf()) {
                    System.out.println("Perhaps you would like to go to " +current.toString());
                    AddNewQuestion(root, current, kb);
                    break;
                }
            } else {
                current = current.getRight();
                if (current.isLeaf()) {
                    System.out.println("Perhaps you would like to go to " +current.toString());
                    AddNewQuestion(root, current, kb);
                    break;
                }
            }
        } while (!current.isLeaf());
    }

    public static boolean askYesNo(String question, Scanner kb) {
        System.out.println(question + " [y/n]");
        char ans = kb.nextLine().charAt(0);
        return ans == 'y' ? true : false;
    }

    public static void AddNewQuestion(TreeNode root, TreeNode current, Scanner kb) {
        if (!(askYesNo("Is this satisfactory?", kb))) {
            System.out.println("What would you prefer instead?");
            TreeNode left = new TreeNode(kb.nextLine());
            System.out.println("Tell me a question that distinguishes "+current.toString()+" from "+left);
            String question = kb.nextLine();
            TreeNode newNode = new TreeNode(question, left, current);
            root.addNode(newNode);
        }
    }

    public static void writeToFile(TreeNode root, File filename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            root.saveTree(out);
            out.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static TreeNode readFromFile(File filename) {
        TreeNode root = new TreeNode(null);
        try (BufferedReader in = new BufferedReader(new FileReader(filename));) {
            root = constuctTree(in, root);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (filename.length() == 0) {
                root = new TreeNode("Do you like cold weather?", 
                                new TreeNode("Are you a keen hiker?", 
                                    new TreeNode("the Scottish highlands"), 
                                    new TreeNode("Moscow")), 
                                new TreeNode("Goa"));
            }
        }
        return root;
    }

    //Recursive algorithm that constrcuts the tree from a file
    public static TreeNode constuctTree(BufferedReader in, TreeNode root) {
        TreeNode current = root;
        try {
            String line = in.readLine();
            if (line != null) {
                if (line.contains("sug")) {
                    current = new TreeNode(line.replace("sug ",""));
                } else {
                    current = new TreeNode(line, constuctTree(in, current), constuctTree(in, current));
                }
            }
        } catch(IOException e) {
            System.err.println(e);
        }
        return current;
    }
}