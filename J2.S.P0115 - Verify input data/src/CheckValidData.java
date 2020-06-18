
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author Huynh Lam - SE62917
 */
public class CheckValidData {

    // --- Verify student code ---
    // max length is 10
    // not contains special characters (@, #, $)
    // not contains space
    public boolean ckStudentCode(String studentCode) {
        if (!studentCode.matches("^[0-9]{1,8}")) {
            return false;
        } else if (studentCode.length() > 10) {
            return false;
        } else if (studentCode.contains(" ")) {
            return false;
        }
        return true;
    }

    // --- Verify student name ---
    // max length is 30
    public boolean ckStudentName(String studentName) {
        if (!studentName.matches("[A-Za-z ]*")) {
            return false;
        } else if (studentName.length() > 30) {
            return false;
        }
        return true;
    }

    // --- Verify birth date ---
    // format birth date - MM/DD/YYYY
    public boolean ckValidBirthDate(String birthDate) {
        if (birthDate.length() != 10) {
            return false;
        }
        if (birthDate.charAt(0) == '0' && birthDate.charAt(1) == '0') {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(0))) {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(1))) {
            return false;
        }
        if (birthDate.charAt(2) != '/') {
            return false;
        }
        if (birthDate.charAt(3) == '0' && birthDate.charAt(4) == '0') {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(3))) {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(4))) {
            return false;
        }
        if (birthDate.charAt(2) != '/') {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(6))) {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(7))) {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(8))) {
            return false;
        }
        if (!Character.isDigit(birthDate.charAt(9))) {
            return false;
        }
        return true;
    }

    public boolean ckBirthDate(String birthDate) {
        String sub1 = birthDate.substring(0, 2);
        String sub2 = birthDate.substring(3, 5);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String date = birthDate.substring(6, 10);
        if (Integer.parseInt(date) > year || (year - Integer.parseInt(date) < 19)) {
            return false;
        } else if (Integer.parseInt(date) == year) {
            if (Integer.parseInt(sub2) > month) {
                return false;
            }
        } else if (Integer.parseInt(date) <= year) {
            if (Integer.parseInt(sub2) <= month) {
                if (Integer.parseInt(sub1) > day) {
                    return false;
                }
            }
        }
        return true;
    }

    // --- Verify exist date ---
    public boolean ckDateIsExist(String date) {
        String sub1 = date.substring(0, 2);
        String sub2 = date.substring(3, 5);
        if (Integer.parseInt(sub1) > 12) {
            return false;
        }
        if (Integer.parseInt(sub2) > 31) {
            return false;
        }
        if (Integer.parseInt(sub1) == 1) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        if (Integer.parseInt(date.substring(6, 10)) % 4 == 0) {
            if (Integer.parseInt(sub1) == 2) {
                if (Integer.parseInt(sub2) >= 29) {
                    return false;
                }
            }
        } else if (Integer.parseInt(sub1) == 2) {
            if (Integer.parseInt(sub2) >= 28) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 3) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 4) {
            if (Integer.parseInt(sub2) >= 31) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 5) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 6) {
            if (Integer.parseInt(sub2) >= 31) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 7) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 8) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 9) {
            if (Integer.parseInt(sub2) >= 31) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 10) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 11) {
            if (Integer.parseInt(sub2) >= 31) {
                return false;
            }
        }
        if (Integer.parseInt(sub1) == 12) {
            if (Integer.parseInt(sub2) >= 32) {
                return false;
            }
        }
        return true;
    }

    // --- Verify phone number ---
    // max length is 15
    // contain numeric characters only (0 – 9)
    public boolean ckPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^[0-9]{1,15}")) {
            return false;
        }
        return true;
    }

    // --- Verify email ---
    // max length is 30
    // contain only one “@” character
    // do not contain special characters (!, #, $)
    public boolean checkEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+([a-zA-Z0-9]+[.]{1})*+[a-zA-Z0-9]+$")) {
            return false;
        } else if (email.length() > 30) {
            return false;
        }
        return true;
    }
}
