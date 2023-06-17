package ru.geekbrains.homework05.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Backupper {
    /**
     * Метод резервного копирования файлов из каталога
     * @param folder каталог для резервного копирования
     * @throws IOException
     */
    public static void makeBackup(File folder) throws IOException {
        if (folder.isFile()) throw new IllegalArgumentException("Аргумент не является каталогом");
        File backup = new File(folder.toString() + "/backup/");
        if (!backup.exists()) {
            backup.mkdirs();
        }
        File[] files = folder.listFiles();
        for (File file: files) {
            if (file.isDirectory()) continue;
            Files.copy(Path.of(file.toString()),
                    Path.of(folder.toString() + "/backup/" + file.getName()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
