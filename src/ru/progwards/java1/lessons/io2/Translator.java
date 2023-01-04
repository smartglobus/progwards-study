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
            if (!Character.isAlphabetic(sentenceArr[i])) {
                outSntc += sentenceArr[i];
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

    private String wordTranslate(String word) {
        String w = word;
        for (int j = 0; j < inLang.length; j++) {
            if (w.toLowerCase().equals(inLang[j])) {
                w = outLang[j];
                if (Character.isUpperCase(word.toCharArray()[0])) {
                    w = w.substring(0, 1).toUpperCase() + w.substring(1);
                }
                break;
            }
        }
        return w;
    }

    public static void main(String[] args) {
        Translator tst = new Translator(new String[]{"привет", "мир"}, new String[]{"hello", "world"});
        System.out.println(tst.translate("привет, Мир!"));
        ;
    }
}


