package ru.progwards.java2.lessons.synchro.app;




import ru.progwards.java2.lessons.synchro.app.model.Account;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Store {

    private static Map<String, Account> store = new HashMap<>();///////////////
    private static File storeFile = new File("src\\ru\\progwards\\java2\\lessons\\synchro\\app", "storeFile.csv");

    static {
        try (FileWriter writer = new FileWriter(storeFile)) {

            for (int i = 0; i < 10; i++) {
               Account acc = new Account();
                String id = UUID.randomUUID().toString();
                acc.setId(id);
                acc.setPin(1000 + i);
                acc.setHolder("Account_" + i);
                acc.setDate(new Date(System.currentTimeMillis() + 365 * 24 * 3600 * 1000L));
                acc.setAmount(Math.random() * 1_000_000);

                store.put(acc.getId(), acc);/////////////////
                writer.write(accToString(acc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String accToString(Account acc){
        return acc.getId() + "," + acc.getHolder() + "," + acc.getDate().getTime() + "," + acc.getPin() + "," + acc.getAmount() + "\n";
    }

    public static Map<String, Account> getStore() { return store;}////////////////
    public static File getStoreFile(){return storeFile;}




    public static void main(String[] args) {
        getStoreFile();
    }
}
