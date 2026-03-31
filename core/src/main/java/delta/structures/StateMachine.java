package delta.structures;

import java.util.*;

public class StateMachine {
    private Map<String, Map<String, Runnable>> states;
    private String currentState;

    public StateMachine() {
        this.states = new HashMap<>();
    }

    public static void Main(String[] args) {
        StateMachine sm = new StateMachine();

        sm.add("normal", "move", () -> System.out.println("normal move"));
        sm.add("normal", "shoot", () -> System.out.println("normal shoot"));
        sm.add("air", "move", () -> System.out.println("air move"));
        sm.add("air", "shoot", () -> System.out.println("air shoot"));
        sm.setState("normal");

        sm.run("shoot");
        sm.setState("air");
        sm.run("shoot");
    }

    public void add(String state, String name, Runnable method) {
        this.states.putIfAbsent(state, new HashMap<String, Runnable>());

        if (this.states.get(state).containsKey(name)) {
            this.states.get(state).replace(name, method);
        } else {
            this.states.get(state).put(name, method);
        }
    }

    public void setState(String state) {
        this.currentState = state;
    }

    public void run(String method) {
        this.states.get(this.currentState).get(method).run();
    }
}
