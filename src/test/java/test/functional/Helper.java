package test.functional;

import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.model.UserModel;

public class Helper {
    private static Long userId = 1L;

    public static String getInvalidEmail() {
        return "@invalidemail.com";
    }

    public static String getValidEmail() {
        return "success@thoughtworks.com";
    }

    public static String getValidPassword() {
        return "password";
    }

    public static Long getNonExistingUserId() { return userId++; };

    public static UserModel getNewUserModelbyUsernameAndPassword(String email, String password) {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setGender(true);
        return userModel;
    }

    public static UserModel getNewUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail("user@thoughtworks.com");
        userModel.setPassword("password");
        userModel.setGender(true);
        return userModel;
    }

    public static User getNewUserByUserId(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setEmail("peterwaltson@thoughtworks.com");
        user.setPassword("password");
        user.setName("Peter Waltson");
        user.setGender(true);
        user.setSchool("Peking University");
        user.setMajor("EE");
        user.setTel("13566668888");
        return user;
    }
}
