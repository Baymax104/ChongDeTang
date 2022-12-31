package com.kob.backend.service.impl.user;
import com.kob.backend.pojo.user;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.InfoSerevice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoSerevice {
    @Override
    public Map<String, String> getinfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        user user = loginUser.getUser();

        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("id",user.getId().toString());
        map.put("username",user.getUsername());
        map.put("photo",user.getPhoto());
        map.put("mail",user.getMail());
        map.put("phone",user.getPhone());

        return map;
    }
}
