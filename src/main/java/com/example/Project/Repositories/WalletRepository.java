

package com.example.Project.Repositories;
import com.example.Project.entities.Wallet;
import com.example.Project.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
    Wallet findByUser(User user);

    @Query("SELECT COUNT(w) FROM Wallet w WHERE w.subscription = :subscription")
    Long countBySubscription(@Param("subscription") String subscription);
}