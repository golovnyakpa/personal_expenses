package com.github.golovnyakpa.personal_expenses.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseDtoRq {
    private Long id;

    @NotEmpty(message = "The expense title is required")
    private String title;

    @NotNull(message = "The expense amount is required")
    @Positive(message = "Expense should be positive")
    @Max(value = 100_000_000, message = "Max expense size should be smaller than 100M")
    private int amount;

    @NotEmpty(message = "The expense category is required")
    private String category;

    @Size(max = 1000, message = "Description should not be bigger than 1000 symbols")
    private String description;
    // todo add time zone support

    @JsonProperty("date_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
}
