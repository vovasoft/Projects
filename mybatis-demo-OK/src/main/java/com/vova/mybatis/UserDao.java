package com.vova.mybatis;

import org.springframework.beans.BeansException;
import org.springframework.beans.Mergeable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author:    WangYang
 * @Data:      17:21 2017/12/12
 *
 */


public class UserDao extends AbstractBaseMongoTemplete implements IUserDao {

    public UserDao(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public void insert(Customer customer) {
        mongoTemplate.insert(customer);
    }

    public void insertAll(List<Customer> list) {
        mongoTemplate.insertAll(list);
    }

    public void deleteById(int id) {
        Customer customer=new Customer(id,null,0);
        mongoTemplate.remove(customer);

    }
}
