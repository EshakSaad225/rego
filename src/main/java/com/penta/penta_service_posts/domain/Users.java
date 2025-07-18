package com.penta.penta_service_posts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name="user_entity")
public class Users {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column
    private String username;

    @OneToOne(mappedBy = "user", optional = true)
    private UserAttribute userAttribute;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userAttributeId=" + (userAttribute != null ? userAttribute.getId() : null) +
                ", Picture=" + (userAttribute != null ? userAttribute.getValue() : null) +
                '}';
    }

}
        