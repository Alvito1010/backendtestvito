package com.testvito.backendtestvito.service;

import com.testvito.backendtestvito.dto.CreateGroupRequest;
import com.testvito.backendtestvito.dto.GroupResponse;
import com.testvito.backendtestvito.dto.ParticipantResponse;
import com.testvito.backendtestvito.entity.BillGroup;
import com.testvito.backendtestvito.entity.Participant;
import com.testvito.backendtestvito.repository.BillGroupRepository;
import com.testvito.backendtestvito.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    private final BillGroupRepository billGroupRepository;
    private final ParticipantRepository participantRepository;

    public GroupService(
            BillGroupRepository billGroupRepository,
            ParticipantRepository participantRepository) {

        this.billGroupRepository = billGroupRepository;
        this.participantRepository = participantRepository;
    }

    @Transactional
    public GroupResponse createGroup(CreateGroupRequest request) {

    BillGroup newGroup = new BillGroup(request.name());

    BillGroup savedGroup = billGroupRepository.save(newGroup);

    List<Participant> participants = request.participants()
            .stream()
            .map(name -> new Participant(name, savedGroup))
            .toList();

    participants = participantRepository.saveAll(participants);

    List<ParticipantResponse> participantResponses =
            participants.stream()
                    .map(participant ->
                            new ParticipantResponse(
                                    participant.getId(),
                                    participant.getName()
                            )
                    )
                    .toList();

    return new GroupResponse(
            savedGroup.getId(),
            savedGroup.getName(),
            participantResponses
    );
}
}