package com.example.utils;

import java.util.List;
import java.util.Optional;

import com.example.model.DirectData;
import com.example.model.IndirectData;
import com.example.model.Result;

public class GtogUtil {

	public static double metalValueResolver(double total, int start, int length, String[] galacticList,
			List<DirectData> directDataList, StringBuilder galacticDataList, double credits) {
		double metalValue = 0;
		total = calc(total, start, length, galacticList, directDataList, galacticDataList);
		if (total != -1) {
			metalValue = credits / total;
		} else {
			metalValue = -1;
		}
		return metalValue;
	}

	public static double calc(double total, int start, int length, String[] individualQuestionData,
			List<DirectData> directDataList, StringBuilder galacticDataListQuestion) {
		double prev = 0;
		int count = 0;
		// get data between "how much is" and "?" from RHS to LHS
		// for (int i = 3; i<individualQuestionData.length-1 ; i++) {
		for (int i = start; i < length; i++) {
			String currentElement = individualQuestionData[i];

			// add to galactic if directData's galactic string matches individualData string
			// Predicate<DirectData> predicateDirectData= pdd ->
			// pdd.getGalactic().equals(currentElement);
			Optional<DirectData> dd = directDataList.stream().filter(ddl -> ddl.getGalactic().equals(currentElement))
					.findFirst();
			double element = dd.get().getNumber();
			// double element=dd.getNumber();
			if (prev == 0) {
				prev = element;
			} else if (prev < element) {
				if ((prev == 1 && (element == 5 || element == 10)) || (prev == 10 && (element == 50 || element == 100))
						|| (prev == 100 && (element == 500 || element == 1000))) {
					total = (element - prev) + total;
					count = 0;
					prev = element;
				} else {
					// invalid input
					total = -1;
				}
			} else if (prev > element) {
				if(total==0) {
					total=prev;
				}
				total = total + element;
				count = 0;
				prev = element;
			} else if ((prev == element && count == 4)
					|| ((prev == element && count == 2) && (element == 5 || element == 50 || element == 500))) {
				// invaid input
				total = -1;
			} else if (prev == element) {
				if(total==0) {
					total=prev;
				}
				count++;
				total = total + element;
				prev = element;
			}

			galacticDataListQuestion.append(dd.get().getGalactic() + " ");

		}
		return total;
	}

	public static double fetchMetalValue(String metal, List<IndirectData> indirectDataList) {
		Optional<IndirectData> idd = indirectDataList.stream().filter(data -> data.getMetal().equals(metal))
				.findFirst();
		return idd.get().getMetalValue();
	}

	public static double getNumberForRoman(String roman) {
		int number = 0;
		switch (roman.toLowerCase()) {
		case "i":
			number = 1;
			break;
		case "v":
			number = 5;
			break;
		case "x":
			number = 10;
			break;
		case "l":
			number = 50;
			break;
		case "c":
			number = 100;
			break;
		case "d":
			number = 500;
			break;
		case "m":
			number = 1000;
			break;
		default:
			break;
		}
		return number;

	}

	public static void print(List<DirectData> directDataList, List<IndirectData> indirectDataList,
			List<Result> resultList) {
		System.out.println("------------Direct data------------");
		directDataList.forEach(data -> {
			System.out.print(data.getGalactic());
			System.out.print("-" + data.getNumber());
			System.out.print("-" + data.getRoman());
			System.out.println();
		});
		System.out.println("------------Indirect data------------");
		indirectDataList.forEach(data -> {
			data.getGalacticList().forEach(galacticData -> System.out.print(galacticData + " "));
			System.out.print("-" + data.getCredit());
			System.out.print("-");
			System.out.print(data.getMetal());
			System.out.print("-");
			System.out.print(data.getMetalValue());
			System.out.println();
		});
		System.out.println("------------Result------------");
		resultList.forEach(data -> {
			System.out.print(data.getAnswer());
			System.out.println();
		});

	}

}
