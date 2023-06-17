package ru.geekbrains.homework05.tree;

import java.io.File;
import java.util.Arrays;

public class Tree {
    private static void printTree(File file, String offsetStr, int offset) {

        File[] files = file.listFiles();
        FileComporator fileComporator = new FileComporator();
        Arrays.sort(files, fileComporator);
        String pre = offsetStr;
        if (files == null) return;
        for (int i = 0; i < files.length; i++) {
            if (i == files.length - 1) {
                System.out.println(offsetStr + "└─"  + files[i].getName());
                pre += "  ";

            } else {
                System.out.println(offsetStr + "├─"  + files[i].getName());
                pre += "│ ";
            }
            if (files[i].isDirectory()) {
                printTree(files[i], pre + "  ",offset + 1);
            }
            pre = offsetStr;
        }
    }

    public static void printTree(File file) {
        System.out.println(file.getName());
        if (file.isDirectory()) {
            printTree(file, " ", 1);
        }
    }
}