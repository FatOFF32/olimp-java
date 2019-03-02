import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class FileUtils {

    /**
     * Создать файл с заданным именем
     *
     * @param fileName имя файла, который необходимо создать
     * @return true если файл был успешно создан или уже существует; false если файл не мог быть создан
     * @throws NullPointerException если {@code fileName} имеет значение {@code null}
     */
    public static boolean createFile(String fileName) {
        Objects.requireNonNull(fileName, "Имя файл не должно равняться null");

        File file = new File(fileName);
        if (file.exists()) {
            return true; // Файл уже был создан
        }

        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
            return false;
        }
    }

    // TODO: Вопрос к мастеру, уместно ли тут использовать present perfect?
    /**
     * Create files with names from array {@code fileNames}
     *
     * @param fileNames array of file's names, which need create
     * @return {@code true} if files have been to create successfully,
     *         {@code false} if at least one file hasn't been to create
     * @throws NullPointerException if at least one file have had value {@code null}
     * @see FileUtils#createFile(String)
    */
    public static boolean createFiles(String[] fileNames) {
        boolean allFilesWereCreated = true;
        for (String fileName : fileNames) {
            boolean createSucces = FileUtils.createFile(fileName);

            if (!createSucces) {
                allFilesWereCreated = false;
            }
        }

        return allFilesWereCreated;
    }

    /**
     * Method read file by name {@code fileName} and return it contents
     *
     * @param fileName file name of returned content
     * @return file's content
     */
    public static String readFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
            return null;
        }

        StringBuilder fileText = new StringBuilder();
        while (scanner.hasNext()) {
            fileText.append(scanner.next());
        }

        return fileText.toString();
    }


    /**
     * Method create new file by name {@code fileName} with context {@code text}.
     * File will be overwritten if it exists.
     *
     * @param fileName file name to creates
     * @param text file's context
     * @return {@code true} if file has been create, {@code false} if file hasn't been create
     */
    public static boolean writeFile(String fileName, String text) {
        createFile(fileName);

        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(text);
        } catch (FileNotFoundException e) {
            System.err.println("Файл " + fileName + " не найден");
            return false;
        }

        return true;
    }

    /**
     * Method add text {@code text} in file by name {@code fileName}.
     * If file exists, method add text to end file. If file does exist, method create new file.
     * @param fileName file name
     * @param text file's text
     * @return {@code true} operation completed successfully, {@code false} operation failed
     * @see FileUtils#writeFile(String, String)
     */
    public static boolean appendFile(String fileName, String text) {
        String fileText = readFile(fileName);
        if (fileText == null) {
            fileText = "";
        }

        return writeFile(fileName, fileText + text);
    }
}
