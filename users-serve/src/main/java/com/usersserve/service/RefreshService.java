package com.usersserve.service;


import com.usersserve.entity.RefreshToken;

public interface RefreshService {
    RefreshToken createRefreshToken(String username);

    RefreshToken verifyExpiration(RefreshToken token);
}
