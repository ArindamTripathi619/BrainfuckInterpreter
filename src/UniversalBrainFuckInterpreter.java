import java.io.*;
import javax.swing.*;
import java.awt.*;
class UniversalBrainFuckInterpreter extends GifOptionPane{
    public static String inputFilePath = "";
    static String correctPath(String s){
        s = s.replace((""+((char)92)),("/"));
        if (s.charAt(0)=='"'||s.charAt(s.length()-1)=='"') {
            if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                s = s.substring(1, s.length() - 1);
            } else if (s.charAt(0) == '"') {
                s = s.substring(1);
            }else {
                s = s.substring(0,s.length()-1);
            }
        }
        return s;
    }
    static String input(){
        StringBuilder str= new StringBuilder();
        String[] options = {"Same Directory as the interpreter", "Other Directory"};
        int choice = JOptionPane.showOptionDialog(null, "Choose where the BrainFuck File is :", "Option Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
        if (choice == 0) {
            inputFilePath = (String) JOptionPane.showInputDialog(null, "Enter the input file name : ", "Input Dialog", JOptionPane.QUESTION_MESSAGE, icon, null, null);
        }else{
            inputFilePath = (String) JOptionPane.showInputDialog(null, "Enter the input file name along with the path : ", "Input Dialog", JOptionPane.QUESTION_MESSAGE, icon, null, null);
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
    static String outputPath(String inputFilePathThis){
        String outputFilePath;
        String[] options = {"Same Directory as the interpreter","Same Directory as the input file" , "Other Directory"};
        int choice = JOptionPane.showOptionDialog(null, "Choose where the Output File Should be :", "Option Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
        if (choice == 0) {
            outputFilePath = (String) JOptionPane.showInputDialog(null, "Enter the output file name : ", "Input Dialog", JOptionPane.QUESTION_MESSAGE, icon, null, null);
        } else if (choice == 1) {
            outputFilePath = parentPath(inputFilePathThis)+(String) JOptionPane.showInputDialog(null, "Enter the output file name : ", "Input Dialog", JOptionPane.QUESTION_MESSAGE, icon, null, null);
        }
        else{
            outputFilePath = (String) JOptionPane.showInputDialog(null, "Enter the output file name along with the path : ", "Input Dialog", JOptionPane.QUESTION_MESSAGE, icon, null, null);
        }
        if(outputFilePath.charAt(0)=='"'&&outputFilePath.charAt(outputFilePath.length()-1)=='"'){
            outputFilePath = outputFilePath.substring(1,outputFilePath.length()-1);
        }
        return correctPath(outputFilePath);
    }
    static void writeAndOpenOutputFile(String input, String output)throws IOException{
        try{
            FileWriter fw=new FileWriter(output);
            fw.write(interpret(input));
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
    static void thanks(String str){
        JLabel label = new JLabel(thanksIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        Object[] exitOptions = {"Exit"};
        JOptionPane.showOptionDialog(null, label, str, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, exitOptions, exitOptions[0]);
    }
    public static void main(String[] args) throws IOException{
        String[] options = {"Continue", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Choose :", "Welcome To BrainFuck Interpreter !",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options[0]);
        if(choice == 0){
            String inputCode = input(); // this is just the .bf code
            String outputFilePathMain = outputPath(correctPath(inputFilePath)); // outputPath() returns the corrected path
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Function 1
                    if(inputCode.length()>1000){
                        waitAnimation(112);
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Function 2
                    try {
                        writeAndOpenOutputFile(inputCode, outputFilePathMain);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread1.start();
            thread2.start();
        }else{
            thanks("Thanks For Using My Program !");
        }
    }
}

//https://youtu.be/h68WlAn_Vfg
//https://icons8.com/preloaders/
