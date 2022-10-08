package dj.eventregister.event;

import dj.eventregister.category.Category;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

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

