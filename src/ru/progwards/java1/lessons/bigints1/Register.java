package ru.progwards.java1.lessons.bigints1;

public  class Register {
public int regVolume;
public Bit[] register;

    public Register(int regVolume) {
        this.regVolume = regVolume;
        this.register = new Bit[regVolume];
        for (int i = 0; i < regVolume; i++) {
            register[i] = new Bit(false);
        }
    }

    public String toString() {
        String result = "";
        boolean firstTrue = false;
        for (int i = regVolume-1; i >= 0; i--) {
            if (i == 0 && !firstTrue) result = "0";
            if (!register[i].value && !firstTrue) continue;
            if (register[i].value) firstTrue = true;
            result = result + register[i].toString();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Register(10) {
            @Override
            public String toString() {
                return super.register[4].toString();
            }
        });
    }
}
