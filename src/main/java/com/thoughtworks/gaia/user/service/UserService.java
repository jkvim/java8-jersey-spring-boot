package com.thoughtworks.gaia.user.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.UserMapper;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.dao.UserTypeDao;
import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.model.UserModel;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import org.springframework.beans.InvalidPropertyException;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.io.InvalidObjectException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Transactional
public class UserService implements Loggable {
    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserTypeDao userTypeDao;

    public User getUser(Long userId) {
        UserModel userModel = userDao.idEquals(userId).querySingle();
        if (userModel == null) {
            error("User not found with id: " + userId);
            throw new NotFoundException();
        }
        return mapper.map(userModel, User.class);
    }

    public boolean updateUserProfile(Long userId, User user) {
        if (!isValidName(user.getName()) ||
                !isValidTel(user.getTel()) ||
                !isValidSchool(user.getSchool()) ||
                !isValidMajor(user.getMajor())) {
            return false;
        }
        UserModel userModel = userDao.idEquals(userId).querySingle();
        if (userModel == null) {
            error("User not found with id: " + userId);
            throw new NotFoundException();
        }
        userModel.setName(user.getName());
        userModel.setGender(user.getGender());
        userModel.setTel(user.getTel());
        userModel.setSchool(user.getSchool());
        userModel.setMajor(user.getMajor());
        userDao.update(userModel);
        return true;
    }

    public boolean addUser(String email, String password) {
        boolean result = false;
        String error;
        if (!isValidEmail(email)) {
            error = "Email " + email + " is not valid";
            error(error);
            throw new InvalidPropertyException(String.class, "email", error);
        }
        if (password == "") {
            error = "Empty password detected";
            error(error);
            throw new InvalidPropertyException(String.class, "password", error);
        }
        UserModel userModel = userDao.findUserByEmail(email);
        if (userModel != null) {
            error = "User email " + email + " exists";
            error(error);
            throw new JavaBeanConverter.DuplicatePropertyException(error);
        }
        userModel = new UserModel();
        userModel.setUserTypeId(userTypeDao.findIdByUserType("student"));
        userModel.setEmail(email);
        userModel.setPassword("password");
        userModel.setGender(true);
        userDao.save(userModel);
        result = true;

        return result;
    }

    private boolean isValidEmail(String email) {
        String pattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        return Pattern.matches(pattern, email);
    }

    private boolean isValidTel(String tel) {
        String pattern = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
        return Pattern.matches(pattern, tel);
    }

    private boolean isValidSchool(String school) {
        return school.length() <= 64;
    }

    private boolean isValidMajor(String major) {
        return major.length() <= 64;
    }

    private boolean isValidName(String name) {
        String pattern = "^[ A-Za-z]*$";
        return Pattern.matches(pattern, name);
    }
}
