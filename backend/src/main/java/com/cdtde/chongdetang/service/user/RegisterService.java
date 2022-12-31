package com.cdtde.chongdetang.service.user;

import java.util.Map;

public interface RegisterService {
    public Map<String,String> register(String username,String password,String confirmPassword,String mail,String phone);
}
