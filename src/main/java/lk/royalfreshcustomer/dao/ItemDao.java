package lk.royalfreshcustomer.dao;

import lk.royalfreshcustomer.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Integer> {


}
