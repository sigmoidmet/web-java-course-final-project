package com.example.final_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {

    @Id
    private String token;

    @ManyToOne(optional = false)
    private Client client;

    private LocalDateTime expirationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PasswordResetRequest that = (PasswordResetRequest) o;
        return token != null && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}
