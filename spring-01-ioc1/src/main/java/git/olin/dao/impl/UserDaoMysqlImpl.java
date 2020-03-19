package git.olin.dao.impl;

import git.olin.dao.UserDao;

public class UserDaoMysqlImpl implements UserDao {
    public void getUser() {
        System.out.println("Mysql获取用户数据");
    }
}
