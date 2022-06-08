package com.pablito.shop.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Audited
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @NotAudited
    private String password;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;

    private String token;

    @ManyToMany
    @JoinTable(name = "user_role", inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
