package org.example.org.example.ws.org.example.ws.repository;

import org.example.org.example.ws.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {
}
