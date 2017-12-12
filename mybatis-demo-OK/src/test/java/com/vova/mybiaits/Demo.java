package com.vova.mybiaits;

import com.vova.mybatis.Customer;
import com.vova.mybatis.Order;
import com.vova.mybatis.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class Demo {
    @Test
    public void insert() throws IOException {

        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession ss = sf.openSession();
        Customer user = new Customer();
        user.setName("Liming");
        user.setAge(13);

        ss.insert("customers.insert", user);

        ss.commit();
        ss.close();

        System.out.println(user.getId());

    }

    @Test
    public void insertOrd() throws IOException {

        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession ss = sf.openSession();
        Order od = new Order();
        od.setOrderno("OiD = 4444");
        od.setPrice((float) 123.5);
        Customer cc = new Customer();
        cc.setAge(new Random(123L).nextInt());
        cc.setName("vova" + BigDecimal.ONE);

        od.setCustomer(cc);
        ss.insert("customers.insert", cc);
        ss.insert("orders.insert", od);

        ss.commit();
        ss.close();

    }

    @Test
    public void update() throws IOException {

        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession ss = sf.openSession();
        Customer user = new Customer();
        user.setName("vo313131");
        user.setAge(22);
        user.setId(1);

        ss.insert("customers.update", user);

        ss.commit();
        ss.close();

    }

    @Test
    public void delete() throws IOException {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();

        //    Customer user = new Customer();
        // user.setId(1);
        ss.delete("customers.delete", 2);

        ss.commit();
        ss.close();

    }

    @Test
    public void findByID() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();

        Customer c = new Customer();
        c.setId(2);

        Customer cnew = s.selectOne("customers.findByid", 2);

        s.commit();
        s.close();

        System.out.println(cnew.getName() + cnew.getAge());
    }

    @Test
    public void findByOrderID() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();


        Order o = s.selectOne("orders.findOneByID", 4);

        s.commit();
        s.close();

        Customer cnew = o.getCustomer();
        if (cnew == null) {
            System.out.println("null error");

        } else {

            System.out.println(o.getCustomer().getName());
        }
    }

    @Test
    public void findCustomerOrder() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        Customer c = s.selectOne("customers.findCustomerOrder",4);
        
        List<Order> orderList = c.getOrders();
        for (Order order : orderList) {
            System.out.println(order.getPrice());
        }
        s.commit();
        s.close();
    }

    @Test
    public void findAll() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        List<Customer> list = s.selectList("customers.findAll");
        for (Customer customer : list) {
            System.out.println(customer.getId() + " " + customer.getName() + " " + customer.getAge());
        }
        
    }

    @Test
    public void insertMongo(){
        Customer customer1 = new Customer(1,"vova1",1231);
        Customer customer2= new Customer(2,"vova2",1232);
        Customer customer3 = new Customer(3,"vova3",1233);
        ApplicationContext ac =new ClassPathXmlApplicationContext("spring-mongodb.xml");
        MongoTemplate mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
        UserDao UserDao = new UserDao(mongoTemplate);

        UserDao.insert(customer1);
        UserDao.insert(customer2);
        UserDao.insert(customer3);
    }
}
