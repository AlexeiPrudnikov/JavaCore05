package ru.geekbrains.homework05.encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class ArrayEncoder {
    /**
     * HashMap для кодирования
     */
    private static final HashMap<Character, Integer> CODE_TABLE;
    /**
     * HashMap для декодирования
     */
    private static final HashMap<Integer, Character> DECODE_TABLE;

    static {
        CODE_TABLE = new HashMap<>();
        CODE_TABLE.put('.', 0);
        CODE_TABLE.put('X', 1);
        CODE_TABLE.put('0', 2);
        CODE_TABLE.put('R', 3);
        DECODE_TABLE = new HashMap<>();
        DECODE_TABLE.put(0, '.');
        DECODE_TABLE.put(1, 'X');
        DECODE_TABLE.put(2, '0');
        DECODE_TABLE.put(3, 'R');
    }

    /**
     * Создает квадратный массив размером length и заполняет случайно сначениями из кодовой таблицы
     *
     * @param length размер массива
     * @return заполненный квадратный двумерный массив
     */
    public static char[][] fillArrayRandom(int length) {
        char[][] result = new char[length][length];
        Random random = new Random();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = DECODE_TABLE.get(random.nextInt(0, DECODE_TABLE.size()));
            }
        }
        return result;
    }

    /**
     * Метод печати двумерного массива
     *
     * @param field двумерный массив
     */
    public static void printArray(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Метод подсчитывания количества элементов произвольного двумерного массива
     *
     * @param field двумерный массив
     * @return количество элементов
     */
    private static int getCountElements(char[][] field) {
        if (field == null) throw new NullPointerException();
        int size = 0;
        for (int i = 0; i < field.length; i++) {
            if (field[i] == null) throw new NullPointerException();
            size += field[i].length;
        }
        return size;
    }

    /**
     * Метод кодирования элементов массива
     *
     * @param field двумерный массив
     * @return закодированная байтовая последовательноть
     * @throws NullPointerException
     */
    public static byte[] codeArray(char[][] field) throws NullPointerException {
        int size = getCountElements(field);
        byte[] result = new byte[size / 3 + size % 3];
        int shift = 0;
        int index = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (shift == 3) {
                    index++;
                    shift = 0;
                }
                result[index] = (byte) (result[index] << 2);
                if (CODE_TABLE.containsKey(field[i][j])) {
                    result[index] += CODE_TABLE.get(field[i][j]);
                } else {
                    result[index] += CODE_TABLE.get('.');
                }
                shift++;
            }
        }
        if (size % 3 != 0) {
            for (int i = 0; i < 3 - size % 3; i++) {
                result[result.length - 1] = (byte) (result[result.length - 1] << 2);
            }
        }
        return result;
    }

    /**
     * Метод декодирования массива
     *
     * @param code  байтовая последовательность для декодирования
     * @param field структура массива
     * @return декодированный двумерный массив заданной структуры
     * @throws IllegalArgumentException
     */
    public static char[][] decodeArray(byte[] code, char[][] field) throws IllegalArgumentException {
        int shift = 0;
        int index = 0;
        int size = getCountElements(field);
        if (code.length * 3 < size) throw new IllegalArgumentException("Короткая кодовая строка");
        int currentValue = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (shift == 3) {
                    index++;
                    shift = 0;
                }
                switch (shift) {
                    case 0:
                        currentValue = code[index] / 16;
                        break;
                    case 1:
                        currentValue = code[index] % 16 / 4;
                        break;
                    case 2:
                        currentValue = code[index] % 4;
                        break;
                    default:
                        currentValue = 0;
                        break;
                }
                if (DECODE_TABLE.containsKey(currentValue)) {
                    field[i][j] = DECODE_TABLE.get(currentValue);
                } else {
                    field[i][j] = '.';
                }
                shift++;
            }
        }
        return field;
    }

    /**
     * Метод кодирования элеменнов массива с последующей записью байтовой последовательности в файл
     *
     * @param filePath файл для записи
     * @param field    массив
     * @throws IOException
     * @throws NullPointerException
     */
    public static void writeCodeArrayToFile(String filePath, char[][] field) throws IOException, NullPointerException {
        byte[] codeField = codeArray(field);
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            fileOutputStream.write(codeField);
        }
    }

    /**
     * Метод декодирования элеменнов массива исходя из данных, записанных в файле
     *
     * @param filePath файл последовательностью
     * @param field    структура массива
     * @return
     * @throws IOException
     */
    public static char[][] loadDecodeArrayFromFile(String filePath, char[][] field) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            byte[] code = fileInputStream.readAllBytes();
            return decodeArray(code, field);
        }
    }
}
