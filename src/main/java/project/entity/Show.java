package project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @NotNull
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    @NotNull
    private Auditorium auditorium;

    @NotEmpty
    private String type;

    @Column(name = "gold_seats")
    @NotNull
    private int goldSeats;

    @Column(name = "platinum_seats")
    @NotNull
    private int platinumSeats;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "price_id")
    @NotNull
    private Price price;
}
