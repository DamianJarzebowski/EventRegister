package dj.eventregister.models.event;

import dj.eventregister.models.category.Category;
import dj.eventregister.models.participant.Participant;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private boolean majority;
    private LocalDateTime dateTime;
    private EventStateMachine stateEvent;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public enum EventStateMachine {

        NOT_ENOUGH_PARTICIPANT("Liczba zapisanych uczestników, nie spełnia organizacyjnego minimum."),
        MINIMUM_PARTICIPANT_ACHIEVED("Minimalna lcizba uczestników wymagana do organizacji wydarzenia została osiągnięta."),
        REGISTRATION_CLOSE("Wydarzenie osiągneło limit rejestracji.");

        final String description;

        EventStateMachine(String description) {
            this.description = description;
        }
    }

}

