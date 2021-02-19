package com.example.bin.filterinterceptordemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String usrLogin(@RequestParam("usrId")String usrId,
                           @RequestParam("channelId")int channelId,
                           @RequestParam("isSuperUser") boolean isSuperUser) {

        String usrInfo = usrId + " from " + channelId;

        if (isSuperUser) {
            usrInfo += "Super User " + usrInfo;
        }

        return usrInfo;
    }
}
