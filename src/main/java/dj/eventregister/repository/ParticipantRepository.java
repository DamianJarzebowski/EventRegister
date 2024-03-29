package dj.eventregister.repository;

import dj.eventregister.models.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByEmail(String email);

    List<Participant> findAllByLastNameContainingIgnoreCase(String lastName);

}
