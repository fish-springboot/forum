package com.github.fish56.forum.user;

import com.github.fish56.forum.service.ServiceResponse;

public interface UserService {
    /**
     * 创建一个用户
     * @param user
     * @return
     */
    public ServiceResponse<User> create(User user);

    /**
     * 根据vo的数据更新一个用户
     * @param userId
     * @param userVo
     * @return
     */
    public ServiceResponse<User> update(Integer userId, UserVo userVo);

    /**
     * 更新token
     * @param user
     * @return
     */
    public User updateToken(User user);

    /**
     * 判断用户的email和密码是否匹配
     * @param email
     * @param password
     * @return
     */
    public Boolean checkByEmail(String email, String password);

    /**
     * 判断用户的id和密码是否匹配
     * @param id
     * @param password
     * @return
     */
    public Boolean checkById(Integer id, String password);
}
