package application.model;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AlarmModel {

	// Format of date used throughout class
	final DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a");

	// Method that is called to add a given alarm to alarms.txt
	public void addAlarm(String alarm, String fileName) throws IOException {
		File f1 = new File(fileName);
		Scanner infile = new Scanner(f1);

		ArrayList<String> alarms = new ArrayList<String>();
		while (infile.hasNext()) {
			alarms.add(infile.nextLine());
		}
		infile.close();

		if (alarms.size() >= 20) {
			System.out.println("Maximum amount of alarms reached.");
		} else {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.append(LocalTime.parse(alarm, format).format(format));
			bw.newLine();
			bw.close();
		}
	}

	// Method that is called to remove a given alarm from alarms.txt
	public void removeAlarm(int count, String fileName) throws IOException {
		File f1 = new File(fileName);
		Scanner infile = new Scanner(f1);
		ArrayList<String> alarms = new ArrayList<String>();
		while (infile.hasNext()) {
			alarms.add(infile.nextLine());
		}
		infile.close();

		if (alarms.isEmpty()) {
			System.out.println("No alarms to remove.");
		} else {
			new FileWriter(fileName, false).close();

			alarms.remove(count);
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < alarms.size(); i++) {
				bw.append(alarms.get(i));
				bw.newLine();
			}

			bw.close();
		}
	}

	// Returns ArrayList of Strings from alarms.txt
	public ArrayList<String> loadAlarms1(String fileName) throws IOException, ParseException {
		File f1 = new File(fileName);
		Scanner infile = new Scanner(f1);

		ArrayList<String> alarms = new ArrayList<String>();
		while (infile.hasNext()) {
			alarms.add(infile.nextLine());
		}
		infile.close();

		ArrayList<String> alarms1 = new ArrayList<String>();
		for (int i = 0; i < alarms.size(); i++) {
			if ((i < 10)) {
				alarms1.add(alarms.get(i));
			}
		}

		return alarms1;
	}

	// Returns ArrayList of Strings from alarms.txt
	public ArrayList<String> loadAlarms2(String fileName) throws IOException, ParseException {
		File f1 = new File(fileName);
		Scanner infile = new Scanner(f1);

		ArrayList<String> alarms = new ArrayList<String>();
		while (infile.hasNext()) {
			alarms.add(infile.nextLine());
		}
		infile.close();

		ArrayList<String> alarms2 = new ArrayList<String>();
		for (int i = 0; i < alarms.size(); i++) {
			if (!(i < 10)) {
				alarms2.add(alarms.get(i));
			}
		}

		return alarms2;
	}

	// Method that looks for alarm that is closest to current time and returns it
	public String updateNextAlarm(String fileName) throws IOException, ParseException {
		ArrayList<String> alarms1List = loadAlarms1(fileName);
		ArrayList<String> alarms2List = loadAlarms2(fileName);

		LocalDateTime today = LocalDateTime.now();

		LocalDateTime nextAlarmTime = LocalDateTime.MAX;
		if (!alarms1List.isEmpty()) {
			for (int i = 0; i < alarms1List.size(); i++) {
				LocalDateTime alarmCheck = LocalDateTime.of(today.toLocalDate(),
						LocalTime.parse(alarms1List.get(i), format));

				if (alarmCheck.isBefore(today)) {
					alarmCheck = alarmCheck.plusDays(1);
				}

				if (alarmCheck.isAfter(today)) {

					if (alarmCheck.isBefore(nextAlarmTime)) {
						nextAlarmTime = alarmCheck;
					}
				}
			}

			for (int i = 0; i < alarms2List.size(); i++) {
				LocalDateTime alarmCheck = LocalDateTime.of(today.toLocalDate(),
						LocalTime.parse(alarms1List.get(i), format));

				if (alarmCheck.isBefore(today)) {
					alarmCheck = alarmCheck.plusDays(1);
				}

				if (alarmCheck.isAfter(today)) {
					if (alarmCheck.isBefore(nextAlarmTime)) {
						nextAlarmTime = alarmCheck;
					}
				}
			}
		}

		return (nextAlarmTime.format(format));
	}

	// Method that checks if any alarm matches current time
	public int alarmOn(String fileName) throws IOException, ParseException {
		LocalTime today = LocalTime.now();

		ArrayList<String> alarms1List = loadAlarms1(fileName);
		ArrayList<String> alarms2List = loadAlarms2(fileName);

		int count = 1;
		for (int i = 0; i < alarms1List.size(); i++) {
			if (today.format(format).equals(LocalTime.parse(alarms1List.get(i), format).format(format))) {
				return count;
			}
			count++;
		}

		for (int i = 0; i < alarms2List.size(); i++) {
			if (today.format(format).equals(LocalTime.parse(alarms1List.get(i), format).format(format))) {
				return count;
			}
			count++;
		}

		return 0;

	}
	
	// Method that checks if given fileName exists, otherwise it creates it
		public void createFile(String fileName) throws IOException {
			File alarms = new File(fileName);
			if (alarms.createNewFile()) {
				System.out.println("Created new file: " + fileName);
			}
		}
}