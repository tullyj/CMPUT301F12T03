/**
 * The program header goes at the top.
 *
 * Method and class comments are of this comment style. 
 */

import java.io*;

/** 
 * The class gets the same name as the file
 * Class names and function names use CamelCase
 */
public class ClassName {
    // Place all class variables here
    public int intVar = 5;
	
	/**
     * Method comment adhering to javadoc standards.
     * Note: No space between method name and first bracket for parameters. 
     * Also, note the space before the brace.
     */
	public static void main(String[] args) {
    
        // Method variables are declared and initialized at the beginning of the 
        // method. Declared variables should always be initialized. If this 
        // variable may not be needed, then initialize when needed.
        // Exception: for loop iterating integer.
        int num = 0;
    
        // With if statements and loops, there is a space between the command 
        // and the condition.
        while (1) {
            
            // For if, else if, else chaining conditions follow the following:
            if (condition1) {
                System.out.print("condition1");
            } else if (condition2) {
				System.out.print("condition2");
			} else {
				System.out.print("nothing");
			}
            
            // If statements containing multiple line conditions, should be 
            // split into multiple lines with 8 space indentation. Note the
            // operators and braces for a multiple line condition. To avoid
            // ambiguity always bracket the sub conditions (conditions between
            // operators).
            if ((condition1 && condition2) || 
                    (condition3 && condition2) ||
                    (condition1 != condition3)) {
                System.out.print("complicated conditions");
            }
            
            // An if statement that can fit on one line, make it two lines.
            // Note that there are no brackets. This also applies to for/while
            // loops.
            if (condition3) 
                print("condition3");
            // Another example of this with an else.
            if (condition3) 
                System.out.print("condition3");
            else 
                System.out.print("condition5");
            // Examples of loops:
            while(true)
                // do something
            for (int i = 0; i < num; i++)
                // do something
                
            // Regarding for loops, if the index variable is not needed, or you
            // can use a for each loop, use the enhanced for loop.
            for (File f : files)
                fileList.add(f)
             
            // Assignment, boolean, and mathematical operators are always 
            // seperated from expressions by a space.
            float x = 1.0;
            float y = 3.25 * x;
            // When doing a operator on the variable itself: 
            x += 5;
            
            // When doing multiple operations, use a space and be explicit
            // with the operator priorities by using braces.
            x = (x + 1) * (y + 3);
            
            
            // Regarding the format for calling methods parameters are seperated
            // by comma and space, and no space between the first parameter 
            // bracket and method.
            ClassName.method(0, 5 * Time.deltaTime, 0);
            // When a method call line is over the 80 character line, move to
            // next line with first parameter than can be moved to the newline.
            // Also, use 8 space indentation when seperating a single line to
            // multiple lines to fit within the 80 character width limit.
            ClassName.method(0, 5 * Time.deltaTime, 0, asdf, asdf, asdf, 
                    asasdfasdffadfsdf());
            
            // No parentheses around return values, unless needed for explicitly
            // denoting operator priority.
            return x * y;
        }
	}
    
    /** 
     * There is one empty line in between different methods.
     */
    private void anotherMethod() {
        // do something
    }
}
