# 1、Spring
#### 1.1、简介
- Spring：春天---->给软件行业带来了春天！

- 2002，首次推出了Spring框架的雏形：interface21框架！

- Spring框架即以interface21框架为基础，经过重新设计，并不断丰富其内涵，于2004年3月24日，发布了1.0正式版。

- **Rod Johnson**，Spring Framework创始人，著名作者。 很难想象Rod Johnson的学历，真的让好多人大吃一惊，他是[悉尼大学](https://baike.baidu.com/item/悉尼大学)的博士，然而他的专业不是计算机，而是音乐学。

- spring理念：使现有的技术更加容易使用，本身是一个大杂烩，整合了现有的技术框架！

  

- SSH：Struts2+Spring+Hibernate！

- SSM：SpringMVC+Spring+Mybatis！



官网：https://spring.io/projects/spring-framework#overview

官方下载地址：https://repo.spring.io/release/org/springframework/spring/

GitHub：https://github.com/spring-projects/spring-framework

```xml
<!--webmvc-->
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.2.RELEASE</version>
</dependency>
<!--jdbc-->
<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.2.RELEASE</version>
</dependency>
```

#### 1.2、优点

- Spring是一个开源免费的框架（容器）！
- Spring是一个轻量级的、非入侵式的框架！
- 控制反转（IOC），面向切面编程（AOP）！
- 支持事务的处理，对框架整合的支持！



==总结一句话：Spring就是一个轻量级的控制反转（IOC）和面向切面编程（AOP）的框架！==



#### 1.3、组成

![](idea-Spring%E5%AD%A6%E4%B9%A0.assets/Spring7%E5%A4%A7%E6%A8%A1%E5%9D%97.png)

#### 1.4、拓展

在Spring的官网有这个介绍：现代化的Java开发！说白就是基于Spring的开发！

![](idea-Spring%E5%AD%A6%E4%B9%A0.assets/Spring.png)

- Spring Boot
  -  一个快速开发的脚手架。
  - 基于Spring Boot可以快速的开发单个微服务。
  - 约定大于配置！

- Spring Cloud
  - SpringCloud是基于SpringBoot实现的。

因为现在大多数公司都在使用SpringBoot进行快速开发，学习SpringBoot的前提，需要完全掌握Spring及SpringMVC！承上启下的作用！

**弊端：发展了太久之后，违背了原来的理念！配置十分繁琐，人称：”配置地狱！“**

------

# 2、IOC理论推导

1.UserDao 接口

```java
public interface UserDao {
    public void getUser();
}
```

2.UserDaoImpl 实现类

```java
public class UserDaoImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("获取用户数据");
    }
}
```

3.UserService 业务接口

```java
public interface UserService {
    public void getUser();
}
```

4.UserServiceImpl 业务实现类

```java
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

5.test测试类

```java
@Test
public void test(){
    UserService service = new UserServiceImpl();
    service.getUser();
}
```

![](idea-Spring%E5%AD%A6%E4%B9%A0.assets/image-20200212175439500.png)

这是我们原来的方式，开始大家也都是这么去写的对吧，那我们现在修改一下。

把UserDao的实现类增加一个（增加需求）

```java
public class UserDaoMysqlImpl implements UserDao {
    public void getUser() {
        System.out.println("Mysql获取用户数据");
    }
}
```

紧接着我们去使用Mysql的话，我们就需要去UserServiceImpl实现类修改对应的实现。

```java
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoMySqlImpl();

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

再假设，我们增加一个UserDao的实现类（再增加一个需求）。

```java
public class UserDaoOracleImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("Oracle获取用户数据");
    }
}
```

那么我们要使用Oracle，又需要去service实现类里面修改对应的实现，假设我们这种需求非常大，这种方式就根本不适用了，甚至反人类对吧，每次变动，都需要修改大量代码，这种设计的耦合性太高了，牵一发而动全身。

**那我们如何解决呢？**

![](idea-Spring%E5%AD%A6%E4%B9%A0.assets/image-20200212175600557.png)

我们可以在需要的用到它的地方，不去实现它，而是留出一个接口，利用set，我们去代码里修改下。

```java
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    // 利用set实现
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```



在我们之前的业务中，用户的需求可能会影响我们原来的代码，我们需要根据用户的需求去修改源代码！如果程序代码量十分大，修改一次的成本代价十分昂贵！

我们使用一个Set接口实现，已经发生了革命性的变化！

```java
private UserDao userDao;

// 利用set进行动态实现值的注入！
public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
}
```

现在去我们的测试类里，进行测试。

```java
@Test
public void test(){
    UserServiceImpl service = new UserServiceImpl();
    // 需求1
    service.setUserDao( new UserDaoMySqlImpl() );
    service.getUser();
    // 需求2
    service.setUserDao( new UserDaoOracleImpl() );
    service.getUser();
}
```

- 之前，程序是主动创建对象！控制权在程序员手上！
- 使用了set注入后，程序不再具有主动性，而是变成了被动的接受对象！

这种思想（控制反转），从本质上解决了问题，我们程序员不用再去管理对象的创建了。系统的耦合性大大降低，可以更加专注业务的实现上！这是IOC的原型！

------

### IOC本质

**控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法**，也有人认为DI只是IoC的另一种说法。没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。![](idea-Spring%E5%AD%A6%E4%B9%A0.assets/image-20200212180029982.png)

**IoC是Spring框架的核心内容**，使用多种方式完美的实现了IoC，可以使用XML配置，也可以使用注解，新版本的Spring也可以零配置实现IoC。

Spring容器在初始化时先读取配置文件，根据配置文件或元数据创建与组织对象存入容器中，程序使用时再从Ioc容器中取出需要的对象。

![](idea-Spring%E5%AD%A6%E4%B9%A0.assets/image-20200212180512242.png)

采用XML方式配置Bean的时候，Bean的定义信息是和实现分离的，而采用注解的方式可以把两者合为一体，Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

**控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）。**

**参考：**

> https://blog.kuangstudy.com/index.php/archives/511/

------

# 3、HelloSpring

