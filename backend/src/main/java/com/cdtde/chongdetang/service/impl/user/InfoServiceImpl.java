package com.cdtde.chongdetang.service.impl.user;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.impl.utils.UserDetailsImpl;
import com.cdtde.chongdetang.service.user.InfoSerevice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoSerevice {
    @Override
    public Map<String, String> getInfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("username",user.getUsername());
        map.put("photo",user.getPhoto());
        map.put("mail",user.getMail());
        map.put("phone",user.getPhone());
        map.put("sex",user.getSex());
        map.put("age",user.getAge());

        return map;
    }
}
