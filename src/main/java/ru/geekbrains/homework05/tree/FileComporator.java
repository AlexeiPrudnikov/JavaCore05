package ru.geekbrains.homework05.tree;

import java.io.File;
import java.util.Comparator;

public class FileComporator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
