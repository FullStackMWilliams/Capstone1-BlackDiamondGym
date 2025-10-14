package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Membership {

    public enum Plan {
        BASIC(29.99),
        PREMIUM(49.99),
        VIP(79.99);

        private final double price;

        Plan(double price) {
            this.price = price;
        }
        public double getPrice() {
            return price;
        }
        public static double getADDonPrice(String addOnName) {
            String key = addOnName.toLowerCase().trim();
            switch (key) {
                case "personal trainer": return 100.00;
                case "gym classes": return 25.00;
                case "pool": return 10.00;
                case "sauna": return 20.00;
                case "towel service": return 5.00;
                default: return 0.0;
            }
        }

        private String username;
        private Plan plan;
        private List<String> addOns = new ArrayList<>();
        private String status = "ACTIVE";

        public Membership() { }

        public Membership(String username,Plan plan, List<String> addOns) {
            this.username = username;
            this.plan = plan;
            if (addOns != null) this.addOns.addAll(addOns);
        }

        public String getUsername() {
            return username;
        }

        public Plan getPlan() {
            return plan;
        }

        public List<String> getAddOns() {
            return addOns;
        }

        public String getStatus() {
            return status;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPlan(Plan plan) {
            this.plan = plan;
        }

        public void setAddOns(List<String> addOns) {
            this.addOns = addOns;
            if ( != null) this.addOns.addAll(list)
        }
        public static List<String> parseAddOns (String addOnString) {
            List<String> list = new ArrayList<>();
            if (addOnString == null || addOnString.trim().isEmpty()) return list;
            String[] parts = addOnString.split(";");
            for (String p : parts) {
                String clean = p.trim();
                if (!clean.isEmpty()) list.add(clean);
            }
            return list;
        }


    }
}
