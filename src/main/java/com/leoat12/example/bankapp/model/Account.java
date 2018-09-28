package com.leoat12.example.bankapp.model;

import com.leoat12.example.bankapp.validation.annotation.NegativeAmount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "account")
@Getter @Setter @NoArgsConstructor
public class Account implements Serializable {

    @Id
    private String number;

    @NonNull
    @NegativeAmount
    private Double balance;

    @NonNull
    private AccountType type;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    public Account(Client client, Agency agency){
        this.number = Integer.toString(client.getId()).concat("-").concat(Integer.toString(agency.getId()));
        this.balance = 0.0;
        this.type = AccountType.COMMON;
        this.client = client;
        this.agency = agency;
    }

    public Account(Client client, Agency agency, Double balance, AccountType type) {

        if(type.equals(AccountType.COMMON) && balance < 0)
            throw new UnsupportedOperationException("The balance for COMMON accounts cannot be negative");
        if(Objects.isNull(client) || Objects.isNull(agency))
            throw new UnsupportedOperationException("Client or Agency cannot be null");

        this.number = Integer.toString(client.getId()).concat("-").concat(Integer.toString(agency.getId()));
        this.balance = balance;
        this.type = type;
        this.client = client;
        this.agency = agency;
    }

    public void updateBalance(Double amount) {
        if(this.type.equals(AccountType.COMMON) && amount < 0
                && this.balance < Math.abs(amount))
            throw new UnsupportedOperationException("The balance to COMMON account cannot be negative.");

        this.balance = this.balance + amount;
    }

    public enum AccountType { COMMON, SILVER, GOLD }


    public void setType(AccountType type) {
        if(type.equals(AccountType.COMMON) && this.balance < 0)
            throw new UnsupportedOperationException();
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number) &&
                Objects.equals(balance, account.balance) &&
                type == account.type &&
                Objects.equals(client, account.client) &&
                Objects.equals(agency, account.agency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, balance, type, client, agency);
    }
}
