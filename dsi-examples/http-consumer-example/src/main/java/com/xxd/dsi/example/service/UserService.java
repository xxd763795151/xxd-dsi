package com.xxd.dsi.example.service;

import com.xxd.dsi.annotation.ServiceInfo;
import com.xxd.dsi.example.beans.User;

@ServiceInfo
public interface UserService {

    Object get(User user);
}
