package CS284;
import java.util.Arrays;
/* Name: Ayush Misra
 * Pledge: I pledge my honor that I have abided by the Stevens Honor System
 * Date: February 2nd, 2024
 */


public class BinaryNumber {
	private int[] data;
	private int length;
	
 /*Constructor: A constructor BinaryNumber(int length) for creating 
  * a binary number of length length and consisting only of zeros. 
  */	
	public BinaryNumber(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("Length must be a positive interger!");
		}
		int[] data = new int[length];
		this.length = length;
		this.data = data;
		}
	
	 /*Constructor: A constructor BinaryNumber(String str) for creating 
	  * a binary number array. String can only be binary numbers (e.g. 1 and 0).
	  */	
	public BinaryNumber(String str) {
		int[] binary_lst = new int[str.length()];
		for (int i = 0; i < binary_lst.length; i++) { 
			char c = str.charAt(i);
			int n = Character.getNumericValue(c);
			if (n != 0 && n!= 1) { 
				throw new IllegalArgumentException("Length must be a positive interger and must consist of only 0s and 1s!");
			}
			binary_lst[i] = n;
		}
		this.length = binary_lst.length;
		this.data = binary_lst;	
	}  
	//Returns the length of the binary object array
	public int getLength() {
		return length;
	}
	//Returns the digit at a specified index of the array. Must be greater than 0 and less than the length.
	public int getDigit(int index) {
	    if (index >= 0 || index <= this.length) {
	        return data[index];
	    } else {
	        throw new IndexOutOfBoundsException("Index out of bounds: " + index);
	    }
	}
	//Returns the integer array representation of the object for binary number.
	public int[] getInnerArray() {
		return data;
	}
	
	//This method calculates the bitwise or of two BinaryNumbers, the disjunction of each element in the array. Must be equal size.
	public static int[] bwor(BinaryNumber bn1, BinaryNumber bn2) {
    	//Note that both argument BinaryNumbers must be of the same length for the input to be considered valid
    	if (bn1.length != bn2.length) {
    		throw new IllegalArgumentException("Both argument BinaryNumbers must be of the same length for the input to be considered valid");
    	}
    	else {
    		int[] copy = new int[bn1.length];
    		for (int i =0; i<bn1.length; i++) {
    			if (bn1.getDigit(i) == 1 || bn2.getDigit(i) == 1) {
    				copy[i] = 1;
    			}
    			else {
    				copy[i] = 0;
    			}
    		}
    		return copy;
    	}
    }
	
	//This method calculates the bitwise and of the two binary numbers, the conjunction of each element of the array. Must be equal size.
	public static int[] bwand(BinaryNumber bn1, BinaryNumber bn2) {
    	//Note that both argument BinaryNumbers must be of the same length for the input to be considered valid
    	if (bn1.length != bn2.length) {
    		throw new IllegalArgumentException("Both argument BinaryNumbers must be of the same length for the input to be considered valid");
    	}
    	else {
    		int[] copy = new int[bn1.length];
    		for (int i =0; i<bn1.length; i++) {
    			if (bn1.getDigit(i) == 1 && bn2.getDigit(i) == 1) {
    				copy[i] = 1;
    			}
    			else {
    				copy[i] = 0;
    			}
    		}
    		return copy;
    	}
    }
	
	//This method shifts all digits in a BinaryNumber left or right depending on the direction. Direction must be either 1 or
	//-1. The amount must be a positive integer.
	public void bitShift(int direction, int amount) {
        if (direction != 1 && direction != -1) {
            throw new IllegalArgumentException("Direction must be 1 (right) or -1 (left)!");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive integer!");
        }
        
        int left_shift = amount + length;
        int right_shift = length - amount;
        
        if (direction == 1) {
            // Right shift
        	int[] shiftedRight = new int[right_shift];
            for (int i = 0; i < shiftedRight.length; i++) {
            	shiftedRight[i] = data[i];
            } 
            data = shiftedRight; 
        }
     
        else {
        	int[] shiftedLeft = new int[left_shift];
        	for (int i = 0; i < shiftedLeft.length; i++) {
        		if (i < data.length) {
        			shiftedLeft[i] = data[i];
        		}
        		else {
        			shiftedLeft[i] = 0;
        		}
        	}
        	
        	data = shiftedLeft;
        }
    }
	//This methode will add two binary numbers together.
	public void add(BinaryNumber aBinaryNumber) {
	    int maxLength = Math.max(this.length, aBinaryNumber.length);

	    int[] copy = new int[maxLength + 1];  // Increase size to accommodate any potential carry
	    int carry = 0;

	    for (int i = maxLength - 1, j = this.length - 1, k = aBinaryNumber.length - 1; i >= 0; i--, j--, k--) {
	        int digitA = (j >= 0) ? getDigit(j) : 0;
	        int digitB = (k >= 0) ? aBinaryNumber.getDigit(k) : 0;

	        int check = carry + digitA + digitB;

	        if (check == 0) {
	            copy[i + 1] = 0;
	            carry = 0;
	        } else if (check == 1) {
	            copy[i + 1] = 1;
	            carry = 0;
	        } else if (check == 2) {
	            copy[i + 1] = 0;
	            carry = 1;
	        } else if (check == 3) {
	            copy[i + 1] = 1;
	            carry = 1;
	        }
	    }

	    copy[0] = carry;

	    this.data = copy;
	    this.length = copy.length;
	}


    @Override 
    //Returns the string representing the binary number.
    public String toString() {
    	String Binary_to_string = new String();
    	for (int i = 0; i < length; i++) {
    		Binary_to_string += getDigit(i);
    	}
    	return Binary_to_string;
    }
    //This method converts the binary representation of a number into decimal form.
    public int toDecimal() {
        int decimal = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 1) {
                decimal += Math.pow(2, data.length - i - 1);
            }
        }
        return decimal;
    }
    

}


	
	
	
	
	
	
	
	
	
	
	
	
	
	