package dev.emre.librarymanagementsystem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Fee {
    private final String id;
    private final BigDecimal amount;
    private final String reason;
    private final LocalDate date;
    private boolean isPaid;


    public Fee(BigDecimal amount, String reason, LocalDate date) {
        this.amount = amount;
        this.reason = reason;
        this.date = date;
        this.isPaid = false;
        this.id = UUID.randomUUID().toString();
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getReason() {
        return reason;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getId() {
        return id;
    }
    public boolean isPaid() {
        return isPaid;
    }
    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Fee [amount=" + amount + ", reason=" + reason + ", date=" + date + ", isPaid=" + isPaid + "]";
    }

}
