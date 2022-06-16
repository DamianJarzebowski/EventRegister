package dj.eventregister.party;

import org.springframework.stereotype.Service;

@Service
public class PartyMapper {

    private PartyMapper() {}

    static PartyDto toDto (Party party) {
        var dto = new PartyDto();
        var event = party.getEvent();
        var participant = party.getParticipant();

        dto.setId(party.getId());
        dto.setEventId(event.getId());
        dto.setParticipantId(participant.getId());

        return dto;
    }

}
