package dj.eventregister.party;

import lombok.Data;

@Data
public class PartyDto {

    private Long id;
    private Long eventId;
    private Long participantId;
}
