package com.example.model;

import java.util.Objects;

public class User {

private final String userName;
    private final String password;
    private final String email;
    private final String firstName;
    private final String secondName;
    private final String dateOfBirth;
    private final String sendToEmail;
    private final String sendToCopyEmail;
    private final String emailSubject;
    private final String emailText;

    private User(Builder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.secondName = builder.secondName;
        this.dateOfBirth = builder.dateOfBirth;
        this.sendToEmail = builder.sendToEmail;
        this.sendToCopyEmail = builder.sendToCopyEmail;
        this.emailSubject = builder.emailSubject;
        this.emailText = builder.emailText;
    }

    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getSecondName() { return secondName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getSendToEmail() { return sendToEmail; }
    public String getSendToCopyEmail() { return sendToCopyEmail; }
    public String getEmailSubject() { return emailSubject; }
    public String getEmailText() { return emailText; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof User)) return false;
        User user = (User) object;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
        private String userName;
        private String password;
        private String email;
        private String firstName;
        private String secondName;
        private String dateOfBirth;
        private String sendToEmail;
        private String sendToCopyEmail;
        private String emailSubject;
        private String emailText;

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withSecondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public Builder withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withSendToEmail(String sendToEmail) {
            this.sendToEmail = sendToEmail;
            return this;
        }
        public Builder withSendToCopyEmail(String sendToCppyEmail) {
            this.sendToCopyEmail = sendToCppyEmail;
            return this;
        }

        public Builder withEmailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }

        public Builder withEmailText(String emailText) {
            this.emailText = emailText;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
