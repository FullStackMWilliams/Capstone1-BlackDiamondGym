package com.pluralsight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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


        }catch (Exception e) {
            System.out.println(Colors.Red + "File set up error: " + e.getMessage() +Colors.RESET);

        }
        }
        public static void createIfMissing(String file) throws IOException {
            Path path = Path.of(file);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        }
        public


        private static String sanitize(String s) {
        if ( s == null) return "";
        return s.replace(","," ");
    }
}
