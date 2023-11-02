package com.github.golovnyakpa.personal_expenses.services;

import com.github.golovnyakpa.personal_expenses.dao.entities.Expense;
import com.github.golovnyakpa.personal_expenses.dao.repositories.ExpensesRepository;
import com.github.golovnyakpa.personal_expenses.dtos.request.ExpenseDtoRq;
import com.github.golovnyakpa.personal_expenses.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExpensesServiceImplTest {

    @Mock
    private ExpensesRepository expenseRepoMock;

    @InjectMocks
    private ExpensesServiceImpl expensesService;

//    @Test
//    public void testGetAllExpenses() {
//        List<Expense> mockExpenses = Arrays.asList(new Expense(), new Expense());
//        Mockito.when(expenseRepoMock.findAll()).thenReturn(mockExpenses);
//
//        List<Expense> expenses = expensesService.getAll();
//
//        assertEquals(2, expenses.size());
//    }

    @Test
    public void testGetExpenseById() {
        Long expenseId = 1L;
        Expense mockExpense = new Expense();
        mockExpense.setId(expenseId);

        System.out.println(mockExpense);

        // Mock the repository to return the expense with the specified ID
        Mockito.when(expenseRepoMock.findById(expenseId)).thenReturn(Optional.of(mockExpense));

        Optional<Expense> expense = expensesService.getById(expenseId);

        // Verify that the service returns the expected expense
        assertEquals(expenseId, expense.get().getId());
    }

    @Test
    public void testGetExpenseByIdNotFound() {
        Long expenseId = 1L;

        // Mock the repository to return an empty optional, indicating the expense is not found
        Mockito.when(expenseRepoMock.findById(expenseId)).thenReturn(Optional.empty());

        // Verify that the service throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> expensesService.getById(expenseId));
    }

    @Test
    public void testDeleteExpense() {
        Long expenseId = 1L;

        // Mock the repository to delete the expense with the specified ID
        Mockito.doNothing().when(expenseRepoMock).deleteById(expenseId);

        expensesService.deleteById(expenseId);

        // Verify that the delete method of the repository is called with the correct ID
        Mockito.verify(expenseRepoMock, Mockito.times(1)).deleteById(expenseId);
    }

    @Test
    public void testSaveExpense() {
        // Create a DTO for the new expense
        ExpenseDtoRq newExpenseDto = new ExpenseDtoRq();
        newExpenseDto.setTitle("Test Expense");
        newExpenseDto.setAmount(100);
        newExpenseDto.setCategory("Test Category");

        // Mock the repository to return the saved expense
        Expense savedExpense = new Expense();
        savedExpense.setId(1L);
        savedExpense.setTitle(newExpenseDto.getTitle());
        savedExpense.setAmount(newExpenseDto.getAmount());
        savedExpense.setCategory(newExpenseDto.getCategory());

        Mockito.when(expenseRepoMock.save(Mockito.any(Expense.class))).thenReturn(savedExpense);

        Expense createdExpense = expensesService.save(newExpenseDto);

        // Verify that the service returns the expected saved expense
        assertEquals(savedExpense, createdExpense);
    }

    @Test
    public void testUpdateExpense() {
        Long expenseId = 1L;

        // Create a DTO for the updated expense
        ExpenseDtoRq updatedExpenseDto = new ExpenseDtoRq();
        updatedExpenseDto.setId(expenseId);
        updatedExpenseDto.setTitle("Updated Expense");
        updatedExpenseDto.setAmount(200);
        updatedExpenseDto.setCategory("Updated Category");

        // Mock the repository to return the existing expense
        Expense existingExpense = new Expense();
        existingExpense.setId(expenseId);

        Mockito.when(expenseRepoMock.findById(expenseId)).thenReturn(Optional.of(existingExpense));
        Mockito.when(expenseRepoMock.save(Mockito.any(Expense.class))).thenReturn(existingExpense);

        Expense updatedExpense = expensesService.update(updatedExpenseDto);

        // Verify that the service returns the updated expense
        assertEquals(updatedExpenseDto.getId(), updatedExpense.getId());
        assertEquals(updatedExpenseDto.getTitle(), updatedExpense.getTitle());
        assertEquals(updatedExpenseDto.getAmount(), updatedExpense.getAmount());
        assertEquals(updatedExpenseDto.getCategory(), updatedExpense.getCategory());
    }

    @Test
    public void testUpdateExpenseNotFound() {
        Long expenseId = 1L;

        // Create a DTO for the updated expense
        ExpenseDtoRq updatedExpenseDto = new ExpenseDtoRq();
        updatedExpenseDto.setId(expenseId);

        // Mock the repository to return an empty optional, indicating the expense is not found
        Mockito.when(expenseRepoMock.findById(expenseId)).thenReturn(Optional.empty());

        // Verify that the service throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> expensesService.update(updatedExpenseDto));
    }
}

