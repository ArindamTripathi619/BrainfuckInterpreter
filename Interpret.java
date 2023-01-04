 public class Interpret {
    public static int ptr; // Data pointer
    /* Max memory limit. It is the highest number which
       can be represented by an unsigned 16-bit binary
       number. Many computer programming environments
       beside brainfuck may have predefined
       constant values representing 65535.*/
    public static final int length = 65535;
    // Array of byte type simulating memory of max 65535 bits from 0 to 65534.
    public static final byte[] memory = new byte[length];
    // Interpreter function which accepts the code as a string parameter
    public static String interpret(String s) {
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
}
