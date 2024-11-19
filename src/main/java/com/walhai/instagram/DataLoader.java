package com.walhai.instagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DataServiceMongo dataServiceMongo;
    @Autowired
    private DataServicePost dataServicePost;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("----------STARTING WITH MONGODB NOW----------");
        dataServiceMongo.loadData100();
        dataServiceMongo.loadData1K();
        dataServiceMongo.loadData10K();
        dataServiceMongo.getAllUsers();
        dataServiceMongo.getUsersByEmail();
        dataServiceMongo.getUsersByEmailWithProjection();
        dataServiceMongo.getUsersByEmailWithProjectionAndSorting("gmail.com$", "created_at", true);
        dataServiceMongo.updateWhereGmail();
        dataServiceMongo.deleteData();
        System.out.println("----------ENDING WITH MONGODB NOW----------");
        System.out.println("----------STARTING WITH POSTGRES NOW----------");
        dataServicePost.loadData100();
        dataServicePost.loadData1K();
        dataServicePost.loadData10K();
        dataServicePost.getAllUsers();
        dataServicePost.getUsersByUsername();
        dataServicePost.getUsersByUsernameWithProjection();
        dataServicePost.getUsersByUsernameWithProjectionAndSorting();
        dataServicePost.updateWhereGmail();
        dataServicePost.deleteData();
        System.out.println("----------ENDING WITH POSTGRES NOW----------");
    }
}
