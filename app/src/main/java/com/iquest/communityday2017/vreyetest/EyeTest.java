package com.iquest.communityday2017.vreyetest;

/**
 * @author andrei.hegedus
 */

public class EyeTest {

    // http://www.dizziness-and-balance.com/practice/images/equipment/die%20chart.2.gif
    private char[][] tests = new char [10][];

    {
        tests[0] = new char[]{'F', 'P'};
        tests[1] = new char[]{'T', 'O', 'Z'};
        tests[2] = new char[]{'L', 'P', 'E', 'D'};
        tests[3] = new char[]{'P', 'E', 'C', 'F', 'D'};
        tests[4] = new char[]{'E', 'D', 'F', 'C', 'Z', 'P'};
        tests[5] = new char[]{'F', 'E', 'L', 'O', 'P', 'Z', 'D'};
        tests[6] = new char[]{'D', 'E', 'F', 'P', 'O', 'T', 'E', 'C'};
        tests[7] = new char[]{'L', 'E', 'F', 'O', 'D', 'P', 'C', 'T'};
        tests[8] = new char[]{'F', 'D', 'P', 'L', 'T', 'C', 'E', 'O'};
        tests[9] = new char[]{'P', 'E', 'Z', 'O', 'L', 'C', 'F', 'T', 'D'};
    }

    public char[] getRow(int nr) {
        if (nr >= 0 && nr <= 9) {
            return tests[nr];
        } else {
            throw new IllegalArgumentException("No such row: " + nr);
        }
    }

    public String getRowAsString(int nr){
        return new String(getRow(nr));
    }

    public int verify(String row, int rowNr){
        row = row.trim().replace(" ", "").toUpperCase();
        String actualRow = getRowAsString(rowNr);
        if(row.length() > actualRow.length()){
            return 0;
        }
        int correct = 0;
        for(int i = 0; i < row.length();i++){
            if(actualRow.charAt(i)==row.charAt(i)){
                correct++;
            }
        }
        return correct * 100 / actualRow.length();
    }
}
