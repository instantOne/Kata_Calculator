package com;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) throws UnknownOperatorException,
            IncorrectFormatException, DifferentTypesException,
            NumberRangeException, RomanNegativeException {
        while (true) {
            System.out.println("Введи в формате: A + B или A / B или A - B или A * B");
            Scanner scan = new Scanner(System.in);
            String[] input = scan.nextLine().split(" ");

            if (input.length != 3) {
                throw new IncorrectFormatException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }

            if (!((input[1].equals("+")) || (input[1].equals("-")) ||
                    (input[1].equals("/")) || (input[1].equals("*")))) {
                throw new UnknownOperatorException("Неверный оператор");
            }
            try {
                int a = Integer.parseInt(input[0]);
                try {
                    int b = Integer.parseInt(input[2]);
                    if ((a >= 0) && (a <= 10) && (b >= 0) && (b <= 10)) {
                        System.out.println(Result(a, b, input[1]));
                    } else {
                        throw new NumberRangeException("Вводить можно только числа в диапазоне от 1 до 10");
                    }
                } catch (NumberFormatException e) {
                    int b = romanToArabic(input[2]);
                    throw new DifferentTypesException("Числа должны быть в одной системе счисления");
                }
            } catch (NumberFormatException e) {
                int a = romanToArabic(input[0]);
                try {
                    int b = Integer.parseInt(input[2]);

                    throw new DifferentTypesException("Числа должны быть в одной системе счисления");
                } catch (NumberFormatException l) {
                    int b = romanToArabic(input[2]);
                    if ((a >= 0) && (a <= 10) && (b >= 0) && (b <= 10)) {
                        System.out.println(arabicToRoman(Result(a, b, input[1])));
                    } else {
                        throw new NumberRangeException("Вводить можно только числа в диапазоне от 1 до 10");
                    }
                }
            }
        }

    }

    public enum Roman {
        M(1000), CM(900), D(500), CD(400), C(100), XC(90), L(50),
        XL(40), X(10), IX(9), V(5), IV(4), I(1);


        private final int value;

        private Roman(int value) {
            this.value = value;
        }

        public int toInt() {
            return value;
        }
    }

    public static int romanToArabic(String input) {

        int result = 0;

        List<Roman> romanNumerals = List.of(Roman.values());

        String romanNumeral = input.toUpperCase();
        int i = 0;
        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            Roman symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.toInt();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException("Должны быть введены целые числа");
        }
        return result;
    }

    public static String arabicToRoman(int number) throws RomanNegativeException {
        if (number < 0) {
            throw new RomanNegativeException("В римской системе нет отрицательных чисел");
        }
        else if (number == 0){
            return "0";
        }


        List<Roman> romanNumerals = List.of(Roman.values());

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            Roman currentSymbol = romanNumerals.get(i);
            if (currentSymbol.toInt() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.toInt();
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    public static int Result(int a, int b, String operator) {
        switch (operator) {
            case ("+"):
                return a + b;
            case ("-"):
                return a - b;
            case ("*"):
                return a * b;
            case ("/"):
                return a / b;
        }
        return 0;
    }
}




