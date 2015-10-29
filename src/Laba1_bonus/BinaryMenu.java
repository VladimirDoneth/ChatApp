package Laba1_bonus;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class BinaryMenu {
	private static Scanner reader = new Scanner(System.in);

	private static boolean Menu(DataInput in, DataOutput out, RandomAccessFile file, int vib) {
		Tour t = new Tour();
		int number;
		switch (vib) {
		case 0:
			System.out.println("Для работы просто вводите эти комманды");
			System.out.println("1 - Просмотр информации о турах");
			System.out.println("2 - Добавление тура в конец");
			System.out.println("3 - Удаление тура в заданной позиции");
			System.out.println("4 - Редактирование тура в заданной позиции");
			System.out.println("666 - Выход + Сохранение в файл");
			break;
		case 1:
			System.out.println("Просмотр информации о турах");
			t.PrintAll(in);
			break;
		case 2:
			System.out.println("Добавление тура в конец");
			try {
				t.AddTourInEndFile(out, in);
			} catch (Exception e) {
				System.out.println("Не удалось записать в файл");
			}
			break;
		case 3:
			System.out.println("Удаление тура в заданной позиции");
			System.out.println("Введите номер удаляемого тура");
			number = reader.nextInt();
			try {
				t.DeleteTour(in, out, file, number);
			} catch (Exception e) {
				System.out.println("Не удалось удалить потому, что такого номера нет");
			}
			break;
		case 4:
			System.out.println("Редактирование тура в заданной позиции");
			System.out.println("Введите НОМЕР ИЗМЕНЯЕМОГО ТУРА");
			number = reader.nextInt();
            try{
            	t.makeModeInTour(file, file, file, number);
            }catch(Exception e){
            	System.out.println("Не выполнил редактирование");
            }
			break;
		case 999:
			System.out.println("Введите число");
			number = reader.nextInt();
			t.writeRandomTourToFile(number, in, out);
		case 666:
			System.out.println("Выполняю выход");
			if (vib == 666)
				return false;
			break;
		}
		return true;

	}

	public static void main(String[] args) {
		RandomAccessFile file = null;
		boolean exit = true;
		try {
			file = new RandomAccessFile("D:/file/tourFile/BaseTourFile.txt", "rw");
		} catch (IOException e) {
			System.out.println("Ошибка открытия файла, или такого файла нет и т.д.");
			exit = false;
		}

		System.out.println("Если не знаете, что делать дальше просто введите '0'");
		while (exit) {
			System.out.println("Введите число выбора");
			try {
				file.seek(0);
			} catch (IOException e) {
				System.out.println("Для разработчика 2, не обращать внимания");
			}
			int vib = reader.nextInt();
			exit = Menu(file, file, file, vib);
			System.out.println();
		}
		reader.close();
	}

}
