package ru.progwards.java1.lessons.bigints1;

public abstract class Register {
public int regVolume;
public Bit[] register;


    public Register(int regVolume) {
        this.regVolume = regVolume;
        this.register = new Bit[regVolume];
        for (int i = 0; i < regVolume; i++) {
            register[i] = new Bit(false);
        }
    }

    public Register(int regVolume, int value){
        this(regVolume);
        for (int i = 0, j = 1; i < regVolume; i++, j <<= 1) {
            if ((value & j) != 0) {
                register[i].value = true;
            }
        }
    }

    public String toString() {
        String result = "";
        // закомментированы строки кода нужные для удаления лишних нулей вначале строки.
//        boolean firstTrue = false;
        for (int i = regVolume-1; i >= 0; i--) {
//            if (i == 0 && !firstTrue && !register[i].value) result = "0";
//            if (!register[i].value && !firstTrue) continue;
//            if (register[i].value) firstTrue = true;
            result = result + register[i].toString();
        }
        return result;
    }

    public abstract String toDecString();

    public static void main(String[] args) {

    }
}
