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

    private boolean isValidEmail(String email) {
        String pattern = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
        return Pattern.matches(pattern, email);
    }

    public boolean addUser(String email, String password) {
        boolean result = false;
        String error;
        if (!isValidEmail(email)) {
            error = "Email " + email + " is not valid";
            error(error);
            throw new InvalidPropertyException(String.class, "email", error);
        }
        else {
            if (password == "") {
                error = "Empty password detected";
                error(error);
                throw new InvalidPropertyException(String.class, "password", error);
            }
            else {
                UserModel userModel = userDao.findUserByEmail(email);
                if (userModel != null) {
                    error = "User email " + email + " exists";
                    error(error);
                    throw new JavaBeanConverter.DuplicatePropertyException(error);
                } else {
                    userModel = new UserModel();
                    userModel.setUserTypeId(userTypeDao.findIdByUserType("student"));
                    userModel.setEmail(email);
                    userModel.setPassword("password");
                    userModel.setGender(true);
                    userDao.save(userModel);
                    result = true;
                }
            }
        }
        return result;
    }
}
