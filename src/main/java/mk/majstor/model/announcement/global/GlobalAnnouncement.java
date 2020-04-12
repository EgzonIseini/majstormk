package mk.majstor.model.announcement.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.majstor.model.announcement.Announcement;
import mk.majstor.model.announcement.AnnouncementType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
@Table(name = "global_announcements")
@NoArgsConstructor
@Getter
@Setter
public abstract class GlobalAnnouncement implements Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String announcement;

    private Long entityId;

    private AnnouncementType announcementType;

    private Timestamp timestamp;

    public GlobalAnnouncement(String announcement, Long entityId, AnnouncementType announcementType) {
        this.announcement = announcement;
        this.entityId = entityId;
        this.announcementType = announcementType;
        this.timestamp = Timestamp.from(Instant.now());
    }

}
