package com.penta.penta_service_posts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name="user_attribute")
public class UserAttribute {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(updatable = false)
    private String name;

    @Column
    private String value;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private Users user;

    @Override
    public String toString() {
        return "UserAttribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                '}';
    }

}
