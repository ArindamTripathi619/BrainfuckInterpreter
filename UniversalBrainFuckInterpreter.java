import java.io.*;
import javax.swing.*;
import java.awt.*;
class UniversalBrainFuckInterpreter extends GifOptionPane{
    public static String inputFilePath = "";
    static String correctPath(String s){
        String s1 = ""+((char)92);
        if(s.charAt(0)=='"'&&s.charAt(s.length()-1)=='"'){
            s=s.substring(1,s.length()-1);
        }
        return s.replace(s1,(s1+s1));
    }
    static String input(){
        StringBuilder str= new StringBuilder();
        String[] options = {"Same Directory as the interpreter file", "Other Directory"};
        int choice = JOptionPane.showOptionDialog(null, "Choose where the Brainfuck File is :", "Option Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            inputFilePath = JOptionPane.showInputDialog("Enter the input file name : ");
        }else{
            inputFilePath = JOptionPane.showInputDialog("Enter the input file name along with the path : ");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(correctPath(inputFilePath)))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                str.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    static String parentPath(String input){
        int lastIndex = input.lastIndexOf('/');
        return input.substring(0, lastIndex+1);
    }
    static String outputPath(){
        //String parentPath = parentPath(correctPath(inputFilePath));
        String outputFilePath;
        String[] options = {"Same Directory as the interpreter file", "Other Directory"};
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
        if(outputFilePath.charAt(0)=='"'&&outputFilePath.charAt(outputFilePath.length()-1)=='"'){
            outputFilePath = outputFilePath.substring(1,outputFilePath.length()-1);
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
        int choice = JOptionPane.showOptionDialog(null, "Choose :", "Welcome To BrainFuck Interpreter !",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(choice == 0){
            String input = input();
            String output = outputPath();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Function 1
                    if(input.length()>1000){
                        waitAnimation(112);
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
//https://icons8.com/preloaders/