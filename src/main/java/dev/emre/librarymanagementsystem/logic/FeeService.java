package dev.emre.librarymanagementsystem.logic;

import dev.emre.librarymanagementsystem.models.Fee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FeeService {
    private static final BigDecimal LATE_FEE_PER_WEEK = new BigDecimal("2.50");
    private static final BigDecimal DAMAGED_FEE = new BigDecimal("15.00");

    /**
     * Calculates the late fee for a returned item based on the due date and the return date.
     * If the item is returned on or before the due date, no late fee is charged.
     *
     * @param dueDate the date the item was originally due to be returned, must not be null
     * @param returnDate the date the item was actually returned, must not be null
     * @return a {@code Fee} object representing the calculated late fee, or {@code null} if the item was returned on or before the due date
     * @throws NullPointerException if either {@code dueDate} or {@code returnDate} is null
     */
   public Fee calculateLateFee(LocalDate dueDate, LocalDate returnDate){
    if(returnDate.isBefore(dueDate) || returnDate.equals(dueDate)){
        return null;
    }
    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
    long weeksLate = (daysLate + 6) / 7;

    BigDecimal totalAmount = LATE_FEE_PER_WEEK.multiply(BigDecimal.valueOf(weeksLate));
    return new Fee(totalAmount, "Late return: " + weeksLate +" week(s)", returnDate);
   }

    /**
     * Calculates the damaged fee for an item based on whether it is damaged.
     * If the item is not damaged, no fee is charged.
     *
     * @param damaged a boolean indicating whether the item is damaged. If {@code true}, a damaged fee is applied.
     * @param feeDate the date when the fee is being calculated. Must not be null.
     * @return a {@code Fee} object representing the damaged fee if the item is damaged, or {@code null} if the item is not damaged.
     */
   public Fee calculateDamagedFee(boolean damaged, LocalDate feeDate){
    if(!damaged){
        return null;
    }
    return new Fee(DAMAGED_FEE, "Damaged book", feeDate);
   }


}
