package ru.progwards.java1.lessons.params;

public class FloatNumber {
    boolean sign;
    long mantissa;
    int exp;

    FloatNumber(boolean sign, long mantissa, int exp) {
        this.sign = sign;
        this.mantissa = mantissa;
        this.exp = exp;
    }

    FloatNumber(String num) {

        char[] numToArray = num.toCharArray();
//        for (char dig: numToArray
//             ) {
//            System.out.println(dig);
//        }

        // блок выделения знака
        if (numToArray[0] != '-') {
            sign = true;
        } else {
            sign = false;
        }


        int firstNumber = 0;
        if (numToArray[0] == '-' || numToArray[0] == '+') {
            firstNumber = 1;
        }
// суммирование членов массива, пока не закончатся значимые цифры или не переполнится мантисса
        long extrMnt = (long) Character.digit(numToArray[firstNumber], 10);

        for (int i = firstNumber; i < numToArray.length - 2; i++) {
// если extrMnt >= Long.MAX_VALUE перестать заполнять мантиссу и перейти к окончательному вычислению степени
            if (extrMnt >= Long.MAX_VALUE / 10) {
                break;
            }

            // если i-й символ точка, меняем его местами с символом i+1 и уменьшаем exp
            if (numToArray[i + 1] == '.' && numToArray[i + 1] != 'e' && numToArray[i + 1] != 'E') {
                char temp = numToArray[i + 1];
                numToArray[i + 1] = numToArray[i + 2];
                numToArray[i + 2] = temp;
                exp--;
            }

            if (numToArray[i+1] == 'e' || numToArray[i+1] == 'E') {
                // переход в блок с циклом, который суммирует в число все цифры, начиная с i+1 , и прибавляет результат к exp
                exp++;
                break;

            }
            // увеличиваем текущее значение мантиссы с учётом разрядности
            extrMnt = extrMnt * 10 + Character.digit(numToArray[i + 1], 10);
            mantissa = extrMnt;
        }


        for (int j = 0; j < numToArray.length; j++) {
            //ищем e или E, .....
            if (numToArray[j] == '.' && numToArray[j] < numToArray.length) {
                j++;
            }
            if (numToArray[j] == '-' && numToArray[j] < numToArray.length) {

            }

        }


    }

    public static void main(String[] args) {
        FloatNumber test = new FloatNumber("0.00007092233720368547E455");
        System.out.println(test);
        System.out.println(Long.MAX_VALUE);
        System.out.println((Double.MAX_VALUE));

    }

    @Override
    public String toString() {
        return "FloatNumber{" +
                "sign=" + sign +
                ", mantissa=" + mantissa +
                ", exp=" + exp +
                '}';
    }
}
