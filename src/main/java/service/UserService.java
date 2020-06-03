package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

public class UserService {
    private UserService() {
    }

    public static class UserServiceHolder {
        public static final UserService HOLDER_INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserServiceHolder.HOLDER_INSTANCE;
    }

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
//        Long id = maxId.incrementAndGet();
//        user.setId(id);
//        dataBase.put(id, user);
//        return (!dataBase.containsKey(id));
        if (!isExistsThisUser(user)) {
            dataBase.put(maxId.getAndIncrement(), user);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return dataBase.containsValue(user);
//        for (User u : dataBase.values()) {
//            if (user.getEmail().equals(u.getEmail())) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
    }

    public List<User> getAllAuth() {
        return (List<User>) authMap.values();
    }

    public boolean authUser(User user) {
        if (isExistsThisUser(user)) {
            authMap.put(maxId.getAndIncrement(), user);
//            authMap.put(user.getId(),user);
            return true;
        } else {
            return false;
        }
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return dataBase.containsKey(id);
    }

}
