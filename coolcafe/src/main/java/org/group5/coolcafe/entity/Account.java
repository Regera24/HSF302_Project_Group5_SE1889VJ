package org.group5.coolcafe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Account")
public class Account extends AbstractEntity {
    @Column(name = "Username", unique = true, nullable = false)
    String username;

    @Column(name = "name" ,columnDefinition = "NVARCHAR(255)")
    String name;

    @Column(name = "Password", nullable = false)
    String password;

    @Column(name = "Email", unique = true)
    String email;

    @Column(name = "PhoneNumber", unique = true, nullable = false)
    String phoneNumber;

    @Column(name = "Avatar", columnDefinition = "NVARCHAR(255)")
    String avatar;

    @Column(name = "IsActive")
    Boolean isActive;

    @Column(name = "Gender")
    Boolean gender;

    @Column(name = "dateOfBirth")
    LocalDate dateOfBirth;

    @OneToMany(mappedBy = "account",cascade = { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    List<ReserveTable> reserveTables = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    Role role;
}
