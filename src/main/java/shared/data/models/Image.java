package shared.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name="images")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Image {

    @Id
    private String url;

    @NonNull
    private String name;

}
