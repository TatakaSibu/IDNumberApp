import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your South African ID number:");
        String idNumber = scanner.nextLine();

        try {
            if (isValidIDNumber(idNumber)) {
                System.out.println("Valid ID number.");
                int year = getYearOfBirth(idNumber);
                int month = getMonthOfBirth(idNumber);
                int day = getDayOfBirth(idNumber);
                char gender = getGender(idNumber);
                String citizenship = getCitizenship(idNumber);

                System.out.println("Year of Birth: " + year);
                System.out.println("Month of Birth: " + month);
                System.out.println("Day of Birth: " + day);
                System.out.println("Gender: " + gender);
                System.out.println("Citizenship: " + citizenship);
            } else {
                throw new InvalidIDNumberException("Invalid ID number.");
            }
        } catch (InvalidIDNumberException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    public static boolean isValidIDNumber(String idNumber) {
        if (idNumber.length() != 13) {
            return false;
        }

        char checksum = calculateChecksum(idNumber.substring(0, 12));
        return idNumber.charAt(12) == checksum;
    }

    public static char calculateChecksum(String idNumber) {
        int sum = 0;
        int[] weights = {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2};

        for (int i = 0; i < idNumber.length(); i++) {
            int digit = Character.getNumericValue(idNumber.charAt(i));
            int weighted = digit * weights[i];
            sum += (weighted >= 10) ? weighted - 9 : weighted;
        }

        int remainder = sum % 10;
        int checksum = (remainder == 0) ? 0 : 10 - remainder;

        return Character.forDigit(checksum, 10);
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

    public static int getMonthOfBirth(String idNumber) {
        return Integer.parseInt(idNumber.substring(2, 4));
    }

    public static int getDayOfBirth(String idNumber) {
        return Integer.parseInt(idNumber.substring(4, 6));
    }

    public static char getGender(String idNumber) {
        int genderDigit = Integer.parseInt(idNumber.substring(6, 7));
        return (genderDigit < 5) ? 'F' : 'M';
    }

    public static String getCitizenship(String idNumber) throws InvalidIDNumberException {
        int citizenshipDigit = Integer.parseInt(idNumber.substring(10, 11));
        if (citizenshipDigit == 0) {
            return "SA Citizen";
        } else {
            throw new InvalidIDNumberException("Not a South African citizen.");
        }
    }
}

class InvalidIDNumberException extends Exception {
    public InvalidIDNumberException(String message) {
        super(message);
    }
}
