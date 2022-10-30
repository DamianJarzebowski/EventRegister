package dj.eventregister.models.category;

import dj.eventregister.models.event.Event;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Include
    @OneToMany(mappedBy = "category")
    private Set<Event> events = new HashSet<>();

}
