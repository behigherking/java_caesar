
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class CaesarCipher {


    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public void encrypt(String inputFile, String outputFile, int key) {
        String text = readFile(inputFile);
        String encryptedText = processText(text, key);
        writeFile(outputFile, encryptedText);
    }

    public void decrypt(String inputFile, String outputFile, int key) {
        String text = readFile(inputFile);
        String decryptedText = processText(text, -key);
        writeFile(outputFile, decryptedText);

    }

    public void bruteForce(String inputFile, String outputFile) {
        String text = readFile(inputFile);
        StringBuilder decryptedText = new StringBuilder();
        for (int key = 1; key < ALPHABET.length(); key++) {
            decryptedText.append("*******************************************\n\n" +
                    "Key: " + key + " "
                    + processText(text, -key) +
                    "\n\n");
        }
        writeFile(outputFile, decryptedText.toString());
    }

    public void statisticalAnalysis(String inputFile, String outputFile) {
        String text = readFile(inputFile);
        StringBuilder decryptedText = new StringBuilder();
        Map<Character, Integer> frequencyMap = new HashMap<>();

        // посчитал количество вхождений каждой буквы
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerChar = Character.toLowerCase(c);
                frequencyMap.put(lowerChar, frequencyMap.getOrDefault(lowerChar, 0) + 1);
            }
        }

        // поиск максимально встречающейся буквы в зашифрованном тексте
        char mostFrequentChar = ' ';
        int maxFrequency = 0;

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentChar = entry.getKey();
            }
        }
        char startChar = mostFrequentChar;
        char endChar = 'e'; //  первая самая частая буква
        int startIndex = ALPHABET.indexOf(startChar);
        int endIndex = ALPHABET.indexOf(endChar);
        int key = endIndex - startIndex;
        decryptedText.append("*******************************************\n\n" + "Letter: " + "'e' " + processText(text, key) + "\n\n");

        endChar = 't'; // вторая самая частая буква
        endIndex = ALPHABET.indexOf(endChar);
        key = endIndex - startIndex;
        decryptedText.append("*******************************************\n\n" + "Letter: " + "'e' " + processText(text, key) + "\n\n");
//        int secondKey  = (mostFrequentChar - 't' + ALPHABET.length()) % ALPHABET.length(); // t - вторая предполагаемая самая частая буква
//        decryptedText.append("*******************************************\n\n" + "Letter: " + "'t' " + processText(text, secondKey) + "\n\n");
//        int thirdKey = (mostFrequentChar - 'a') % 26; // t - вторая предполагаемая самая частая буква
//        decryptedText.append("*******************************************\n\n" + "Letter: " + "'a' " + processText(text, thirdKey) + "\n\n");

        writeFile(outputFile, decryptedText.toString());
    }



    private String processText(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int shifted = (character - base + key) % ALPHABET.length();
                if (shifted < 0) shifted += ALPHABET.length();
                result.append((char) (base + shifted));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }


    private String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return content.toString();
    }

    private void writeFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        CaesarCipher cipher = new CaesarCipher();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Выберите действие:");
            System.out.println("1. Шифрование");
            System.out.println("2. Расшифровка с ключом");
            System.out.println("3. Брутфорс");
            System.out.println("4. Статистический анализ");
            System.out.println("0. Выход");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя входного файла: ");
                    String inputFileEncrypt = scanner.nextLine();
                    System.out.print("Введите имя выходного файла: ");
                    String outputFileEncrypt = scanner.nextLine();
                    System.out.print("Введите ключ: ");
                    int encryptKey = scanner.nextInt();
                    cipher.encrypt(inputFileEncrypt, outputFileEncrypt, encryptKey);
                    break;

                case 2:
                    System.out.print("Введите имя входного файла: ");
                    String inputFileDecrypt = scanner.nextLine();
                    System.out.print("Введите имя выходного файла: ");
                    String outputFileDecrypt = scanner.nextLine();
                    System.out.print("Введите ключ: ");
                    int decryptKey = scanner.nextInt();
                    cipher.decrypt(inputFileDecrypt, outputFileDecrypt, decryptKey);
                    break;

                case 3:
                    System.out.print("Введите имя входного файла: ");
                    String inputFileBruteForce = scanner.nextLine();
                    System.out.print("Введите имя выходного файла: ");
                    String outputFileBruteForce = scanner.nextLine();
                    cipher.bruteForce(inputFileBruteForce, outputFileBruteForce);
                    break;

                case 4:
                    System.out.print("Введите имя входного файла: ");
                    String inputFileAnalysis = scanner.nextLine();
                    System.out.print("Введите имя выходного файла: ");
                    String outputFileAnalysis = scanner.nextLine();
                    cipher.statisticalAnalysis(inputFileAnalysis, outputFileAnalysis);
                    break;

                case 0:
                    System.out.println("Выход из программы.");
                    break;

                default:
                    System.out.println("\nНеверный выбор. Пожалуйста, попробуйте снова.\n");
            }
        } while (choice != 0);

        scanner.close();
    }
}
