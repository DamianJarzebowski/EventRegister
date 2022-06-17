package dj.eventregister.event;

import dj.eventregister.category.Category;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private int currentParticipants;
    private boolean majority;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Category getCategory() {
        return category;
    }
}
