import git.olin.dao.impl.UserDaoMysqlImpl;
import git.olin.dao.impl.UserDaoOracleImpl;
import git.olin.service.UserService;
import git.olin.service.impl.UserServiceImpl;
import org.junit.Test;

public class MyTest {
    @Test
    public void testIoc1(){
        // 用户实际调用的是业务层，dao层不需要接触！
        UserService userService = new UserServiceImpl();

        // 如果需要增加一个需求（比如增加UserDaoMysqlImpl），需要怎么办呢？如何实现增加的需求？
        // 答案就是UserServiceImpl中把UserDaoImpl改成UserDaoMysqlImpl即可，
        // 同理，增加多个需求，修改源码是不是很麻烦？
        // 最终的解决办法就是在ServiceImpl实现类中利用set进行动态实现值的注入！
        //   ((UserServiceImpl) userService).setUserDao(new UserDaoMysqlImpl());
        ((UserServiceImpl) userService).setUserDao(new UserDaoOracleImpl());
        userService.getUser();
    }
}
