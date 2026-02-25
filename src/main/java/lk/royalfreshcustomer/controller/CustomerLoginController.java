package lk.royalfreshcustomer.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lk.royalfreshcustomer.dao.CustomerDao;
import lk.royalfreshcustomer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CustomerLoginController {


    @Autowired
    private CustomerDao customerDao;
    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder; // In order to encrypt
    // Admin's password

    // request mapping for load user login ui[URL --->/login]
    @RequestMapping(value = "/customerlogin")
    public ModelAndView loadCustomerLoginUI() {

        ModelAndView customerLoginUI = new ModelAndView();
        customerLoginUI.setViewName("customerLogin.html");

        return customerLoginUI;
    }

    // mapping for return dashboard page url --> [/dashboard]
    @RequestMapping(value = { "/customerdashboard" })
    public ModelAndView uiDashboardPage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();// This can access authentication
        // object which has numerous
        // properties

        ModelAndView dashboardPage = new ModelAndView();
        dashboardPage.setViewName("customerDashboard.html");
        dashboardPage.addObject("loggedcustomername", auth.getName());// This returns the name of the logged customer name in the
        // dashboard
        return dashboardPage;

    }


    // request mapping for get Customer Name by loggedCustomer  [URL
    // --->/customerlogin/customernamebyusername]
    @GetMapping(value = "/customerlogin/customernamebyusername", produces = "application/json")
    public List<Customer> findCustomerNameByUserName() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();// This can access authentication

        // Get Customer name by logged username
        return customerDao.getCustomerNameByLogin(auth.getName());
    }


    // mapping for return error page url --> [/errorpage]
    @RequestMapping(value = { "/errorpage" })
    public ModelAndView errorPageUI() {
        ModelAndView errorPageView = new ModelAndView();
        errorPageView.setViewName("errorpage.html");
        return errorPageView;

    }

//    // mapping for create User "Admin"
//    @RequestMapping(value = { "/createadmin" })
//    public ModelAndView generateAdminAccount() {
//        User extAdminUser = userDao.getByUsername("Admin");
//        // If there is no existing Admin, we have to generate a new Admin,if there is,no
//        // need
//        if (extAdminUser == null) {
//            User adminUser = new User();
//            adminUser.setUsername("Admin");
//            adminUser.setEmail("Admin@gmail.com");
//            adminUser.setStatus(true);
//            adminUser.setAdded_date_time(LocalDateTime.now());
//            adminUser.setPassword(bCryptPasswordEncoder.encode("12345"));// Admin's password
//
//            Set<Role> roles = new HashSet<>(); // HashSet<>() needed to produce a "Set" Unless it's an interface
//            Role adminRole = roleDao.getReferenceById(11);
//            roles.add(adminRole);
//
//            adminUser.setRoles(roles);
//
//            userDao.save(adminUser);
//        }
//        ModelAndView loginUI = new ModelAndView();
//        loginUI.setViewName("login3.html");
//        return loginUI;
//
//    }
//


}
