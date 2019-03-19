package com.cin_damasi.app.chinese_checker_package;

import javax.swing.JOptionPane;


import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Random;


public class Util
{
    public static void infoBox(String titleBar, String infoMessage)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }


    public static int[][] read2DArrayFromTextFile(String filePath, int rowCount, int columnCount)
    {
        File file = new File(filePath);

        //  check if file exists and its not a directory
        if (!file.exists() || file.isDirectory())
        {
            return null;
        }

        //  check if size of file makes sense
        //currently it must be less than 1 kb
        long fileSize = file.length();
        if (fileSize > 1024)
        {
            return null;
        }

        //  start reading file
        int [][] result = new int[rowCount][columnCount]; 
        //String line = null;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Scanner okuyucu = new Scanner(bufferedReader);

            int lineCounter = 0;
            while (okuyucu.hasNextLine())
            {
                //  if file has too many lines
                if (lineCounter >= rowCount)
                {
                    System.out.println(Util.class.getSimpleName() + " - given file " + filePath + " has more lines than specified amount!");
                    okuyucu.close();
                    return null;
                }

                for (int i = 0; i < columnCount; ++i)
                {
                    result[lineCounter][i] = okuyucu.nextInt();
                }

                //  go to next line
                if (okuyucu.hasNextLine())
                {
                    okuyucu.nextLine();
                }

                ++lineCounter;
            }

            //  TO DO: put this in a finally ???
            okuyucu.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }


        return result;
    }

    public static void write2DArrayToTextFile(String filePath, int[][] arr)
    {
        int rowCount = arr.length;

        File file = new File(filePath);
        if (file.exists())
        {
            System.out.println(Util.class.getSimpleName() + " - write2DArrayToTextFile - " 
                                +"given file " + filePath + " already exists!");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            for (int i = 0; i < rowCount; ++i)
            {
                //  convert row to string
                StringBuilder sb = new StringBuilder();
                int columnCount = arr[i].length;
                for (int j = 0; j < columnCount; ++j)
                {
                    sb.append(Integer.toString(arr[i][j]));
                    sb.append(" ");
                }

                //  write current line of numbers to file
                try {
                    writer.write(sb.toString());
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void print2DArray(int[][] arr)
    {
        int rowCount = arr.length;
        for (int r = 0; r < rowCount; ++r)
        {
            int columnCount = arr[r].length;
            for (int c = 0; c < columnCount; ++c)
            {
                System.out.print(arr[r][c] + " ");
            }
            System.out.print(System.lineSeparator());
        }
    }



    
    private static int random64Counter = 0;
    private static Random random64_1 = new Random();
    private static Random random64_2 = new Random();
    public static long getRandomLong()
    {
        ++random64Counter;
        if (random64Counter == 0)
        {
            random64_1 = new Random();
        }

        long randomLong = random64_1.nextLong() ^ random64_2.nextLong() << 1;
        return randomLong;
    }

    public static void main(String args[])
    {
        //  create a dummy 2d array
        int[][] arr = new int[8][8];
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                arr[i][j] = j;
            }
        }

        //  write it to file
        String filePath = "asd.txt";
        write2DArrayToTextFile(filePath, arr);

        //  read array back
        int [][] readed = read2DArrayFromTextFile(filePath, 8, 8);

        //  print it
        System.out.println("Written array:");
        print2DArray(arr);
        System.out.println("Readed array:");
        print2DArray(readed);
    }
}