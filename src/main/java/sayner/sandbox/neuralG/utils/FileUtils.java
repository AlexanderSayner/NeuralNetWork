package sayner.sandbox.neuralG.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс обеспечивает минимальный функционал для работы с файлами
 */
public class FileUtils {

    // Sorry again
    private FileUtils() {

    }

    public static String loadAsString(String filePath) {

        StringBuilder result = new StringBuilder();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String buffer = ""; // Буфер для чтения из файла

            while ((buffer = reader.readLine()) != null) {

                result.append(buffer);
                result.append("\n");
            }

            reader.close();

        } catch (FileNotFoundException fnfe) {

            System.out.println(String.format("Файл %s не обнаружен", filePath));
            fnfe.printStackTrace();

        } catch (IOException ioe) {

            System.out.println(String.format("Оошибка при чтении из файла %s", filePath));
            ioe.printStackTrace();
        }

        return result.toString();
    }
}
