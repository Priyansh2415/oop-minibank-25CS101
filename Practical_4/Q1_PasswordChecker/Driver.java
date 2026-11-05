public class Driver {
    static String mark(boolean ok) { return ok ? "PASS" : "FAIL"; }

    public static void main(String[] args) {
        // (c) test several passwords
        String[] passwords = { "abc", "abcdefgh", "Abcdefgh1", "abcd1234", "Abcd1234!" };
        for (String pw : passwords) {
            System.out.println("Password : " + pw);
            System.out.println("  Length >= 8 : " + mark(PasswordChecker.hasLength(pw)));
            System.out.println("  Uppercase   : " + mark(PasswordChecker.hasUpper(pw)));
            System.out.println("  Digit       : " + mark(PasswordChecker.hasDigit(pw)));
            System.out.println("  Special char: " + mark(PasswordChecker.hasSpecial(pw)));
            System.out.println("  Strength    : " + PasswordChecker.strength(pw) + "\n");
        }
    }
}
