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

    // test
    /* TODO: Здесь нужно написать документацию. Данная функция создает файлы с именами, которые были переданы в массиве
     *  и возвращает true если все файлы были успешно созданы и false если хотя бы один файл был создан не успешно */
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

    // TODO: Здесь нужно написать документацию. Данная функция возвращает содержимое файла c переданным именем
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

    /* todo здесь нужно написать документацию. Данная функция записывает файл с переданным именем заданный текст.
    *    Если в файле что-то есть, то его содержимое перезаписывается переданным текстом */
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

    /* TODO: здесь нужно написать документацию. Данная функция записывает файл с переданным именем заданный текст.
     *    Если в файле что-то есть, то переданный текст записывается в конец файла, оставляя содержимое файла нетронутым */
    public static boolean appendFile(String fileName, String text) {
        String fileText = readFile(fileName);
        if (fileText == null) {
            fileText = "";
        }

        return writeFile(fileName, fileText + text);
    }
}
