package ru.progwards.java1.lessons.io2;

public class PhoneNumber {

    public static String format(String phone) throws Exception {
        String numFormated = "";

        for (char ch : phone.toCharArray()) {
            if (Character.isDigit(ch))
                numFormated += ch;
        }
        if (numFormated.length() == 10) {
            numFormated = finalFormat(numFormated);
        } else if (numFormated.length() == 11) {
            numFormated = numFormated.substring(1);
            numFormated = finalFormat(numFormated);
        } else throw new Exception("Неверное количество цифр");

        return numFormated;
    }

    private static String finalFormat(String phoneNum) {
        return "+7(" + phoneNum.substring(0, 3) + ')' + phoneNum.substring(3, 6) + "-" + phoneNum.substring(6);
    }

    public static void main(String[] args) {
        try {
            System.out.println(format(" - 8 999 1567890 22 33"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

