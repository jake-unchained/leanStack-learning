package org.example.org.example.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@EnableTransactionManagement
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Greeting {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String text;

    public Greeting (){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
