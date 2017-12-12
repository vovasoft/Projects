package com.vova.mybatis;

import java.util.List;

/**
 * @author: Vova
 * @create: date ${time} ${date}
 */
public interface IUserDao {

    void insert(Customer customer);

    void insertAll(List<Customer> list);

    void deleteById(int id);


}
