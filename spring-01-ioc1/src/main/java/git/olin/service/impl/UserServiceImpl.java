package git.olin.service.impl;

import git.olin.dao.UserDao;
import git.olin.dao.impl.UserDaoImpl;
import git.olin.dao.impl.UserDaoMysqlImpl;
import git.olin.service.UserService;

public class UserServiceImpl implements UserService {
    // 使用Java里的组合概念
    // private UserDao userDao = new UserDaoImpl();
    // private UserDao userDao = new UserDaoMysqlImpl();
    private UserDao userDao;

    // 利用set进行动态实现值的注入！
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void getUser() {
        userDao.getUser();
    }
}
