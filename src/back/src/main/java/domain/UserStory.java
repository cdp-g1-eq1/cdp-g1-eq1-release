package domain;

import java.util.Comparator;

public class UserStory {
    final public Integer id;

    final public Integer projectId;
    final public String name;
    final public String priority;
    final public Integer difficulty;

    final public static Comparator<UserStory> comparator;

    static {
        comparator = Comparator
                .comparing(
                        (UserStory us) -> us.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.name,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.priority,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.difficulty,
                        Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    public UserStory() {
        this(null, null, null, null, null);
    }

    public UserStory(Integer id, Integer projectId, String name, String priority, Integer difficulty) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.priority = priority;
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof UserStory))
            return false;

        return comparator.compare(this, (UserStory) obj) == 0;
    }
}
