package com.example.website;

//import com.example.website.Repo.AddressRepo;
import com.example.website.Repo.UsersRepo;
import com.example.website.model.Address;
import com.example.website.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// implements CommandLineRunner


@SpringBootApplication
public class WebsiteApplication{


    public static void main(String[] args) {

        SpringApplication.run(WebsiteApplication.class, args);
    }
    /*
    @Override
    public void run(String...args){
        Users userInfo = new Users("firstname1",
                "lastname1",
                "username",
                "pw",
                "email",
                "phone");
        UsersRepo.save(users);
    }


    @Override
    public void run(String...args){
        Address addressInfoOne = new Address("street1",
                "street2",
                "city",
                "country",
                "zip code");
        addressRepo.save(addressInfoOne);
    }

 */
}
