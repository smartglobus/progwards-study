package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class SessionManager {
    //    private Collection<UserSession> sessions;// collection type - ???
    private int sessionValid;
    private Map<Integer, UserSession> sessionMap;// private <коллекция> sessions;

    public SessionManager(int sessionValid) {
        this.sessionValid = sessionValid;
        sessionMap = new HashMap<>();
    }

    public void add(UserSession userSession) {
//        UserSession sessionToAdd = find(userSession.getUserName());
//        if (sessionToAdd == null) {
//            sessionMap.put(userSession.getSessionHandle(), userSession);
//        }else {
//
//        }
        sessionMap.put(userSession.getSessionHandle(), userSession);
    }

    public UserSession find(String userName) {
        for (UserSession s : sessionMap.values()) {
            if (s.getUserName().equals(userName)) {
                return get(s.getSessionHandle());
            }
        }
        return null;
    }

    public UserSession get(int sessionHandle) {

        UserSession session = sessionMap.get(sessionHandle);
        if (session == null || Duration.between(session.getLastAccess(), LocalDateTime.now()).toSeconds() > sessionValid) {
            return null;
        }
        session.updateLastAccess();
        return session;
    }

    public void delete(int sessionHandle) {
        sessionMap.remove(sessionHandle);
    }

    public void deleteExpired() {
        LocalDateTime now = LocalDateTime.now();
        Collection<UserSession> currentSessions = new ArrayList<>(sessionMap.values());
        for (UserSession s : currentSessions) {
            if (Duration.between(s.getLastAccess(), now).toSeconds() > sessionValid) {
                sessionMap.remove(s.getSessionHandle());
            }
        }
    }

    public static void main(String[] args) {
        SessionManager sm = new SessionManager(10);
        UserSession krk = new UserSession("Krk");
        if (sm.find("Krk") == null) {
            sm.add(krk);
        }
        System.out.println("krk last call  "+sm.get(krk.getSessionHandle()).getLastAccess());
        System.out.println("krk last call  "+sm.get(krk.getSessionHandle()).getLastAccess());
        System.out.println("krk last call  "+sm.get(krk.getSessionHandle()).getLastAccess());
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("is it null?  "+sm.get(krk.getSessionHandle()));
        UserSession krk2 = new UserSession("Krk");
        System.out.println("krk2 last call  "+krk2.getLastAccess());
        sm.add(krk2);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserSession krk3 = new UserSession("Krk");
        System.out.println("krk3 last call  "+krk3.getLastAccess());
        sm.add(krk3);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("time before delExp "+ LocalDateTime.now());
        sm.deleteExpired();

        for (var n : sm.sessionMap.entrySet()){
            System.out.println("Krk key  "+n.getKey());
        }
        sm.delete(krk3.getSessionHandle());
        System.out.println("is it empty?  "+sm.sessionMap.isEmpty()+ "   sm.size "+ sm.sessionMap.size());
    }
}
/*
Протестировать следующим образом:

    Создать сессию по userName, для этого
    - сделать find,
    - убедиться что вернется null
    - создать новую сессию
    - добавить используя add

    Вызвать несколько раз get

    Подождать (Thread.sleep) время, большее, чем время валидности

    Проверить что сессии нет через метод get

    Создать еще одну сессию

    Подождать половину времени валидности сессии

    Создать еще одну сессию

    Подождать еще раз половину времени валидности сессии

    Вызвать deleteExpired()

    Убедиться, что одна сессия удалена, вторая нет

    Удалить оставшуюся через метод delete

    Убедиться что удаление прошло
 */

class UserSession {
    private int sessionHandle;
    private String userName;
    private LocalDateTime lastAccess;//<дата-время>

    private static Random random = new Random();

    public int getSessionHandle() {
        return sessionHandle;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getLastAccess() {
        return lastAccess;
    }

    public void updateLastAccess() {
        lastAccess = LocalDateTime.now();
    }

    public UserSession(String userName) {
        this.userName = userName;
        sessionHandle = random.nextInt();
        lastAccess = LocalDateTime.now();
    }
}
