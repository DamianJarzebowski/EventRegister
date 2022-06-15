package dj.eventregister.party;

import org.springframework.stereotype.Service;

@Service
public class PartyMapper {

    static PartyDto toDto (Party party) {
        var dto = new PartyDto();

        dto.setId(party.getId());
        dto.setEvent(party.getEvent());
        dto.setParticipant(party.getParticipant());

        return dto;
    }

    static Party toEntity (PartyDto partyDto) {
        var entity = new Party();

        entity.setId(partyDto.getId());
        entity.setEvent(partyDto.getEvent());
        entity.setParticipant(partyDto.getParticipant());

        return entity;
    }
}
