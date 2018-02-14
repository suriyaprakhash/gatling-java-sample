package com.example.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.controller.FileUploadController;
import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.model.IndirectData;
import com.example.model.Result;
import com.example.utils.GtogUtil;

public class ContentProcessor {
	private static final Logger logger = LogManager.getLogger(FileUploadController.class);

	private List<String> directDataInputLine = new ArrayList<String>();
	private List<String> indirectDataInputLine = new ArrayList<String>();
	private List<String> directQuestionInputLine = new ArrayList<String>();
	private List<String> indirectQuestionInputLine = new ArrayList<String>();
	private List<String> irrelevantQuestionInputLine = new ArrayList<String>();

	private List<DirectData> directDataList;
	private List<IndirectData> indirectDataList;
	private List<Result> resultList;

	public void setUpData() {
		directDataList = new ArrayList<DirectData>();
		indirectDataList = new ArrayList<IndirectData>();
		resultList = new ArrayList<Result>();
	}

	public void processDirectData() throws GtogException {
		directDataInputLine.forEach(directData -> {
			try {
				processDirectData(directData);
			} catch (GtogException e) {
				try {
					throw new GtogException("Directdata error", e);
				} catch (GtogException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public void processDirectData(String indirectData) throws GtogException {
		String[] individualData = indirectData.split(" ");
		if (individualData.length == 3) {
			String galactic = "", roman = "";
			int number = 0;
			if (individualData[0] instanceof String) {
				galactic = individualData[0];
			} else {
				throw new GtogException("invalid input data : direct data : 0 : not string");
			}
			if (individualData[2] instanceof String && individualData[2].length() == 1) {
				roman = individualData[2];
			} else {
				throw new GtogException("invalid input data : direct data : 2 : not roman");
			}
			if ("is".toLowerCase().equals(individualData[1])) {
				DirectData data = new DirectData();
				data.setGalactic(galactic);
				data.setRoman(roman);
				data.setNumber(GtogUtil.getNumberForRoman(roman));
				directDataList.add(data);
			} else {
				throw new GtogException("invalid input data : direct data : 1 : not `is`");
			}

		} else {
			throw new GtogException("invalid input data : direct data : length mismatch");
		}
	}

	public void processIndirectData() throws GtogException {
		indirectDataInputLine.forEach(indirectData -> {
			try {
				processIndirectData(indirectData);
			} catch (GtogException e) {
				try {
					throw new GtogException("Indirectdata error", e);
				} catch (GtogException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void processIndirectData(String indirectDataLine) throws GtogException {
		String[] individualData = indirectDataLine.split(" ");
		int credit = 0;
		List<String> galacticList = new ArrayList<String>();
		String metal = "";

		try {
			// getting the number before credit
			credit = Integer.parseInt(individualData[individualData.length - 2]);
		} catch (Exception e) {
			throw new GtogException("invalid input data : indirect data : credit not a number ");
		}
		// getting galactic data
		for (int i = 0; i < individualData.length - 3; i++) {
			String currentElement = individualData[i];
			// add to galactic if directData's galactic string matches individualData string
			if (directDataList.stream().anyMatch(directData -> directData.getGalactic().equals(currentElement))) {
				galacticList.add(currentElement);
			}
			// else mark it as metal
			else {
				metal = currentElement;
			}
		}
		int total = 0;
		int start = 0;
		int length = galacticList.size();
		StringBuilder galacticDataList = new StringBuilder("");
		IndirectData indirectData = new IndirectData();
		double metalValue=GtogUtil.metalValueResolver(total, start, length,
				galacticList.stream().toArray(String[]::new), directDataList, galacticDataList,credit);
		if(metalValue==-1) {
			throw new GtogException("invalid input data : indirect data : invalid metal value ");
		}
		indirectData.setCredit(credit);
		indirectData.setGalacticList(galacticList);
		indirectData.setMetal(metal);
		indirectData.setMetalValue(metalValue);
		indirectDataList.add(indirectData);
	}

	public void processDirectQuestion() throws GtogException {
		directQuestionInputLine.forEach(directQuestionLine -> {
			try {
				processDirectQuestion(directQuestionLine);
			} catch (GtogException e) {
				try {
					throw new GtogException("Direct Question error", e);
				} catch (GtogException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void processDirectQuestion(String directQuestionLine) throws GtogException {
		String[] individualQuestionData = directQuestionLine.split(" ");
		Result result = new Result();
		StringBuilder galacticDataListQuestion = new StringBuilder("");
		double total = 0;
		int start = 3;// start after "how much is"
		int length = individualQuestionData.length - 1;// ends before "?"

		total = GtogUtil.calc(total, start, length, individualQuestionData, directDataList, galacticDataListQuestion);

		if (total == -1) {
			result.setQuetion(directQuestionLine);
			result.setAnswer("I have no idea what you are talking about");
			resultList.add(result);
		} else {
			result.setQuetion(directQuestionLine);
			result.setAnswer(galacticDataListQuestion.append("is ").append(total).toString());
		}

		resultList.add(result);

	}

	public void processIndirectQuestion() throws GtogException {
		indirectQuestionInputLine.forEach(indirectQuestionLine -> {
			try {
				processIndirectQuestion(indirectQuestionLine);
			} catch (GtogException e) {
				try {
					throw new GtogException("Direct Question error", e);
				} catch (GtogException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void processIndirectQuestion(String indirectQuestionLine) throws GtogException {
		String[] individualQuestionData = indirectQuestionLine.split(" ");
		Result result = new Result();
		StringBuilder galacticDataListQuestion = new StringBuilder("");
		double total = 0;
		int start = 4;// start after "how many credits is"
		int length = individualQuestionData.length - 2;// ends before "?"

		total = GtogUtil.calc(total, start, length, individualQuestionData, directDataList, galacticDataListQuestion);

		if (total == -1) {
			result.setQuetion(indirectQuestionLine);
			result.setAnswer("I have no idea what you are talking about");
			resultList.add(result);
		} else {
			double metalValue=GtogUtil.fetchMetalValue(individualQuestionData[individualQuestionData.length - 2],indirectDataList);
			result.setQuetion(indirectQuestionLine);
			result.setAnswer(galacticDataListQuestion.append(individualQuestionData[individualQuestionData.length - 2]).append("is").append(metalValue * total).toString());
		}

		resultList.add(result);

	}

	public void classifyInput(String inputLine) {
		if (inputLine.endsWith("?")) {
			if (inputLine.toLowerCase().startsWith("how much is")) {
				directQuestionInputLine.add(inputLine);
			} else if (inputLine.toLowerCase().startsWith("how many credits is")) {
				indirectQuestionInputLine.add(inputLine);
			} else {
				irrelevantQuestionInputLine.add(inputLine);
			}
		} else {
			if (inputLine.toLowerCase().endsWith("credits")) {
				indirectDataInputLine.add(inputLine);
			} else {
				directDataInputLine.add(inputLine);
			}
		}
	}

	public void print() {
		GtogUtil.print(directDataList, indirectDataList, resultList);
	}

}
