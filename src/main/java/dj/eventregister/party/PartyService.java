package dj.eventregister.party;

import dj.eventregister.event.Event;
import dj.eventregister.event.EventRepository;
import dj.eventregister.participant.Participant;
import dj.eventregister.participant.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    PartyDto registerTheParticipant(PartyDto partyDto) {

        Optional<Event> event = eventRepository.findById(partyDto.getEventId());
        Optional<Participant> participant = participantRepository.findById(partyDto.getParticipantId());

        var party = new Party();

        Long participantId = partyDto.getParticipantId();
        Long eventId = partyDto.getEventId();

        party.setEvent(event.orElseThrow(() ->
                new InvalidPartyException("Brak eventu z id: " + eventId)));
        party.setParticipant(participant.orElseThrow(() ->
                new InvalidPartyException("Brak uczestnika z id " + participantId)));

        int actualCurrentParticipants = getEventFromParty(partyDto).getCurrentParticipants();
        getEventFromParty(partyDto).setCurrentParticipants(actualCurrentParticipants + 1);

        return PartyMapper.toDto(partyRepository.save(party));
    }

    Event getEventFromParty(PartyDto partyDto) {
        Long eventDto = partyDto.getEventId();
        return eventRepository.getReferenceById(eventDto);
    }
}
