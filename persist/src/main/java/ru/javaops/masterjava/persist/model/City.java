package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class City extends BaseEntity {
    @Column("name")
    private @NonNull String name;
    private @NonNull String ref;

    public City(Integer id, String name, String ref) {
        this(name, ref);
        this.id=id;
    }
}
