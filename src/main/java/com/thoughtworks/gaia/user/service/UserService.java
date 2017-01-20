package com.thoughtworks.gaia.user.service;

import com.thoughtworks.gaia.Helper;
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

@Component
@Transactional
public class UserService implements Loggable {
    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserTypeDao userTypeDao;

    public User getUserById(Long userId) {
        UserModel userModel = userDao.idEquals(userId).querySingle();
        if (userModel == null) {
            error("User not found with ID " + userId);
            throw new NotFoundException();
        }
        return mapper.map(userModel, User.class);
    }

    public User getUserByEmail(String email) {
        if (!Helper.isValidEmail(email)) {
            String error = "Email " + email + " is not valid";
            error(error);
            throw new InvalidPropertyException(String.class, "email", error);
        }
        UserModel userModel = userDao.getUserByEmail(email);
        if (userModel == null) {
            error("User not found with email " + email);
            throw new NotFoundException();
        }
        return mapper.map(userModel, User.class);
    }

    public boolean updateUserProfile(Long userId, User user) {
        if (!Helper.isValidName(user.getName()) ||
                !Helper.isValidTel(user.getTel()) ||
                !Helper.isValidSchool(user.getSchool()) ||
                !Helper.isValidMajor(user.getMajor())) {
            return false;
        }
        UserModel userModel = userDao.idEquals(userId).querySingle();
        if (userModel == null) {
            error("User not found with ID " + userId);
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

    public Boolean addUser(String email, String password) {
        String error;
        if (!Helper.isValidEmail(email)) {
            error = "Email " + email + " is not valid";
            error(error);
            throw new InvalidPropertyException(String.class, "email", error);
        }
        if (password == "") {
            error = "Empty password detected";
            error(error);
            throw new InvalidPropertyException(String.class, "password", error);
        }
        UserModel userModel = userDao.getUserByEmail(email);
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
        return true;
    }
}
