package lk.royalfreshcustomer.controller;


import lk.royalfreshcustomer.dao.CustomerLoginUserDao;
import lk.royalfreshcustomer.dao.CustomerOrderDao;
import lk.royalfreshcustomer.entity.CustomerLoginUser;
import lk.royalfreshcustomer.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@RestController
public class OnlineCustomerOrderController {

    @Autowired
    private CustomerOrderDao customerOrderDao;

    @Autowired
    private CustomerLoginUserDao customerLoginUserDao;

    // request mapping for load customer ui[URL --->/customeronlineorder]
    @RequestMapping(value = "/customeronlineorder")
    public ModelAndView loadOnlineCustomerOrderUI() {


        ModelAndView customerOnlineOrderUI = new ModelAndView();
        customerOnlineOrderUI.setViewName("onlineCustomerOrder.html");


        customerOnlineOrderUI.addObject("title", "onlinecustomerorder");

        return customerOnlineOrderUI;
    }



    // 2...........Insert...........................
    // request mapping for insert online customer order data from the frontend [URL
    // --->/onlinecustomerorder/insert]
    @PostMapping(value = "/onlinecustomerorder/insert")
    public String saveCustomerOrderData(@RequestBody CustomerOrder customerorder) {

            // duplicate check

            try {
                //Getting ID of logged Customer using CustomerLoginUserDao
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                CustomerLoginUser loggedCustomer =customerLoginUserDao.getByUsername(auth.getName());

                // Set auto added data
                customerorder.setAdded_date_time(LocalDateTime.now());
                customerorder.setAdd_user_id(loggedCustomer.getId());
                customerorder.setOrder_no(customerOrderDao.getNextOrderNo());

                // save operator(save frontend object)
                customerOrderDao.save(customerorder);
                return "OK";
            } catch (Exception e) {
                return "Save not completed :" + e.getMessage();
            }

    }
}
