package com.leoat12.example.bankapp.model;

import com.leoat12.example.bankapp.utils.DocumentUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "client")
@Getter @Setter @NoArgsConstructor
@ToString
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String document;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Min(1)
    private Integer age;

    public Client(String name, String document, Gender gender, Integer age) {

        if(StringUtils.isBlank(name) || StringUtils.isBlank(document) || gender == null || age == null || age <= 0)
            throw new IllegalArgumentException("Any of the fields cannot be null or empty.");
        if(!DocumentUtils.isCPF(document))
            throw new IllegalArgumentException("The document number is invalid.");

        this.name = name;
        this.document = document;
        this.gender = gender;
        this.age = age;
    }

    public enum Gender { M, F }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(name, client.name) &&
                Objects.equals(document, client.document) &&
                gender == client.gender &&
                Objects.equals(age, client.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, document, gender, age);
    }
}
