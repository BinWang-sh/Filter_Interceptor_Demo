package com.example.bin.filterinterceptordemo.service.impl;

import com.example.bin.filterinterceptordemo.service.UserPrivilegeService;

import java.util.Arrays;

public class UserPrivilegeServiceImpl implements UserPrivilegeService {

    private String[] mBlackList = {"120001", "130002", "140000"};

    @Override
    public boolean isInBlackList(String userId) {

        return Arrays.stream(mBlackList).filter(m->m.equals(userId)).findAny().isPresent();
    }

    @Override
    public boolean isVIP(String userId) {

        int usrId = Integer.getInteger(userId);
        if ((usrId>0)&&(usrId<=10000)) {
            return true;
        }

        return false;
    }
}
