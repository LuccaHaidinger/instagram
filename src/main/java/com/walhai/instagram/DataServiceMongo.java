package com.walhai.instagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walhai.instagram.mongo.model.Follows;
import com.walhai.instagram.mongo.model.Users;
import com.walhai.instagram.mongo.persistence.FollowsRepositoryMongo;
import com.walhai.instagram.mongo.persistence.UsersRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataServiceMongo {

    @Autowired
    private UsersRepositoryMongo usersMongo;

    @Autowired
    private FollowsRepositoryMongo followsMongo;

    @Autowired
    private ObjectMapper objectMapper;

    public void loadData100() throws IOException {
        // Load Users JSON
        List<Users> users = objectMapper.readValue(new File("mockdata/users100.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Users.class));
        long mongoStart = System.currentTimeMillis();
        usersMongo.saveAll(users);
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Users Create 100 Time: " + (mongoEnd - mongoStart) + "ms");

        // Load Follows JSON
        List<Follows> follows = objectMapper.readValue(new File("mockdata/follows100.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Follows.class));
        long mongoStart2= System.currentTimeMillis();
        followsMongo.saveAll(follows);
        long mongoEnd2 = System.currentTimeMillis();
        System.out.println("MongoDB Follows Create 100 Time: " + (mongoEnd2 - mongoStart2) + "ms");
    }

    public void loadData1K() throws IOException {
        // Load Users JSON
        List<Users> users = objectMapper.readValue(new File("mockdata/users1K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Users.class));
        long mongoStart = System.currentTimeMillis();
        usersMongo.saveAll(users);
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Users Create 1K Time: " + (mongoEnd - mongoStart) + "ms");

        // Load Follows JSON
        List<Follows> follows = objectMapper.readValue(new File("mockdata/follows1K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Follows.class));
        long mongoStart2= System.currentTimeMillis();
        followsMongo.saveAll(follows);
        long mongoEnd2 = System.currentTimeMillis();
        System.out.println("MongoDB Follows Create 1K Time: " + (mongoEnd2 - mongoStart2) + "ms");
    }

    public void loadData10K() throws IOException {
        // Load Users JSON
        List<Users> users = objectMapper.readValue(new File("mockdata/users10K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Users.class));
        long mongoStart = System.currentTimeMillis();
        usersMongo.saveAll(users);
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Users Create 10K Time: " + (mongoEnd - mongoStart) + "ms");

        // Load Follows JSON
        List<Follows> follows = objectMapper.readValue(new File("mockdata/follows10K.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Follows.class));
        long mongoStart2= System.currentTimeMillis();
        followsMongo.saveAll(follows);
        long mongoEnd2 = System.currentTimeMillis();
        System.out.println("MongoDB Follows Create 10K Time: " + (mongoEnd2 - mongoStart2) + "ms");
    }

    // 1. Ohne Filter (Without Filter)
    public List<Users> getAllUsers() {
        long mongoStart = System.currentTimeMillis();
        List<Users> users = usersMongo.findAll();
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Find All Users Time: " + (mongoEnd - mongoStart) + "ms");
        return users;
    }

    // 2. Mit Filter (With Filter)
    public List<Users> getUsersByEmail() {
        long mongoStart = System.currentTimeMillis();
        List<Users> users = usersMongo.findByEmailRegex("gmail.com$");
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Find Users With Filter Time: " + (mongoEnd - mongoStart) + "ms");
        return users;
    }

    // 3. Mit Filter und Projektion (With Filter and Projection)
    public List<Users> getUsersByEmailWithProjection() {
        long mongoStart = System.currentTimeMillis();
        List<Users> users = usersMongo.findByEmailRegexWithProjection("gmail.com$");
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Find Users With Filter and Projection Time: " + (mongoEnd - mongoStart) + "ms");
        return users;
    }

    // 4. Mit Filter, Projektion und Sortierung (With Filter, Projection, and Sorting)
    public List<Users> getUsersByEmailWithProjectionAndSorting(String emailRegex, String sortField, boolean ascending) {
        long mongoStart = System.currentTimeMillis();
        Sort sort = ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        List<Users> users = usersMongo.findByEmailRegexWithProjectionAndSorting(emailRegex, sort);
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Find Users With Filter, Projection, and Sorting Time: " + (mongoEnd - mongoStart) + "ms");
        return users;
    }

    public void updateWhereGmail() {
        long mongoStart = System.currentTimeMillis();
        List<Users> gmailUsers = usersMongo.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().endsWith("@gmail.com"))
                .toList();

        if (gmailUsers.isEmpty()) {
            return;
        }

        gmailUsers.forEach(user -> {
            String updatedEmail = user.getEmail().replace("@gmail.com", "@yahoo.com");
            user.setEmail(updatedEmail);
        });
        usersMongo.saveAll(gmailUsers);
        System.out.println("Updated " + gmailUsers.size() + " Gmail emails to Yahoo.");
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Update gmail to yahoo: " + (mongoEnd - mongoStart) + "ms");
    }

    public void deleteData() {
        long mongoStart = System.currentTimeMillis();
        List<Users> usersToDelete = usersMongo.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().endsWith("@aol.com"))
                .toList();

        if (usersToDelete.isEmpty()) {
            System.out.println("No users found with emails ending in @aol.com.");
            return;
        }

        // Delete matching users
        usersMongo.deleteAll(usersToDelete);
        System.out.println("Deleted " + usersToDelete.size() + " users with emails ending in @aol.com.");
        long mongoEnd = System.currentTimeMillis();
        System.out.println("MongoDB Delete Where Email @aol.com: " + (mongoEnd - mongoStart) + "ms");
    }
}