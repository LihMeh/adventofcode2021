package day2;

import com.google.common.collect.ImmutableMap;
import day02.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class Day2 {

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

        final var result = state.getHorizontalPos() * state.getVerticalPos();

        return String.valueOf(result);
    }

    public static String task1(final String input) {
        return taskImpl(input, ImmutableMap.of(
                Direction.forward, (state, arg) -> new State(state.getHorizontalPos() + arg, state.getVerticalPos(), 0),
                Direction.up, (state, arg) -> new State(state.getHorizontalPos(), state.getVerticalPos() - arg, 0),
                Direction.down, (state, arg) -> new State(state.getHorizontalPos(), state.getVerticalPos() + arg, 0)
        ));
    }

    public static String task2(final String input) {
        return taskImpl(input, ImmutableMap.of(
                Direction.down, (state, arg) -> new State(state.getHorizontalPos(), state.getVerticalPos(), state.getAim() + arg),
                Direction.up, (state, arg) -> new State(state.getHorizontalPos(), state.getVerticalPos(), state.getAim() - arg),
                Direction.forward, (state, arg) -> new State(
                        state.getHorizontalPos() + arg,
                        state.getVerticalPos() + state.getAim() * arg,
                        state.getAim())
        ));
    }

}
