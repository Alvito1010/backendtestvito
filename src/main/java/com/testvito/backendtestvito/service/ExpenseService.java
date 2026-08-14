package com.testvito.backendtestvito.service;

import com.testvito.backendtestvito.dto.AddExpenseRequest;
import com.testvito.backendtestvito.dto.ExpenseResponse;
import com.testvito.backendtestvito.entity.BillGroup;
import com.testvito.backendtestvito.entity.Expense;
import com.testvito.backendtestvito.entity.Participant;
import com.testvito.backendtestvito.repository.BillGroupRepository;
import com.testvito.backendtestvito.repository.ExpenseRepository;
import com.testvito.backendtestvito.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testvito.backendtestvito.exception.GroupNotFoundException;
import com.testvito.backendtestvito.exception.ParticipantNotFoundException;
import com.testvito.backendtestvito.exception.InvalidExpenseException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final BillGroupRepository billGroupRepository;
    private final ParticipantRepository participantRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseService(
            BillGroupRepository billGroupRepository,
            ParticipantRepository participantRepository,
            ExpenseRepository expenseRepository) {

        this.billGroupRepository = billGroupRepository;
        this.participantRepository = participantRepository;
        this.expenseRepository = expenseRepository;
    }

    @Transactional
    public ExpenseResponse addExpense(
            Long groupId,
            AddExpenseRequest request) {

        BillGroup group = billGroupRepository.findById(groupId)
                .orElseThrow(() ->
                        new GroupNotFoundException(groupId));

        Participant paidBy = participantRepository
                .findById(request.paidBy())
                .orElseThrow(() ->
                        new ParticipantNotFoundException(request.paidBy()));

        List<Participant> participants =
                participantRepository.findAllById(request.splitAmong());

        Set<Long> requestedParticipantIds =
                Set.copyOf(request.splitAmong());

        Set<Long> foundParticipantIds =
                participants.stream()
                        .map(Participant::getId)
                        .collect(Collectors.toSet());

        if (!foundParticipantIds.equals(requestedParticipantIds)) {

            Set<Long> missingParticipantIds = requestedParticipantIds.stream()
                    .filter(id -> !foundParticipantIds.contains(id))
                    .collect(Collectors.toSet());

            throw new ParticipantNotFoundException(
                    "Participants with ids " + missingParticipantIds + " not found");
        }

        if (!paidBy.getGroup().getId().equals(groupId)) {
            throw new InvalidExpenseException(
                    "Payer does not belong to this group");
        }

        if (participants.stream()
                .anyMatch(p -> !p.getGroup().getId().equals(groupId))) {

            throw new InvalidExpenseException(
                    "One or more participants do not belong to this group");
        }

        Expense expense = new Expense();

        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setGroup(group);
        expense.setPaidBy(paidBy);
        expense.setSplitAmong(Set.copyOf(participants));

        Expense savedExpense = expenseRepository.save(expense);

        return new ExpenseResponse(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getAmount(),
                savedExpense.getPaidBy().getId(),
                savedExpense.getSplitAmong()
                        .stream()
                        .map(Participant::getId)
                        .toList()
        );
    }
}