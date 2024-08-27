package core;

public class UserData {
    private boolean isSuccess;
    private int errorCode;
    private String errorMessage;
    private User user;

    public UserData(boolean isSuccess, int errorCode, String errorMessage, User user) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.user = user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public User getUser() {
        return user;
    }

    public static class User {
        private int id;
        private String name;
        private String gender;
        private int age;
        private String city;
        private String registrationDate;

        public User(int id, String name, String gender, int age, String city, String registrationDate) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.age = age;
            this.city = city;
            this.registrationDate = registrationDate;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public String getCity() {
            return city;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }
    }
}

