package com.lil.maven.dao.mapper;

import com.lil.maven.pojo.User;
import com.lil.maven.pojo.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author:lil
 * @Date: 2022-02-27
 */
@Mapper
@Repository
public interface UserProfileMapper {
    UserProfile queryAllByUserId(User user);

    void insertAtAll(UserProfile userProfile);
}
