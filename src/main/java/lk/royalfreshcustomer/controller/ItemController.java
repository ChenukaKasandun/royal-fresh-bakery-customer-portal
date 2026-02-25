package lk.royalfreshcustomer.controller;


import lk.royalfreshcustomer.dao.ItemDao;
import lk.royalfreshcustomer.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemDao itemDao;

    // request mapping for get item all data [URL --->//item/alldata]
    @GetMapping(value = "/item/alldata", produces = "application/json")
    public List<Item> getAllItemData() {
            return itemDao.findAll(Sort.by(Direction.DESC, "id"));

    }


}
