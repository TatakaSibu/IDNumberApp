import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean validID = false;
        while (!validID) {
            System.out.println("Please enter your ID Number:");                             // SA ID Number
            String idNumber = scanner.nextLine();

            try {
                if (isValidIDNumber(idNumber)) {
                    System.out.println("Valid ID number.");
                    int year = getYearOfBirth(idNumber);
                    String month = getMonthOfBirth(idNumber);
                    int day = getDayOfBirth(idNumber);
                    String gender = checkGender(idNumber);
                    String citizenship = checkCitizenship(idNumber);

                    System.out.println("Year of Birth: " + year +
                            "\nMonth of Birth: " + month +
                            "\nDay of Birth: " + day +
                            "\nGender: " + gender +
                            "\nCitizenship: " + citizenship);
                    validID = true;                                             // Set validID to true to exit the loop
                } else {
                    throw new InvalidIDNumberException("Invalid ID number.");
                }
            } catch (InvalidIDNumberException e) {
                System.out.println("Message: " + e.getMessage());
                                                                                    // Continue to prompt for ID number
            }
        }

        scanner.close();
    }

    public static boolean isValidIDNumber(String idNumber) {
        return idNumber.length() == 13;
    }

    public static int getYearOfBirth(String idNumber) {
        int year = Integer.parseInt(idNumber.substring(0, 2));
        int century = Integer.parseInt(idNumber.substring(0, 2));

        if (century >= 0 && century <= 21) {
            return 2000 + year;
        } else {
            return 1900 + year;
        }
    }
    public static String getMonthOfBirth(String idNumber) {
        int month = Integer.parseInt(idNumber.substring(2, 4));
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Invalid month";
        };
    }
    public static int getDayOfBirth(String idNumber) {
        return Integer.parseInt(idNumber.substring(4, 6));
    }

    public static String checkGender(String idNumber) {                     // Extract the next 4 digits representing gender
        String genderDigits = idNumber.substring(6, 10);
        int genderValue = Integer.parseInt(genderDigits);                  // Convert the genderDigits string to an integer
        int threshold = 5000;                                             // Define the threshold to differentiate between male and female

        if (genderValue < threshold) {                                   // Check if the gender value is less than the threshold
            return "Female";
        } else {
            return "Male";
        }
    }
    public static String checkCitizenship(String idNumber) {
        // Extract the citizenship digit
        char citizenshipDigit = idNumber.charAt(12);

        // Check citizenship
        if (citizenshipDigit == '0') {
            return "SA citizen";
        } else if (citizenshipDigit == '1') {
            return "Permanent resident";
        } else {
            return "Unknown citizenship";
        }
    }

}
class InvalidIDNumberException extends Exception {
    public InvalidIDNumberException(String message) {
        super(message);
    }
}
