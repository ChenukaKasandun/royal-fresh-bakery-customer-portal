package lk.royalfreshcustomer.dao;

import lk.royalfreshcustomer.entity.CustomerLoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerLoginUserDao  extends JpaRepository<CustomerLoginUser,Integer> {

    @Query(value = "select u from CustomerLoginUser  u where u.username=?1")
    CustomerLoginUser getByUsername(String username);
}
