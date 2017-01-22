package test.functional;

import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.entity.UserProfile;
import com.thoughtworks.gaia.user.model.UserModel;
import com.thoughtworks.gaia.user.model.UserProfileModel;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Helper {
    public static UserModel getUserModelbyUsernameAndPassword(String email, String password) {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail(email);
        userModel.setPassword(password);
        return userModel;
    }

    public static UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail("user@thoughtworks.com");
        userModel.setPassword("password");
        return userModel;
    }

    public static User getUserByUserId(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setEmail(defaultEmail);
        user.setPassword(defaultPassword);
        return user;
    }

    public static UserProfile getUserProfileByUserId(Long userId) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        userProfile.setName(defaultName);
        userProfile.setGender(defaultGender);
        userProfile.setSchool(defaultSchool);
        userProfile.setMajor(defaultMajor);
        userProfile.setTel(defaultTel);
        return userProfile;
    }

    public static Map getUserMap(String email, String password) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("email", email != null ? email : defaultEmail);
        userMap.put("password", password != null ? password : defaultPassword);
        return userMap;
    }

    public static Map getUserProfileMap(String name, boolean gender, String school, String major, String tel) {
        Map<String, Object> userProfileMap = new HashMap<String, Object>();
        userProfileMap.put("name", name != null ? name : defaultName);
        userProfileMap.put("gender", gender);
        userProfileMap.put("school", school != null ? school : defaultSchool);
        userProfileMap.put("major", major != null ? major : defaultMajor);
        userProfileMap.put("tel", tel != null ? tel : defaultTel);
        return userProfileMap;
    }

    public final static String nonExistingEmail = "null@thoughtworks.com";
    public final static String invalidEmail = "@invalidemail.com";
    public final static Long nonExistingUserId = 0L;
    public final static String defaultEmail = "peterwaltson@thoughtworks.com";
    public final static String defaultPassword = "password";
    public final static String defaultName = "Peter Waltson";
    public final static boolean defaultGender = true;
    public final static String defaultSchool = "Peking University";
    public final static String defaultMajor = "EE";
    public final static String defaultTel = "13566668888";
}
