package dj.eventregister.party;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;




}
