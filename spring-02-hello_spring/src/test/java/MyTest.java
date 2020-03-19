import git.olin.pojo.Hello;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void testHelloSpring(){
        // 获取Spring的上下文对象！
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 我们的对象现在都在Spring中的管理了，我们要使用，直接去里面取出来就可以了！
        Hello hello1 = (Hello) context.getBean("hello");
        Hello hello2 =  context.getBean(Hello.class);
        System.out.println(hello1.toString());
        System.out.println(hello2.toString());
    }
}
