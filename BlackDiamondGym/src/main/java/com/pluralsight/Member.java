package com.pluralsight;

public class Member extends User {
    private Membership membership;

    public Member() {
        super();
        setRole("MEMBER");
    }
    public Member(String username, String password) {
        super(username,password, "MEMBER");
    }
    public Membership getMembership() {
        return membership;
    }
    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}
