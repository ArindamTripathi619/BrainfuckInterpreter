package com.company;
import java.io.*;
import javax.swing.*;
import java.awt.*;
class UniversalBrainFuckCompiler extends GifOptionPane{
    private static int ptr; // Data pointer
    public static String inputFilePath = "";
    /* Max memory limit. It is the highest number which
       can be represented by an unsigned 16-bit binary
       number. Many computer programming environments
       beside brainfuck may have predefined
       constant values representing 65535.*/
    private static final int length = 65535;
    // Array of byte type simulating memory of max 65535 bits from 0 to 65534.
    private static final byte[] memory = new byte[length];
    // Interpreter function which accepts the code as a string parameter
    private static String interpret(String s) {
        String output = "";
        int c = 0;
        // Parsing through each character of the code
        for (int i = 0; i < s.length(); i++) {
            // BrainFuck is a tiny language with only eight instructions. In this loop we check
            // and execute all those eight instructions

            // > moves the pointer to the right
            if (s.charAt(i) == '>') {
                if (ptr == length - 1)//If memory is full
                    ptr = 0;//pointer is returned to zero
                else
                    ptr++;
            }
            // < moves the pointer to the left
            else if (s.charAt(i) == '<') {
                if (ptr == 0) // If the pointer reaches zero
                    // pointer is returned to rightmost memory
                    // position
                    ptr = length - 1;
                else
                    ptr--;
            }
            // + increments the value of the memory
            // cell under the pointer
            else if (s.charAt(i) == '+')
                memory[ptr]++;
                // - decrements the value of the memory cell
                // under the pointer
            else if (s.charAt(i) == '-')
                memory[ptr]--;
                // . outputs the character signified by the
                // cell at the pointer
            else if (s.charAt(i) == '.'){
                output+=(char)(memory[ptr]);
            }
            // , inputs a character and store it in the
            // cell at the pointer
            else if (s.charAt(i) == ',')
                memory[ptr] = (byte)(s.charAt(0));
                // [ jumps past the matching ] if the cell
                // under the pointer is 0
            else if (s.charAt(i) == '[') {
                if (memory[ptr] == 0) {
                    i++;
                    while (c > 0 || s.charAt(i) != ']') {
                        if (s.charAt(i) == '[')
                            c++;
                        else if (s.charAt(i) == ']')
                            c--;
                        i++;
                    }
                }
            }
            // ] jumps back to the matching [ if the
            // cell under the pointer is nonzero
            else if (s.charAt(i) == ']') {
                if (memory[ptr] != 0) {
                    i--;
                    while (c > 0 || s.charAt(i) != '[') {
                        if (s.charAt(i) == ']')
                            c++;
                        else if (s.charAt(i) == '[')
                            c--;
                        i--;
                    }
                    i--;
                }
            }
        }
        return output;
    }
    static String correctPath(String s){
        String s1 = ""+((char)92);
        return s.replace(s1,(s1+s1));
    }
    static String input(){
        String str="";
        String[] options = {"Same Directory as this file", "Other Directory"};
        int choice = JOptionPane.showOptionDialog(null, "Choose where the Brainfuck File is :", "Option Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            inputFilePath = JOptionPane.showInputDialog("Enter the input file name : ");
        }else{
            inputFilePath = JOptionPane.showInputDialog("Enter the input file name along with the path : ");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(correctPath(inputFilePath)))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                str += sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    static String parentPath(String input){
        int lastIndex = input.lastIndexOf('/');
        return input.substring(0, lastIndex+1);
    }
    static String outputPath(){
        //String parentPath = parentPath(correctPath(inputFilePath));
        String outputFilePath;
        String[] options = {"Same Directory as the compiler file", "Other Directory"};
        //String [] options2 = {"Same Directory as the input file", "Other Directory"};
        int choice = JOptionPane.showOptionDialog(null, "Choose where the Output File Should be :", "Option Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            outputFilePath = JOptionPane.showInputDialog("Enter the output file name : ");
        } //else if (choice == 1) {
            //outputFilePath = parentPath+JOptionPane.showInputDialog("Enter the output file name : ");
        //}
        else{
            outputFilePath = JOptionPane.showInputDialog("Enter the output file name along with the path : ");
        }
        return correctPath(outputFilePath);
    }
    static void writeAndOpenOutputFile(String input, String output)throws IOException{
        try{
            FileWriter fw=new FileWriter(output);
            fw.write(interpret(input));//////
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }

        File file = new File(output);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        String[] options = {"Continue", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Choose :", "Welcome To BrainFuck Compiler !",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(choice == 0){
            String input = input();
            String output = outputPath();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Function 1
                    if(input.length()>1000){
                        waitAnimation(110);
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Function 2
                    try {
                        writeAndOpenOutputFile(input,output);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread1.start();
            thread2.start();
        }else{
            System.exit(0);
        }
    }
}

//https://youtu.be/h68WlAn_Vfg
