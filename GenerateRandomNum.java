package dn.task2.atalay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/*=============================================================================
 |   Assignment:  Design Algorithm (Random Number Generation)
 |       Author:  [Atalay Özkan (matalayozkan@gmail.com)]
 |
 |     Due Date:  [2021/11/19 01:40AM]
 |
 |  Description:  [Design random number generator with some rules
 |                Input:
 |                  a) inputDigit: Indicates how many numbers are able to generate.
 |                  
 |                Random numbers:
 |                  Digits are 0-9. Total count of digits will be any positive number.
 |			        Count difference of each digit must always equal or one.
 |			        Value difference of two digits next to each other must be always 
 | 			        higher than 1.
 |                  
 | 		         Example:
 |                  Total count: 23
 |			        random digits: 68395627408491506273941
 |        		    count of each digit: 2x (0,1,2,3,5,7,8), 3x (4,6,9)
 |
 |                Print:
 |                  It is defined above example.                  
 |
 |     Language:  [Java and Java Development Kit version is openjdk-17.0.1]
 | Ex. Packages:  [There are not additional requirement]
 |
 | Deficiencies:  [There is not known bug to fix.]
 *=============================================================================*/
 
public class GenerateRandomNum {

    public static void main(String[] args) {

        System.out.print("Total count:");
        Scanner scan = new Scanner(System.in);
        int inputDigit = scan.nextInt();
        generateRandomNumbers(inputDigit);

    }

    public static void generateRandomNumbers(int inputDigit) {

        int mandatoryNum[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int tempArray[] = new int[inputDigit];
        int repeat = tempArray.length / mandatoryNum.length;
        int remain = tempArray.length % mandatoryNum.length;
        int currentNum = 0;

        //Create Repeat Numbers List
        ArrayList<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < mandatoryNum.length; i++) {
            currentNum = i;
            while (currentNum < (i + repeat)) {
                tempArray[currentNum] = mandatoryNum[i];
                currentNum++;
                tempList.add(tempArray[i]);
            }

        }

        //initilize the temporary Array  
        Arrays.fill(tempArray, 0);

        //Generate random numbers for remaining
        int inputRandom = 0;
        ArrayList<Integer> remainRandomList = new ArrayList<>(Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)); //initiliaze list
        for (int i = 0; i < remain; i++) {
            inputRandom = new Random().nextInt(0, mandatoryNum.length);
            while (remainRandomList.contains(inputRandom)) {
                inputRandom = new Random().nextInt(0, mandatoryNum.length);
            }
            remainRandomList.set(i, inputRandom);
            tempArray[inputRandom]++;
        }

        //Merging and Sorting the ArrayList ascending the order and remove -1
        remainRandomList.removeAll(Arrays.asList(-1));
        tempList.addAll(remainRandomList);
        Collections.sort(tempList);

        //Count duplicate number
        ArrayList<Integer> generateRepeatNumCalculate = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            generateRepeatNumCalculate.add(Collections.frequency(tempList, i));
        }

        //Random Numbers are generated by rules
        int index;
        int selectedNumber;
        ArrayList<Integer> generateRandomResultList = new ArrayList<>();
        ArrayList<Integer> deleteRandomTempList = new ArrayList<>();
        ArrayList<Integer> addAfterPickingRandom = new ArrayList<>();
        deleteRandomTempList.addAll(tempList);
        for (int i = 0; i < tempList.size(); i++) {
            try {
                index = new Random().nextInt(0, deleteRandomTempList.size());
            } catch (Exception e) {
                deleteRandomTempList.clear();
                deleteRandomTempList.addAll(tempList);
                i = 0;
                continue;
            }
            selectedNumber = deleteRandomTempList.get(index);
            generateRandomResultList.add(selectedNumber);

            deleteRandomTempList.addAll(addAfterPickingRandom);
            addAfterPickingRandom.clear();

            while (deleteRandomTempList.remove(Integer.valueOf(selectedNumber - 1))) {
                addAfterPickingRandom.add(selectedNumber - 1);
            }
            while (deleteRandomTempList.remove(Integer.valueOf(selectedNumber))) {
                addAfterPickingRandom.add(selectedNumber);
            }
            while (deleteRandomTempList.remove(Integer.valueOf(selectedNumber + 1))) {
                addAfterPickingRandom.add(selectedNumber + 1);
            }
            addAfterPickingRandom.remove(Integer.valueOf(selectedNumber));
        }

        System.out.print("random digits: ");
        for (int digit : generateRandomResultList) {
            System.out.print(digit);
        }
        System.out.println();

        //Create list to print below both repeating and remaining
        ArrayList<String> repeatingOutput = new ArrayList<>();
        ArrayList<String> remainingOutput = new ArrayList<>();
        for (int i = 0; i < generateRepeatNumCalculate.size(); i++) {
            if (generateRepeatNumCalculate.get(i) == repeat) {
                repeatingOutput.add(String.valueOf(i));
            } else {
                remainingOutput.add(String.valueOf(i));
            }
        }

        String output;
        output = repeat + "x (" + String.join(",", repeatingOutput) + "), ";
        output += remain + "x (" + String.join(",", remainingOutput) + ")";

        System.out.print("count of each digit: ");
        System.out.println(output);
    }

}
