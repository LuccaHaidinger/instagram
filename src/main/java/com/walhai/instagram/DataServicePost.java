package com.walhai.instagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walhai.instagram.postgres.model.Users;
import com.walhai.instagram.postgres.persistence.FollowsRepositoryPost;
import com.walhai.instagram.postgres.persistence.UsersRepositoryPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataServicePost {

    @Autowired
    private UsersRepositoryPost usersPostgres;

    @Autowired
    private FollowsRepositoryPost followsPostgres;

    @Autowired
    private ObjectMapper objectMapper;

    public void loadData100() throws IOException {
        // Load Users JSON
        List<Users> users = objectMapper.readValue(
                new File("mockdata/users100.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.walhai.instagram.postgres.model.Users.class)
        );
        long postgresStart = System.currentTimeMillis();
        usersPostgres.saveAll(users);
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Users Create 100 Time: " + (postgresEnd - postgresStart) + "ms");

        // Load Follows JSON
        List<com.walhai.instagram.postgres.model.Follows> follows = objectMapper.readValue(
                new File("mockdata/follows100.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.walhai.instagram.postgres.model.Follows.class)
        );
        long postgresStart2 = System.currentTimeMillis();
        followsPostgres.saveAll(follows);
        long postgresEnd2 = System.currentTimeMillis();
        System.out.println("Postgres Follows Create 100 Time: " + (postgresEnd2 - postgresStart2) + "ms");
    }

    public void loadData1K() throws IOException {
        // Load Users JSON
        List<Users> users = objectMapper.readValue(
                new File("mockdata/users1K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.walhai.instagram.postgres.model.Users.class)
        );
        long postgresStart = System.currentTimeMillis();
        usersPostgres.saveAll(users);
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Users Create 1K Time: " + (postgresEnd - postgresStart) + "ms");

        // Load Follows JSON
        List<com.walhai.instagram.postgres.model.Follows> follows = objectMapper.readValue(
                new File("mockdata/follows1K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.walhai.instagram.postgres.model.Follows.class)
        );
        long postgresStart2 = System.currentTimeMillis();
        followsPostgres.saveAll(follows);
        long postgresEnd2 = System.currentTimeMillis();
        System.out.println("Postgres Follows Create 1K Time: " + (postgresEnd2 - postgresStart2) + "ms");
    }

    public void loadData10K() throws IOException {
        // Load Users JSON
        List<Users> users = objectMapper.readValue(
                new File("mockdata/users10K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.walhai.instagram.postgres.model.Users.class)
        );
        long postgresStart = System.currentTimeMillis();
        usersPostgres.saveAll(users);
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Users Create 10K Time: " + (postgresEnd - postgresStart) + "ms");

        // Load Follows JSON
        List<com.walhai.instagram.postgres.model.Follows> follows = objectMapper.readValue(
                new File("mockdata/follows10K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.walhai.instagram.postgres.model.Follows.class)
        );
        long postgresStart2 = System.currentTimeMillis();
        followsPostgres.saveAll(follows);
        long postgresEnd2 = System.currentTimeMillis();
        System.out.println("Postgres Follows Create 10K Time: " + (postgresEnd2 - postgresStart2) + "ms");
    }

    // 1. Ohne Filter (Without Filter)
    public List<Users> getAllUsers() {
        long postgresStart = System.currentTimeMillis();
        List<Users> users = usersPostgres.findAll();
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Find All Users Time: " + (postgresEnd - postgresStart) + "ms");
        return users;
    }

    // 2. Mit Filter (With Filter)
    public List<Users> getUsersByUsername(){
        long postgresStart = System.currentTimeMillis();
        List<Users> users = usersPostgres.findByUsername("handsomeshade");
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Find Users With Filter Time: " + (postgresEnd - postgresStart) + "ms");
        return users;
    }
    // 3. Mit Filter und Projektion (With Filter and Projection)
    public List<UserProjectionDTO> getUsersByUsernameWithProjection(){
        long postgresStart = System.currentTimeMillis();
        List<UserProjectionDTO> users = usersPostgres.findByUsernameWithProjection("handsomeshade");
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Find Users With Filter and Projection Time: " + (postgresEnd - postgresStart) + "ms");
        return users;
    }
    // 4. Mit Filter, Projektion und Sortierung (With Filter, Projection, and Sorting)
    public List<UserProjectionDTO> getUsersByUsernameWithProjectionAndSorting() {
        long postgresStart = System.currentTimeMillis();
        Sort sort = Sort.by("created_at").ascending();
        List<UserProjectionDTO> users = usersPostgres.findByUsernameWithProjectionAndSorting("handsomeshade", sort);
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Find Users By Username With Filter, Projection, and Sorting Time: " + (postgresEnd - postgresStart) + "ms");
        return users;
    }


    public void updateWhereGmail(){
        long postgresStart = System.currentTimeMillis();
        List<Users> gmailUsers = usersPostgres.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().endsWith("gmail.com"))
                .toList();

        if(gmailUsers.isEmpty()){
            return;
        }

        gmailUsers.forEach(user -> {
            String updatedEmail = user.getEmail().replace("@gmail.com", "@yahoo.com");
            user.setEmail(updatedEmail);
        });
        usersPostgres.saveAll(gmailUsers);
        System.out.println("Updated " + gmailUsers.size() + " Gmail emails to Yahoo.");
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Update gmail to yahoo: " + (postgresEnd - postgresStart) + "ms");
    }

    public void deleteData(){
        long postgresStart = System.currentTimeMillis();
        usersPostgres.deleteAll();
        System.out.println("All users have been deleted.");
        long postgresEnd = System.currentTimeMillis();
        System.out.println("Postgres Delete All: " + (postgresEnd - postgresStart) + "ms");

        long postgresStart2 = System.currentTimeMillis();
        followsPostgres.deleteAll();
        System.out.println("All follows have been deleted.");
        long postgresEnd2 = System.currentTimeMillis();
        System.out.println("Postgres Delete All: " + (postgresEnd2 - postgresStart2) + "ms");
    }

    public void deleteFollows(){
        followsPostgres.deleteAll();
    }

    public void deleteDataSilent(){
        usersPostgres.deleteAll();
        followsPostgres.deleteAll();
    }
}
