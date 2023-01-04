package ru.progwards.java1.lessons.io2;

public class Translator {
    private String[] inLang;
    private String[] outLang;

    Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        this.outLang = outLang;
    }

    public String translate(String sentence) {
        String outSntc = "";
        char[] sentenceArr = sentence.toCharArray();

        for (int i = 0; i < sentenceArr.length; i++) {
//            System.out.println(sentenceArr[i]);
            if (!Character.isAlphabetic(sentenceArr[i])) {
                outSntc += sentenceArr[i];
                continue;
            } else {
                String word = "";
                while (i < sentenceArr.length && Character.isAlphabetic(sentenceArr[i])) {
                    word += sentenceArr[i];
                    i++;
                }
                i--;
                outSntc += wordTranslate(word);
            }
        }
        return outSntc;
    }

    private String wordTranslate(String wrd) {
        String w = wrd;
        for (int j = 0; j < inLang.length; j++) {
            if (w.toLowerCase().equals(inLang[j])) {
                w = outLang[j];
                if (Character.isUpperCase(wrd.toCharArray()[0])) {
                    w = w.substring(0, 1).toUpperCase() + w.substring(1);
                }
                break;
            }
        }
        return w;
    }

    public static void main(String[] args) {
        Translator tst = new Translator(new String[]{"привет", "мир"}, new String[]{"hello", "world"});
        System.out.println(tst.translate("Привет, Мир!"));
        ;
    }
}
/*
Создать класс Translator - переводчик, который будет переводить предложения с одного языка на другой

2.1 Конструктор Translator(String[] inLang, String[] outLang), где inLang и outLang - массивы слов на разных языках, например русском и английском.
Сохранить значения параметров в приватных свойствах класса

2.2  Метод public String translate(String sentence), в котором найти слова, содержащиеся в sentence и в inLang и заменить их на соответствующие в outLang.
 Пунктуация должна быть соблюдена (скопирована из оригинальной строки).
 При этом надо соблюсти заглавные буквы, если они были в оригинальном тексте.
 В inLang и outLang все слова хранятся в нижнем регистре.

Например, фраза
"Hello World!" будет переведена как "Привет Мир!"
при наличии слов "hello", "world" в inLang и "привет", "мир" в outLang



 */

