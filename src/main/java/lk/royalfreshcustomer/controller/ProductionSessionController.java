package lk.royalfreshcustomer.controller;

import lk.royalfreshcustomer.dao.ProductionSessionDao;
import lk.royalfreshcustomer.entity.ProductionSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductionSessionController {

    @Autowired
    private ProductionSessionDao productionSessionDao;

    // request mapping for get production session all data [URL
    // --->//productionsession/alldata]
    @GetMapping(value = "/productionsession/alldata", produces = "application/json")
    public List<ProductionSession> findAllData() {
        return productionSessionDao.findAll();

    }

}
