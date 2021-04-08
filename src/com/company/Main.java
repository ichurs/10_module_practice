package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, MyCustomExeption {
        System.out.println(varianceFromFile(new File("src/input10.txt")));
    }

    public static String varianceFromFile(File file) throws IOException, MyCustomExeption {
        // пробуем открыть файл по указанному пути
        // вычисляем количество строк в файле для определения размера массива
        try {
            FileReader inFile = new FileReader(file);
            Scanner scanner = new Scanner(inFile);
            int counter = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                counter++;
            }
            int[] array = new int[counter];
            inFile.close();

            // снова открываем файл и считываем строки, последовательно записывая их в массив типа int
            // если в текстовом файле присутствует строка, содержащая нечисловые значения - выкидывается NumberFormatException
            try {
                FileReader newInFile = new FileReader(file);
                Scanner newScanner = new Scanner(newInFile);
                for(int i = 0; i < array.length; i++){
                    try {
                        array[i] = Integer.parseInt(newScanner.nextLine());
                    }
                    catch (NumberFormatException e){
                        throw new MyCustomExeption("Не числовой формат данных! " + e.getMessage());
                    }
                    finally {
                        newInFile.close();
                    }
                }
                // расчитываем среднее значение ряда
                double average = 0.0;
                int summ = 0;
                int summSquare = 0;
                for(int i: array){
                    summ += i;
                }
                try {
                    average = summ / array.length;
                }
                catch (ArithmeticException e) {
                    throw new MyCustomExeption("Невозможно вычислить дисперсию для пустого множества! " + e.getMessage());
                }
                // рассчитаем сумму квадратов разностей чисел ряда и среднего значения
                for (int i: array){
                    summSquare += Math.pow(i - average, 2);
                }
                // рассчитаем дисперсию как отношение суммы квадратов разностей чисел ряда и среднего значения
                // к числу элементов ряда
                double variance = summSquare / array.length;
                return "Дисперсия целочисленного ряда равна: " + variance;

            } catch (FileNotFoundException e) {
                throw new MyCustomExeption("Файл не найден! " + e.getMessage());
            }
        }
        catch (FileNotFoundException e) {
            throw new MyCustomExeption("Файл не найден! " + e.getMessage());
        }
    }
}
