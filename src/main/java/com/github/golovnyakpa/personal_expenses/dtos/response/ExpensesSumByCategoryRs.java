package com.github.golovnyakpa.personal_expenses.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpensesSumByCategoryRs {
    private String category;
    private Long amount;
}
