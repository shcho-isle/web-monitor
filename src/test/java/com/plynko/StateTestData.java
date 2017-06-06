package com.plynko;

import com.plynko.matcher.ModelMatcher;
import com.plynko.model.State;
import com.plynko.model.Status;
import com.plynko.repository.StateRepository;

import java.util.Objects;

import static com.plynko.ConfigTestData.*;

public class StateTestData {

    public static final int STATE_ID1 = 1;
    public static final int STATE_ID2 = STATE_ID1 + 1;

    public static final State TEST_STATE1 = new State(STATE_ID1, CONFIG_ID1, TEST_CONFIG1.getUrl(), Status.WARNING, "info1");
    public static final State TEST_STATE2 = new State(STATE_ID2, CONFIG_ID2, TEST_CONFIG2.getUrl(), Status.CRITICAL, "info2");

    public static final ModelMatcher<State> MATCHER = ModelMatcher.of(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getConfigId(), actual.getConfigId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getStatus(), actual.getStatus())
                    )
    );

    public static void populateWithTestData(StateRepository repository) {
        repository.save(new State(STATE_ID1, CONFIG_ID1, TEST_CONFIG1.getUrl(), Status.WARNING, "info1"));
        repository.save(new State(STATE_ID2, CONFIG_ID2, TEST_CONFIG2.getUrl(), Status.CRITICAL, "info2"));
    }

    public static State getCreated() {
        return new State(null, 100, "new state", Status.UNKNOWN, "new info");
    }

    public static State getUpdated() {
        return new State(STATE_ID1, CONFIG_ID1, "updated state", Status.WARNING, "updated info");
    }
}
