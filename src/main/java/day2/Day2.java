package day2;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class Day2 {

    public static class State {
        private final int x;
        private final int y;
        private final int aim;

        public State(int x, int y) {
            this.x = x;
            this.y = y;
            this.aim = 0;
        }

        public State(int x, int y, int aim) {
            this.x = x;
            this.y = y;
            this.aim = aim;
        }

        public final int getX() {
            return x;
        }

        public final int getY() {
            return y;
        }

        public final int getAim() {
            return aim;
        }
    }

    public enum Direction {
        forward,
        down,
        up
    }

    public static class Command {
        private final Direction direction;
        private final int arg;

        public Command(Direction direction, int arg) {
            this.direction = direction;
            this.arg = arg;
        }

        public final Direction getDirection() {
            return direction;
        }

        public final int getArg() {
            return arg;
        }
    }

    public static List<Command> parseInput(final String input) {
        checkNotNull(input, "input required");

        final var inputRows = input.split("\n");
        final var result = new ArrayList<Command>(inputRows.length);

        for (final String inputRow : inputRows) {
            final var splitRow = inputRow.split(" ");
            checkState(splitRow.length == 2);
            final var direction = Direction.valueOf(splitRow[0]);
            final var arg = Integer.parseUnsignedInt(splitRow[1]);
            result.add(new Command(direction, arg));
        }

        return List.copyOf(result);
    }

    public static String taskImpl(final String input, final ImmutableMap<Direction, BiFunction<State, Integer, State>> processors) {
        final var commands = parseInput(input);

        var state = new State(0, 0, 0);
        for (final Command command : commands) {
            final var processor = processors.get(command.getDirection());
            checkNotNull(processor, "processor required");
            state = processor.apply(state, command.getArg());
        }

        final var result = state.getX() * state.getY();

        return String.valueOf(result);
    }

    public static String task1(final String input) {
        return taskImpl(input, ImmutableMap.of(
                Direction.forward, (state, arg) -> new State(state.getX() + arg, state.getY()),
                Direction.up, (state, arg) -> new State(state.getX(), state.getY() - arg),
                Direction.down, (state, arg) -> new State(state.getX(), state.getY() + arg)
        ));
    }

    public static String task2(final String input) {
        return taskImpl(input, ImmutableMap.of(
                Direction.down, (state, arg) -> new State(state.getX(), state.getY(), state.getAim() + arg),
                Direction.up, (state, arg) -> new State(state.getX(), state.getY(), state.getAim() - arg),
                Direction.forward, (state, arg) -> new State(
                        state.getX() + arg,
                        state.getY() + state.getAim() * arg,
                        state.getAim())
        ));
    }

}
