package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Fee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FeeService {
    private static final BigDecimal LATE_FEE_PER_WEEK = new BigDecimal("2.50");
    private static final BigDecimal DAMAGED_FEE = new BigDecimal("15.00");


   public Fee calculateLateFee(LocalDate dueDate, LocalDate returnDate){
    if(returnDate.isBefore(dueDate) || returnDate.equals(dueDate)){
        return null;
    }
    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
    long weeksLate = (daysLate + 6) / 7;

    BigDecimal totalAmount = LATE_FEE_PER_WEEK.multiply(BigDecimal.valueOf(weeksLate));
    return new Fee(totalAmount, "Late return: " + weeksLate +" week(s)", LocalDate.now());
   }

   public Fee calculateDamagedFee(boolean damaged){
    if(!damaged){
        return null;
    }
    return new Fee(DAMAGED_FEE, "Damaged book", LocalDate.now());
   }


}
