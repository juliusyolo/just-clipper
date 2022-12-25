package com.sdefaa.just.clipper.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julius Wong
 * <p>
 *
 * <p>
 * @since 1.0.0
 */
@RestController
@RequestMapping("/demo")
public class CustomerController {
    @GetMapping("/customers/query")
    public List<Customer> queryCustomer() {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer("林默", "350321096003237001",
                "18616887788", "xiaoshengsheng1994@gmail.com",
                "6001111001020241235"));
        list.add(new Customer("张三丰", "44242219981025211X",
                "80187789", "xiaoshengsheng1994@gmail.com",
                "6001111001020241236"));
        list.add(new Customer("上官婉儿", "442422199810252111",
                "9061688856", "xiaoshengsheng1994@gmail.com",
                "6001111001020241237"));
        list.add(new Customer("薛仁贵", "442422199810252111",
                "021-6575817", "xiaoshengsheng1994@gmail.com",
                "6001111001020241238"));
        return list;
    }
    static class Customer {
        private String name;
        private String identNo;
        private String phone;
        private String email;
        private String bankNo;

        public Customer(String name, String identNo,
                        String phone, String email, String bankNo) {
            this.name = name;
            this.identNo = identNo;
            this.phone = phone;
            this.email = email;
            this.bankNo = bankNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdentNo() {
            return identNo;
        }

        public void setIdentNo(String identNo) {
            this.identNo = identNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "name='" + name + '\'' +
                    ", identNo='" + identNo + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    ", bankNo='" + bankNo + '\'' +
                    '}';
        }
    }
}
