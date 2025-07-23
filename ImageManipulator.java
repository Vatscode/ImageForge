
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Image processing program with crop, mirror, and greyscale operations.
 *
 * @author Vats Upadhyay A00454163
 */
public class ImageManipulator {

    /**
     * Main method/runner for the image manipulator program.
     *
     */
    public static void main(String[] args) throws IOException {
        Scanner kbd = new Scanner(System.in);
        BufferedImage pic = null;

        // Get image file from user with error handling
        while (pic == null) {
            System.out.print("Enter the file path of the image: ");
            String path = kbd.nextLine();
            try {
                pic = ImageIO.read(new File(path));
                if (pic == null) {
                    System.out.println("Could not read image. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("File not found or not a valid image. Please try again.");
            }
        }

        // simple menu loop which runs until the user quits
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an operation:");
            System.out.println("0: Display image");
            System.out.println("1: Crop");
            System.out.println("2: Greyscale");
            System.out.println("3: Flip");
            System.out.println("4: Quit");
            System.out.print("Enter your choice: ");

            String choice = kbd.nextLine();

            switch (choice) { //switch statement to handle the different cases
                case "0":
                    displayImage(pic);
                    break;
                case "1":
                    System.out.print("Enter x%: ");
                    int x = Integer.parseInt(kbd.nextLine());
                    System.out.print("Enter y%: ");
                    int y = Integer.parseInt(kbd.nextLine());
                    BufferedImage cropped = crop(x, y, pic);
                    displayImage(cropped);
                    break;
                case "2":
                    BufferedImage grey = greyScale(pic);
                    displayImage(grey);
                    break;
                case "3":
                    System.out.print("Enter 1 for vertical, 2 for horizontal: ");
                    String flipChoice = kbd.nextLine();
                    if (flipChoice.equals("1")) { //simple if else statement
                        BufferedImage vertFlip = mirrorVertical(pic);
                        displayImage(vertFlip);
                    } else if (flipChoice.equals("2")) {
                        BufferedImage horizFlip = mirrorHorizontal(pic);
                        displayImage(horizFlip);
                    } else {
                        System.out.println("Invalid flip option.");
                    }
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    /**
     * Displays the given BufferedImage in a new window.
     *
     */
    public static void displayImage(BufferedImage pic) {
        JFrame frame = new JFrame();
        frame.add(new JLabel(new ImageIcon(pic)));
        frame.getContentPane();
        frame.setVisible(true);
        frame.setSize(pic.getWidth(), pic.getHeight());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Crops the image to specified percentage .
     *
     */
    public static BufferedImage crop(int x, int y, BufferedImage pic) {

        int newWidth = pic.getWidth() * x / 100;
        int newHeight = pic.getHeight() * y / 100;

        // Create new BufferedImage with new dimensions
        BufferedImage cropped = new BufferedImage(newWidth, newHeight, pic.getType());

        // Copy pixels from original to cropped image using nested loops
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                int rgb = pic.getRGB(i, j);
                cropped.setRGB(i, j, rgb);
            }
        }

        return cropped;
    }

    /**
     * Mirrors image horizontally.
     *
     */
    public static BufferedImage mirrorHorizontal(BufferedImage pic) {
        int width = pic.getWidth();
        int height = pic.getHeight();

        // Create new BufferedImage with same dimensions
        BufferedImage mirrored = new BufferedImage(width, height, pic.getType());

        // Mirror pixels horizontally
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = pic.getRGB(x, y);
                mirrored.setRGB(width - 1 - x, y, rgb);
            }
        }

        return mirrored;
    }

    /**
     * Mirrors image vertically.
     *
     */
    public static BufferedImage mirrorVertical(BufferedImage pic) {
        int width = pic.getWidth();
        int height = pic.getHeight();

        // Create new BufferedImage with same dimensions
        BufferedImage mirrored = new BufferedImage(width, height, pic.getType());

        // Mirror pixels vertically
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = pic.getRGB(x, y);
                mirrored.setRGB(x, height - 1 - y, rgb);
            }
        }

        return mirrored;
    }

    /**
     * Converts image to greyscale using RGB averaging.
     *
     */
    public static BufferedImage greyScale(BufferedImage pic) {
        int width = pic.getWidth();
        int height = pic.getHeight();

        // Create new BufferedImage with same dimensions
        BufferedImage grey = new BufferedImage(width, height, pic.getType());

        // Convert each pixel to greyscale using RGB averaging
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = pic.getRGB(x, y);
                Color color = new Color(rgb);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Calculate greyscale value using averaging formula
                int greyValue = (red + green + blue) / 3;

                // Create new Color with same value for R, G, and B components
                Color greyColor = new Color(greyValue, greyValue, greyValue);
                grey.setRGB(x, y, greyColor.getRGB());
            }
        }

        return grey;
    }
}
//end of program
