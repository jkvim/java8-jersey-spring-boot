package test.functional;

import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.model.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Helper {
    public static String getInvalidEmail() {
        return "@invalidemail.com";
    }

    public static String getValidEmail() {
        return "success@thoughtworks.com";
    }

    public static String getValidPassword() {
        return "password";
    }

    public static Long getNonExistingUserId() { return 0L; };

    public static UserModel getUserModelbyUsernameAndPassword(String email, String password) {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setGender(true);
        return userModel;
    }

    public static UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail("user@thoughtworks.com");
        userModel.setPassword("password");
        userModel.setGender(true);
        return userModel;
    }

    public static User getUserByUserId(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setEmail(defaultEmail);
        user.setPassword(defaultPassword);
        user.setName(defaultName);
        user.setGender(defaultGender);
        user.setSchool(defaultSchool);
        user.setMajor(defaultMajor);
        user.setTel(defaultTel);
        return user;
    }

    public static String getNonExistingEmail() {
        return "null@thoughtworks.com";
    }

    public static Map getUserMap(String email, String password, String name, boolean gender, String school, String major, String tel) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("email", email != null ? email : defaultEmail);
        userMap.put("password", password != null ? password : defaultPassword);
        userMap.put("name", name != null ? name : defaultName);
        userMap.put("gender", gender);
        userMap.put("school", school != null ? school : defaultSchool);
        userMap.put("major", major != null ? major : defaultMajor);
        userMap.put("tel", tel != null ? tel : defaultTel);
        return userMap;
    }

    public final static String defaultEmail = "peterwaltson@thoughtworks.com";
    public final static String defaultPassword = "password";
    public final static String defaultName = "Peter Waltson";
    public final static boolean defaultGender = true;
    public final static String defaultSchool = "Peking University";
    public final static String defaultMajor = "EE";
    public final static String defaultTel = "13566668888";
}
