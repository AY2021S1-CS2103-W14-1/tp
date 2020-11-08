package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all meetings in the calendar book to the user.
 */
public class ListMeetingCommand extends Command {

    public static final String COMMAND_WORD = "list-m";

    public static final String MESSAGE_SUCCESS = "Displaying full meeting list.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
