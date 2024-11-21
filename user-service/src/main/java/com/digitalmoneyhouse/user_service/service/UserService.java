package com.digitalmoneyhouse.user_service.service;

import com.digitalmoneyhouse.user_service.model.User;
import com.digitalmoneyhouse.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${alias.words.file}")
    private String wordsFile;

    @Transactional
    public User registerUser(User user) {
        user.setCvu(generateCvu());
        user.setAlias(generateAliasFromFile());

        return userRepository.save(user);
    }

    private String generateCvu() {
        return String.format("%022d", new Random().nextInt(1000000000));
    }


    private String generateAliasFromFile() {
        try {

            List<String> words = Files.readAllLines(Paths.get(wordsFile));

            Random random = new Random();
            String alias = words.get(random.nextInt(words.size())) + "."
                    + words.get(random.nextInt(words.size())) + "."
                    + words.get(random.nextInt(words.size()));

            return alias;

        } catch (IOException e) {
            return "default.alias.generated";
        }
    }
}
