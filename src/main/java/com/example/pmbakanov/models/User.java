package com.example.pmbakanov.models;

import com.example.pmbakanov.models.enums.Role;
import com.example.pmbakanov.models.enums.SpecialAdresses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * Представляет пользователя.
 * Этот объект сопоставлен с таблицей "users" в базе данных.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails, Comparable<User> {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Адрес пользователя.
     */
    private String address;

    /**
     * Номер квартиры пользователя.
     */
    private String flat;

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Флаг активности пользователя.
     */
    private boolean active;

    /**
     * Пароль пользователя.
     */
    @Column(length = 1000)
    private String password;

    /**
     * Электронная почта пользователя.
     */
    @Column(unique = true, updatable = false)
    private String email;

    /**
     * Код активации пользователя.
     */
    private String activationCode;

    /**
     * Роли пользователя.
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    /**
     * Список показаний счетчиков, связанных с пользователем.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<MeterReading> meterReadings = new ArrayList<>();

    /**
     * Список запросов, связанных с пользователем.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Request> requests = new ArrayList<>();

    /**
     * Список записей о потреблении электроэнергии, связанных с пользователем.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<ElectricityMeterReading> electricityMeterReadings = new ArrayList<>();

    /**
     * Возвращает последние показания счетчика пользователя.
     *
     * @return Последние показания счетчика пользователя.
     */
    public MeterReading getLastMeterReading() {
        return areMeterReadings() ? meterReadings.get(meterReadings.size() - 1) : null;
    }

    /**
     * Возвращает последние показания счетчика электроэнергии пользователя.
     *
     * @return Последние показания счетчика электроэнергии пользователя.
     */
    public ElectricityMeterReading getLastElectricityMeterReading() {
        return areElectricityMeterReadings() ? electricityMeterReadings.get(electricityMeterReadings.size() - 1) : null;
    }

    /**
     * Проверяет, есть ли у пользователя показания счетчика.
     *
     * @return true, если у пользователя есть показания счетчика, в противном случае false.
     */
    public boolean areMeterReadings() {
        return meterReadings.size() > 0;
    }

    /**
     * Проверяет, есть ли у пользователя показания счетчика электроэнергии.
     *
     * @return true, если у пользователя есть показания счетчика электроэнергии, в противном случае false.
     */
    public boolean areElectricityMeterReadings() {
        return electricityMeterReadings.size() > 0;
    }

    /**
     * Проверяет, есть ли у пользователя показания счетчика у соседа.
     *
     * @return true, если у пользователя есть показания счетчика у соседа, в противном случае false.
     */
    public boolean areNeighborMeterReadings() {
        return getLastMeterReading().getNeighborHot() != null;
    }

    /**
     * Возвращает первый запрос пользователя.
     *
     * @return Первый запрос пользователя.
     */
    public Request getFirstRequest() {
        Request result = null;
        if (areRequests())
            result = requests.get(0);
        return result;
    }

    /**
     * Проверяет, есть ли у пользователя запросы.
     *
     * @return true, если у пользователя есть запросы, в противном случае false.
     */
    public boolean areRequests() {
        return requests.size() > 0;
    }

    // security config

    /**
     * Получает роли пользователя.
     *
     * @return Роли пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Получает пароль пользователя.
     *
     * @return Пароль пользователя.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Получает имя пользователя.
     *
     * @return Имя пользователя.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Проверяет, истек ли срок действия учетной записи.
     *
     * @return true, если срок действия учетной записи не истек, в противном случае false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, заблокирована ли учетная запись пользователя.
     *
     * @return true, если учетная запись пользователя не заблокирована, в противном случае false.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, активен ли пользователь.
     *
     * @return true, если пользователь активен, в противном случае false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Проверяет, не истек ли срок действия учетных данных пользователя.
     *
     * @return true, если срок действия учетных данных пользователя не истек, в противном случае false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, включен ли пользователь.
     *
     * @return true, если пользователь включен, в противном случае false.
     */
    @Override
    public boolean isEnabled() {
        return active;
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @return true, если пользователь является администратором, в противном случае false.
     */
    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

    /**
     * Проверяет, является ли пользователь техником.
     *
     * @return true, если пользователь является техником, в противном случае false.
     */
    public boolean isTechnician() {
        return roles.contains(Role.ROLE_TECHNICIAN);
    }

    /**
     * Проверяет, является ли пользователь наблюдателем.
     *
     * @return true, если пользователь является наблюдателем, в противном случае false.
     */
    public boolean isSupervisor() {
        return roles.contains(Role.ROLE_SUPERVISOR);
    }

    /**
     * Проверяет, является ли пользователь бухгалтером.
     *
     * @return true, если пользователь является бухгалтером, в противном случае false.
     */
    public boolean isBuh() {
        return roles.contains(Role.ROLE_BUH);
    }

    /**
     * Сравнивает пользователя с другим пользователем на основе адреса.
     *
     * @param o Пользователь для сравнения.
     * @return Отрицательное целое число, ноль или положительное целое число в зависимости от того, меньше ли, равен ли или больше данный пользователь, чем указанный пользователь.
     */
    @Override
    public int compareTo(User o) {
        return this.getAddress().compareTo(o.getAddress());
    }


    /**
     * Проверяет, является ли пользователь пользователем с особым адресом.
     *
     * @return true, если пользователь является пользователем с особым адресом, в противном случае false.
     */
    public boolean isUserWithSpecialAddress() {
        for (SpecialAdresses address : SpecialAdresses.values()) {
            if (address.getTitle().equals(this.address))
                return true;
        }
        return false;
    }

    /**
     * Проверяет, является ли пользователь пользователем с уменьшенными данными.
     *
     * @return true, если пользователь является пользователем с уменьшенными данными, в противном случае false.
     */
    public boolean isUserWithReducedData() {
        for (SpecialAdresses address : SpecialAdresses.values()) {
            if (address.getNeighborAddress().equals(this.address))
                return true;
        }
        return false;
    }
}
