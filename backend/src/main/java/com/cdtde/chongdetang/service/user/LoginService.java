package com.cdtde.chongdetang.service.user;

import java.util.Map;

public interface LoginService {
    public Map<String,String> getToken(String phone,String password);
}
