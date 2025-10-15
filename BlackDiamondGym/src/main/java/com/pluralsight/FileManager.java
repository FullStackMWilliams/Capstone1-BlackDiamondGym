package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static final String USERS_FILE = "users.csv";
    public static final String MEMBERS_FILE = "members.csv";
    public static final String TRANSACTIONS_FILE = "transactions.csv";

    public static void ensureFiles() {
        try {
            createIfMissing(USERS_FILE);
            createIfMissing(MEMBERS_FILE);
            createIfMissing(TRANSACTIONS_FILE);


        } catch (Exception e) {
            System.out.println(Colors.Red + "File set up error: " + e.getMessage() + Colors.RESET);

        }
    }

    public static void createIfMissing(String file) throws IOException {
        Path path = Path.of(file);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public static List<User> readUsers() {
        List<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",", -1);
                if (t.length >= 3) list.add(new User(t[0], t[1], t[2]));
            }
        } catch (IOException ignored) {
        }
        return list;

    }
}

