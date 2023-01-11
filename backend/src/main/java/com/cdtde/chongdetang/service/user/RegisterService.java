package com.cdtde.chongdetang.service.user;

import java.util.Map;

public interface RegisterService {
    public Map<String,String> register(String phone,String password,String confirmPassword);
}
