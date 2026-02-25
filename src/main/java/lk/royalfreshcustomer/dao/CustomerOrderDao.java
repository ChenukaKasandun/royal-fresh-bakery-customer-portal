package lk.royalfreshcustomer.dao;

import lk.royalfreshcustomer.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderDao  extends JpaRepository<CustomerOrder,Integer> {


    // Query To generate next order no
    @Query(value = "SELECT CONCAT('ODR', LPAD(COALESCE(MAX(CAST(SUBSTRING(c.order_no, 4) AS UNSIGNED)), 0) + 1, 3, '0')) AS next_order_no FROM cckcakesandbakery.customer_order as c;", nativeQuery = true)
    String getNextOrderNo();

}
