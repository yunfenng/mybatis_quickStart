package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    @Test
    public void test1() throws IOException {
        // 1. Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2. 解析配置文件，创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 3. 生产sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession(); // 默认开启一个事务，但是不会自动提交事务
                                                                // 在进行增删改的时候，需要手动提交事务
        // 4. sqlSession调用方法，查询所有：selectList 查询单个：selectOne 添加：insert 修改：update 删除：delete
        List<User> users = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true); // 自动提交事务
        User user = new User();
        user.setId(4);
        user.setUsername("奉孝");
        sqlSession.insert("com.lagou.dao.IUserDao.saveUser", user);
        // sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test3() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        user.setUsername("王八");
        sqlSession.update("com.lagou.dao.IUserDao.updateUser", user);
        sqlSession.commit();

        sqlSession.close();
    }


    @Test
    public void test4() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(4);
        sqlSession.delete("userMapper.deleteUser", user);
        sqlSession.commit();

        sqlSession.close();
    }

    /**
     * 代理模式
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> users = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test6() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setUsername("tom");

        List<User> users = mapper.findByCondition(user1);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test7() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        int[] arr = {1, 2};

        List<User> users = mapper.findByIds(arr);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
