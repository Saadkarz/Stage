package com.example.client.Repository;
import com.example.client.Bean.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ClientsRepository extends JpaRepository<Clients, Long> {
}
