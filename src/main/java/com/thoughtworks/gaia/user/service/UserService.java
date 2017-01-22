package com.thoughtworks.gaia.user.service;

import com.thoughtworks.gaia.Helper;
import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.UserMapper;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.dao.UserProfileDao;
import com.thoughtworks.gaia.user.dao.UserTypeDao;
import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.entity.UserProfile;
import com.thoughtworks.gaia.user.model.UserModel;
import com.thoughtworks.gaia.user.model.UserProfileModel;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter.DuplicatePropertyException;
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
    private UserProfileDao userProfileDao;

    @Autowired
    private UserTypeDao userTypeDao;

    public User getUserById(Long userId) throws NotFoundException {
        UserModel userModel = userDao.idEquals(userId).querySingle();
        if (userModel == null) {
            error("User not found with ID " + userId);
            throw new NotFoundException();
        }
        return mapper.map(userModel, User.class);
    }

    public User getUserByEmail(String email) throws NotFoundException, InvalidPropertyException {
        validateEmail(email);
        UserModel userModel = userDao.getUserByEmail(email);
        if (userModel == null) {
            error("User not found with email " + email);
            throw new NotFoundException();
        }
        return mapper.map(userModel, User.class);
    }

    public UserProfile getUserProfileById(Long userId) throws NotFoundException {
        UserProfileModel userProfileModel = userProfileDao.idEquals(userId).querySingle();
        if (userProfileModel == null) {
            error("User profile not found with ID " + userId);
            throw new NotFoundException();
        }
        return mapper.map(userProfileModel, UserProfile.class);
    }

    public Boolean addUser(String email, String password) throws InvalidPropertyException, DuplicatePropertyException {
        String error;
        validateEmail(email);
        validatePassword(password);
        UserModel userModel = userDao.getUserByEmail(email);
        if (userModel != null) {
            error = "User email " + email + " exists";
            error(error);
            throw new DuplicatePropertyException(error);
        }
        userModel = new UserModel();
        userModel.setUserTypeId(userTypeDao.findIdByUserType("student"));
        userModel.setEmail(email);
        userModel.setPassword("password");
        userDao.save(userModel);

        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel.setUserModel(userModel);
        userProfileModel.setGender(Helper.defaultGender);
        userProfileDao.save(userProfileModel);
        return true;
    }

    public boolean updateUserProfile(Long userId, UserProfile user) throws NotFoundException {
        if (!Helper.isValidName(user.getName()) ||
                !Helper.isValidTel(user.getTel()) ||
                !Helper.isValidSchool(user.getSchool()) ||
                !Helper.isValidMajor(user.getMajor())) {
            return false;
        }
        UserProfileModel userProfileModel = userProfileDao.idEquals(userId).querySingle();
        if (userProfileModel == null) {
            error("User not found with ID " + userId);
            throw new NotFoundException();
        }
        userProfileModel.setName(user.getName());
        userProfileModel.setGender(user.getGender());
        userProfileModel.setTel(user.getTel());
        userProfileModel.setSchool(user.getSchool());
        userProfileModel.setMajor(user.getMajor());
        userProfileDao.update(userProfileModel);
        return true;
    }

    public User loginUser(String email, String password) throws InvalidPropertyException, DuplicatePropertyException {
        String error;
        validateEmail(email);
        validatePassword(password);
        UserModel userModel = userDao.getUserByEmail(email);
        if (userModel == null) {
            error = "User not found with email " + email;
            error(error);
            throw new NotFoundException();
        }
        if (userModel.getPassword().equals(password)) {
            return mapper.map(userModel, User.class);
        } else {
            return null;
        }
    }

    private void validateEmail(String email) throws InvalidPropertyException {
        if (!Helper.isValidEmail(email)) {
            String error = "Email " + email + " is not valid";
            error(error);
            throw new InvalidPropertyException(String.class, "email", error);
        }
    }

    private void validatePassword(String password) throws InvalidPropertyException {
        if (password.isEmpty()) {
            String error = "Empty password detected";
            error(error);
            throw new InvalidPropertyException(String.class, "password", error);
        }
    }
}
