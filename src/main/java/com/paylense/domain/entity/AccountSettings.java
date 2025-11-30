package com.paylense.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "account_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private boolean emailNotificationsEnabled = true;
    private boolean smsNotificationsEnabled = true;
    private String preferredLanguage = "en";
}
