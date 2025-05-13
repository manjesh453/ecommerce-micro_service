package com.usersserve.repo;



import com.usersserve.entity.RefreshToken;
import com.usersserve.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshRepo extends JpaRepository<RefreshToken,Integer> {

    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByCustomer(Users customer);
}
