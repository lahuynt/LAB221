/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.controllers;

import static java.lang.Math.sqrt;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JFrame;
import sc.gui.SimpleCalculator;

/**
 *
 * @author Huynh Lam - SE62917
 */
public class CalculatorController {

    SimpleCalculator c;
    BigDecimal result = new BigDecimal("0");
    BigDecimal Dnum = new BigDecimal("0");
    String operator = "";
    Boolean newNumber = true;

    public CalculatorController(SimpleCalculator c) {
        this.c = c;
    }

    //return interger format if input number is integer
    public String FixNumber(BigDecimal currentNum) {
        String s = currentNum.toString();
        //go thought s to find if it has dot
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                String newS = "";
                int newL = s.length();
                //go through s to delete all last 0 digit after dot
                for (int j = s.length() - 1; j > 1; j--) {
                    //while last digit is still 0
                    if (s.charAt(j) == '0') {
                        //update new length of s
                        newL = j;
                    } //next digit is real
                    else {
                        break;
                    }
                }
                //all digit after dot is 0.so dot is useless
                if (s.charAt(newL - 1) == '.') {
                    newL--;
                }
                //go through s to save new string
                for (int j = 0; j < newL; j++) {
                    newS = newS + s.charAt(j);
                }
                s = newS;
                break;
            }
        }
        return s;
    }

    // Button All Clear
    // Reset everything back to 0
    public String ButtonClear() {
        Dnum = new BigDecimal("0");
        result = new BigDecimal("0");
        operator = "";
        newNumber = true;
        return ("0");
    }

    // Button Memory Clear
    // Clear all numbers in memory
    public void ButtonMC() {
        Dnum = new BigDecimal("0");
        newNumber = true;
    }

    // Button Memory Recall
    // Return number which in memory
    public String ButtonMR() {
        result = Dnum;
        newNumber = true;
        return (FixNumber(Dnum));
    }

    // Button M+
    // Add current number to memory
    // If current display is Error
    // Can't add to memory
    public void ButtonMAdd(String text) {
        if (!text.equals("Error")) {
            Dnum = Dnum.add(new BigDecimal(text));
            newNumber = true;
        }
    }

    // Button M-
    // Substract current number to memory
    // If current display is Error
    // Can't add to memory
    public void ButtonMSub(String text) {
        if (!text.equals("Error")) {
            Dnum = Dnum.subtract(new BigDecimal(text));
            newNumber = true;
        }
    }

    // Button √
    // Square Root the current number
    // If not display Error
    public String ButtonSqrt() {
        // cant square root for number < 0
        if (result.compareTo(new BigDecimal("0")) == -1) {
            return "Error";
        } else {
            Double dnum = sqrt(result.doubleValue());
            result = new BigDecimal(dnum);
            result = result.divide(new BigDecimal("1"), RoundingMode.HALF_EVEN);
            newNumber = true;
            return (FixNumber(result));
        }
    }

    // Button %
    // Divide current number by 100
    public String ButtonPercent() {
        result = result.divide(new BigDecimal("100"));
        newNumber = true;
        return (FixNumber(result));
    }

    // Button 1/x
    // Take 1 then divide by current number
    public String ButtonFlip() {
        // if current number = 0
        // can't divide
        if (result.equals(new BigDecimal("0"))) {
            return "Error";
        } else {
            BigDecimal One = new BigDecimal("1");
            result = One.divide(result, 12, RoundingMode.HALF_EVEN);
            newNumber = true;
            return (FixNumber(result));
        }
    }

    // Button .
    // Add a dot to current number
    // If there was a dot, it won't add another dot
    public String ButtonDot(String text) {
        // There was a input number
        if (!newNumber) {
            newNumber = false;
            // go through text to find dot
            for (int i = 0; i < text.length(); i++) //there was a dot in number
            {
                if (text.charAt(i) == '.') {
                    return text;
                }
            }
            //there wasn't any dot in number
            return text + '.';
        } //current value of input number is 0
        else {
            newNumber = false;
            return "0.";
        }
    }

    // Button +/-
    // Swap current number to be positive or negative
    public String ButtonSwap() {
        result = new BigDecimal("0").subtract(result);
        newNumber = true;
        return (FixNumber(result));
    }

    public String addDigit(String text, String s) {
        //input digit will replace current text
        if (newNumber) {
            // after operator = result will be reset so next operation
            // won't take previous operation result
            if (operator.equals("=")) {
                result = new BigDecimal("0");
            }
            newNumber = false;
            return (FixNumber(new BigDecimal(s)));
        } else if ((text).equals("0")) {
            //current text is 0, the first input digit will replace it
            return (FixNumber(new BigDecimal("s")));
        } else {
            //add 1 digit to last of the text
            return (text + s);
        }
    }

    public String Calculate(String text, String currentOp) {
        String output = text;
        if (!newNumber) {
            BigDecimal num = new BigDecimal(text);
            switch (operator) {
                case "+":
                    result = result.add(num);
                    break;
                case "-":
                    result = result.subtract(num);
                    break;
                case "x":
                    result = result.multiply(num);
                    break;
                case "/":
                    //can't divide by 0
                    if (num.equals(new BigDecimal("0"))) {
                        output = "Error";
                    } else {
                        result = result.divide(num, 12, RoundingMode.HALF_EVEN);
                    }
                    break;
                default:
                    result = new BigDecimal(text);
                    break;
            }
            if (!output.equals("Error")) {
                output = FixNumber(result);
            }
        }
        //save new operator to calculate next opression
        operator = currentOp;
        newNumber = true;
        return output;
    }

    public void frameSetting(JFrame frame) {
        frame.setLocationRelativeTo(null);
    }
}
