package domain;

import java.util.Comparator;

public class Sprint {
    public final Integer projectId;
    public final Integer id;
    public final String name;
    public final String state;

    public static final Comparator<Sprint> COMPARATOR;

    static {
        COMPARATOR = Comparator
                .comparing(
                        (Sprint sprint) -> sprint.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Sprint sprint) -> sprint.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Sprint sprint) -> sprint.name,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Sprint sprint) -> sprint.state,
                        Comparator.nullsFirst(Comparator.naturalOrder()));

    }

    // Required by jackson
    public Sprint() {
        this(null, null, null, null);
    }

    public Sprint(Integer projectId, String name) {
        this(projectId, name, "pending", null);
    }

    public Sprint(Integer projectId, String name, String state) {
        this(projectId, name, state, null);
    }

    public Sprint(Integer projectId, String name, Integer id) {
        this(projectId, name, "pending", id);
    }

    public Sprint(Integer projectId, String name, String state, Integer id) {
        this.projectId = projectId;
        this.name = name;
        this.state = state;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Sprint))
            return false;

        return COMPARATOR.compare(this, (Sprint) obj) == 0;
    }

    @Override
    public String toString() {
        return "Sprint(projectId=" + projectId
                + ", id=" + id
                + ", name=" + name
                + ", state=" + state
                + ")";
    }
}
