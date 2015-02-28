import java.io.BufferedReader;
import java.io.FileReader;
//Note:  Will need to change the path to the input file in try block in main function
public class Solution {

	/*
	 * The solution to this problem is a greedy algorithm.
	 * Step 1:  Sort the agencies in order of decreasing money values (highest first)
	 * Step 2:  Starting on the last day, choose the highest possible agency for that day.
	 * 			ie: Highest Agency with deadline >= that day.
	 * Step 3:  Repeat Step 2 in decreasing days, cannot choose an agency that has already been chosen.
	 */
	static Agency[] Algorithm(Agency[] sortedArr, int numDays)
	{
		Agency[] chosenArr = new Agency[numDays];
		
		for(int i = numDays; i>=1; i--)	//Starting on last day, decreasing days.
		{
			for(int j = 0; j < sortedArr.length; j++)
			{
				if(sortedArr[j].deadline >= i && !sortedArr[j].chosen)	//Pick highest possible for that day (don't need to search whole list,
				{														// just first possible that hasn't been chosen because of sorting)
					sortedArr[j].chosen = true;
					chosenArr[i-1] = sortedArr[j];
					break;
				}
			}
		}
		return chosenArr;
	}

	public static void main(String[] args) {

		Agency[] agencyArr;
		Agency[] chosenArr;
		int numDays;
		/************ Will need to change filePath to point to file!!!!! *****************/
		try(BufferedReader inputFile = new BufferedReader(new FileReader(/*File Path Here!*/"")))
		{
			//Parse through file, first line contains number of Days and number of agencies
			String line = inputFile.readLine();
			int[] info = parseFile(line);
			int numAgencies = info[1];
			agencyArr = new Agency[numAgencies];
			numDays = info[0];
			chosenArr = new Agency[numDays];
			
			//All other lines contain Agency Data
			for(int i = 0; i < numAgencies; i++)
			{
				line = inputFile.readLine();
				int[] agencyInfo = parseFile(line);
				int deadline = agencyInfo[0];
				int value = agencyInfo[1];
				agencyArr[i] = new Agency(deadline, value, i+1);
			}
			
			agencyArr = merge_sort(agencyArr);			//Sort in decreasing value
			chosenArr = Algorithm(agencyArr,numDays);	//Run algorithm in sorted array
			int sum = 0;
			//Print out solution
			System.out.println("Schedule:");
			for(int i = 0; i < chosenArr.length; i++)
			{
				System.out.print("Day " + (i + 1) + ": $" + chosenArr[i].value + "\t");
				System.out.print("Deadline: " + chosenArr[i].deadline + "\t");
				System.out.println("Agency # " + chosenArr[i].num);
				sum += chosenArr[i].value;
			}

			System.out.println("Total Funding: $" + sum);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	//Parse a line from the file and returns the two integers (one from each column)
	//in an array of 2 integers
	static int[] parseFile(String line) throws NumberFormatException
	{
		String col1 = "";
		String col2 = "";
		int i = 0;
		for(i = 0; !((line.charAt(i) == ' ') || (line.charAt(i) == '\t')); i++)
		{
			col1 = col1.concat(line.charAt(i) + "");
		}
		while((line.charAt(i) == ' ') || (line.charAt(i) == '\t'))
		{
			i += 1;
		}
		for(; i < line.length(); i++)
		{
			col2 = col2.concat(line.charAt(i) + "");
		}
		int intCol1 = Integer.parseInt(col1);
		int intCol2 = Integer.parseInt(col2);

		int[] retArr = {intCol1, intCol2};

		return retArr;

	}
	
	/******** Merge Sort Algorithm functions:  Merge Sort sorts as Highest Value first *****************/
	static Agency[] merge_sort(Agency[] list)
	{
		if(list.length <= 1)
			return list;
		
		int middle = list.length/2;
		Agency[] left = new Agency[middle];
		Agency[] right = new Agency[list.length - middle];
		
		for(int i = 0; i < middle; i++)
		{
			left[i] = list[i];
		}
		for(int i = middle; i < list.length; i++)
		{
			right[i- middle] = list[i];
		}
		
		left = merge_sort(left);
		right = merge_sort(right);
		
		return merge(left, right);
	}
	
	static Agency[] merge(Agency[] left, Agency[] right)
	{
		Agency[] mergeList = new Agency[left.length + right.length];
		int i = 0;
		int j = 0;
		int k = 0;
		
		while(i < left.length || j < right.length)
		{
			if(i < left.length && j < right.length)
			{
				if(left[i].value >= right[j].value)
				{
					mergeList[k] = left[i];
					i++;
				}
				else
				{
					mergeList[k] = right[j];
					j++;
				}
			}
			else if(i < left.length)
			{
				mergeList[k] = left[i];
				i++;
			}
			else	//j < right.length
			{
				mergeList[k] = right[j];
				j++;
			}
			k++;
		}
		
		return mergeList;
	}
}

