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
        dataServiceMongo.deleteDataSilent();
        dataServiceMongo.loadData1K();
        dataServiceMongo.deleteDataSilent();
        dataServiceMongo.loadData10K();
        dataServiceMongo.getAllUsers();
        dataServiceMongo.getUsersByEmail();
        dataServiceMongo.getUsersByEmailWithProjection();
        dataServiceMongo.getUsersByEmailWithProjectionAndSorting("gmail.com$", "created_at", true);
        dataServiceMongo.updateWhereGmail();
        dataServiceMongo.deleteData();
        dataServiceMongo.deleteFollows();
        dataServiceMongo.loadData1K();
        System.out.println("----------ENDING WITH MONGODB NOW----------");
        System.out.println("----------STARTING WITH POSTGRES NOW----------");
        dataServicePost.loadData100();
        dataServicePost.deleteDataSilent();
        dataServicePost.loadData1K();
        dataServicePost.deleteDataSilent();
        dataServicePost.loadData10K();
        dataServicePost.getAllUsers();
        dataServicePost.getUsersByUsername();
        dataServicePost.getUsersByUsernameWithProjection();
        dataServicePost.getUsersByUsernameWithProjectionAndSorting();
        dataServicePost.updateWhereGmail();
        dataServicePost.deleteData();
        dataServicePost.deleteFollows();
        dataServicePost.loadData1K();
        System.out.println("----------ENDING WITH POSTGRES NOW----------");
    }
}
