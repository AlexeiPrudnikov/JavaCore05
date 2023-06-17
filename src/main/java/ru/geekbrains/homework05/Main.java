package ru.geekbrains.homework05;

import ru.geekbrains.homework05.backup.Backupper;
import ru.geekbrains.homework05.encoder.ArrayEncoder;
import ru.geekbrains.homework05.tree.Tree;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        File file = new File("/Users/alx/Downloads");
        Tree.printTree(file);

        char[][] field = ArrayEncoder.fillArrayRandom(3);
        ArrayEncoder.printArray(field);
        System.out.println();
        ArrayEncoder.writeCodeArrayToFile("field.txt", field);
        field = ArrayEncoder.loadDecodeArrayFromFile("field.txt", field);
        ArrayEncoder.printArray(field);
        Backupper.makeBackup(new File("/Users/alx/Downloads"));
        System.out.println("");

    }
}