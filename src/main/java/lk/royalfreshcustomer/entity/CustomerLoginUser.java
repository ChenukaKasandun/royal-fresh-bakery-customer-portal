package lk.royalfreshcustomer.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity // this class generate as an entity
@Table(name = "customer_login_user") // table mapping

@Data // setter and getters, toString
@AllArgsConstructor // All Argument constructor
@NoArgsConstructor // Default constructor / empty constructor
public class CustomerLoginUser {

    @Id// PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private LocalDateTime add_date_time;

    private LocalDateTime update_date_time;

    private LocalDateTime delete_date_time;

    @NotNull
    private Integer add_user_id;

    private Integer update_user_id;

    private Integer delete_user_id;


//    Fotrign Key Customer Table
    @ManyToOne
    @JoinColumn(name = "customer_id" , referencedColumnName = "id")
    private Customer customer_id;


}
