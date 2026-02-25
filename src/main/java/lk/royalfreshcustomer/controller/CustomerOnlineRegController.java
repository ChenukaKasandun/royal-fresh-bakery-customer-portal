package lk.royalfreshcustomer.controller;


import lk.royalfreshcustomer.dao.CustomerLoginUserDao;
import lk.royalfreshcustomer.entity.Customer;
import lk.royalfreshcustomer.dao.CustomerDao;
import lk.royalfreshcustomer.entity.CustomerHasItem;
import lk.royalfreshcustomer.entity.CustomerLoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
public class CustomerOnlineRegController {


    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomerLoginUserDao customerLoginUserDao;

    // request mapping for load customer ui[URL --->/customeronlinereg]
    @RequestMapping(value = "/customeronlinereg")
    public ModelAndView loadCustomerUI() {


        ModelAndView customerOnlineRegUI = new ModelAndView();
        customerOnlineRegUI.setViewName("customeronlinereg.html");

        customerOnlineRegUI.addObject("title", "customer registration");

        return customerOnlineRegUI;
    }

    //.................Insert.................................

    //request mapping for insert online customer registration data from the frontend [URL
    //--->//onlinecustomer/insert]
    @PostMapping(value = "/onlinecustomer/insert")
    public String saveOnlineCustomerData(@RequestBody Customer customer) {

        // duplicate check

        // Email Duplicate Check
        Customer extCustomerByEmail = customerDao.getCustomerByEmail(customer.getEmail());
        if (extCustomerByEmail != null && extCustomerByEmail.getId() != customer.getId()) {
            // return "Save not Completed : Email Allready exist..! ";
            return "Save Not Completed  : Entered Email " + customer.getEmail() + " already exist...!";

        }

        // Mobile No Duplicate Check
        Customer extCustomerByMobile = customerDao.getCustomerByMobileNo(customer.getMobileno());
        if (extCustomerByMobile != null && extCustomerByMobile.getId() != customer.getId()) {
            return "Save Not Completed : Entered Mobile No " + customer.getMobileno() + "already exists...!";

        }

        try {

            // Set auto added data
            customer.setAdd_date_time(LocalDateTime.now());
            customer.setAdd_user_id(1);
            customer.setReg_no(customerDao.getNextCustomerRegNo());


// Saving data in association table
            for (CustomerHasItem chi : customer.getCustomerHasItemList() ){
                chi.setCustomer_id(customer);

}

            // save operator(save frontend object)
           Customer newCustomer = customerDao.save(customer);


            // dependancy for online customer account creation
            CustomerLoginUser  customerLoginUser = new CustomerLoginUser();
            customerLoginUser.setAdd_date_time(LocalDateTime.now());
            customerLoginUser.setAdd_user_id(customer.getAdd_user_id());
            customerLoginUser.setCustomer_id(newCustomer);
//            Set Customer Login User Account username as customer's mobile no
            customerLoginUser.setUsername(customer.getEmail());

//           Set Customer Login User Account password as customer's email address
            customerLoginUser.setPassword(bCryptPasswordEncoder.encode(customer.getMobileno()));

//          saving in Customer Login User Table
            customerLoginUserDao.save(customerLoginUser);


            return "OK";
        } catch (Exception e) {
            return "Save not completed :" + e.getMessage();
        }

    }

}





