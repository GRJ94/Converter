/*
 * Name: Glendys Rodriguez 
 * File name: Zipcode
 */

package Part2;
/*
 * The Class Zipcode to convert from zip code to bar code and vice versa.
 */
public class Zipcode {

   /* 
    * binary rep for each digit (0 to 9)
    */
    private static final String[] BINARIES = { "11000", "00011", 
       "00101", "00110", "01001", "01010", "01100", "10001","10010", "10100" };

   /*
    * Gets the barcode
    *
    * @param zip the zip code
    * @return the barcode
    */
    public static String getBarcode(String zip) {
        int sum = 0;

        String barcode = "";

        for (int i = zip.length() - 1; i >= 0; i--) {
                int digit = zip.charAt(i) - '0';
                barcode = toBar(digit) + barcode;
                sum += digit;
        }

        // calc check digit
        int checkdigit = 0;
        if (sum / 10 != 0) {
                checkdigit = 10 - sum % 10;
        }

        barcode += toBar(checkdigit);
        return "|" + barcode + "|";
}

   /*
    * Gets the Zip code
    *
    * @param bar the bar code
    * @return the ZIP code
    */
    public static String getZIPcode(String bar) {
        // invalid bar code length of doesnt have full bars at either end
        if (bar.length() != 32 || bar.charAt(0) != '|' || 
                bar.charAt(bar.length() - 1) != '|') {
                return "Error: bar code is not valid!";
        }

        int countDigits = 0, sum = 0;

        int zip = 0;

        // for each digit
        for (int i = 1; i < bar.length() - 1; i += 5) {

                // get digit
                int digit = toDigit(bar.substring(i, i + 5));
                sum += digit;
                countDigits++;

                // to avoid adding checkdigit
                if (countDigits <= 5) {
                        zip = zip * 10 + digit;
                }
        }

        // verify sum is divisible by 10
        if (sum % 10 != 0) {
            return ("Error: bar code is not valid!");
        }

        return String.format("%05d", zip);

}
   /* 
    * Converts one digit to bar
    *
    * @param digit the digit
    * @return the string
    */
    private static String toBar(int digit) {
        String binary = BINARIES[digit];
        String bar = "";
        for (int i = 0; i < binary.length(); i++) {
                bar += binary.charAt(i) == '0' ? ":" : "|";
        }
        return bar;
}
   /* 
    * Converts bar to one digit
    *
    * @param bars the bars
    * @return the int
    */
    private static int toDigit(String bars) {
        String binary = "";
        for (int i = 0; i < bars.length(); i++) {
                binary += bars.charAt(i) == ':' ? 0 : 1;
        }

        for (int i = 0; i < BINARIES.length; i++) {
                if (BINARIES[i].equals(binary))
                        return i;
        }

        return -1;
}
   /* 
    * The main method to test the application
    * 
    * @param args the arguments
    */
    public static void main(String[] args) {

        String[] zipCodes = { "12345", "33199", "20500", 
            "10001", "99950", "00501" };
        for (int i = 0; i < zipCodes.length; i++) {
            System.out.println(zipCodes[i] + " - " + getBarcode(zipCodes[i]));
        }

        System.out.println();

        String[] barCodes = { "|::||::|:|:|::|:::|:||::|::|::||", 
            "||::|::|:|:||::::|::|:|::||:|::|",
            "||:::|::|:|::|:|:::||:||:::|::||",
            "||:|:::|::|::|:|||::::|||:||:::|",
            "|:|::||:|::|:::|::||::|::|::||:|" };

        for (int i = 0; i < barCodes.length; i++) {
            System.out.println(barCodes[i] + " - " + getZIPcode(barCodes[i]));
        }
    }

}