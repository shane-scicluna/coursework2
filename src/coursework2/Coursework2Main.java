/*
 * nearest neighbour using euclidean distance
 * two data sets are read into 2 2d arrays
 * if last value of two shortest rows is the same then it's correct
 */
package coursework2;

import java.io.*;

public class Coursework2Main {

	public static final File FILE1 = new File("cw2DataSet1.csv");
	public static final File FILE2 = new File("cw2DataSet2.csv");
	public static final int ROW = 2810;
	public static final int COL = 65;

	public static void main(String[] args) throws IOException 
	{
		System.out.println("Start");
		printResults();
		System.out.println("End");
	}

	//method for reading csv file and inputting into 2d Array
	public static double[][] inputValues(File file) throws NumberFormatException, IOException
	{
		double[][] dataSet = new double[ROW][COL];
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line;
		int dataRow = 0;
		
		//looping through every line
		while((line = input.readLine()) != null)
		{
			String[] token = line.split(",");	//splitting to store numbers individually
			
			//looping through every column to build row
			for (int dataCol = 0; dataCol < COL; dataCol++)
			{
				dataSet[dataRow][dataCol] = Double.parseDouble(token[dataCol]);	//parsing string to double
			}
			dataRow++;
		}
		input.close();
		return dataSet;
	}
	
	//finding euclidean distance between vectors
	public static double findDistance(double[][] dataSet1, double[][] dataSet2, int dataRow1, int dataRow2)	
	{
		double finalResult = 0.0;
		double firstValue = 0.0;
		double secondValue = 0.0;
		double result = 0.0;
		
		for(int dataCol = 0; dataCol < COL-1; dataCol++)	//omitting last value as it's row code
		{
			firstValue = dataSet1[dataRow1][dataCol];	//getting value from first row
			secondValue = dataSet2[dataRow2][dataCol];	//getting value from second row
			
			result = Math.pow(firstValue - secondValue, 2);
			finalResult = finalResult + result;
		}
		return finalResult;
	}
	
	//checks for nearest distance and compares row code, prints results
	public static double categorise(double[][] dataSet1, double[][]dataSet2) throws IOException
	{
		double correct = 0.0;
		double distance = 0.0;
		
		//for loops iterating through the two data sets
		for(int dataRow1 = 0; dataRow1 < ROW; dataRow1++)
		{
			double nearestDistance = Double.MAX_VALUE;
			double nearestRow = -1.0;
			
			for(int dataRow2 = 0; dataRow2 < ROW; dataRow2++)
			{
				distance = findDistance(dataSet1, dataSet2,dataRow1,dataRow2);	//getting distance from method
				
				if(distance < nearestDistance && nearestDistance > 0) //checking if shortest distance
				{
					nearestDistance = distance;
					nearestRow = dataSet2[dataRow2][COL-1];	//setting row code from the shortest distance row
				}
			}
			if(nearestRow == dataSet1[dataRow1][COL-1])	//comparing row code to the original row
			{
				correct = correct+1;
			}
		}
		correct = (correct/ROW) * 100;
		return correct;
	}
	
	//prints final results
	public static void printResults() throws IOException{
		double fold1 = 0.0;
		double fold2 = 0.0;
		double result = 0.0;
		
		//calculating average
		fold1 = categorise(inputValues(FILE1),inputValues(FILE2));
		fold2 = categorise(inputValues(FILE2),inputValues(FILE1));
		result = (fold1 + fold2) / 2;
		
		System.out.println("Correct: " + result + "%");
	}
}