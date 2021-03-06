package seedu.address.logic.parser.meetingparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_ORDER;

import java.util.stream.Stream;

import seedu.address.logic.commands.meetingcommands.SortMeetingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingDateComparator;

/**
 * Parses input arguments and creates a new SortMeetingCommand object
 */
public class SortMeetingCommandParser implements Parser<SortMeetingCommand> {

    /**
     * Parses the given string of arguments in the context of the SortMeetingCommand
     * and returns a SortMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEETING_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEETING_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortMeetingCommand.MESSAGE_USAGE));
        }

        Boolean isAscending = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_MEETING_ORDER).orElse(null));
        MeetingDateComparator comparator = new MeetingDateComparator();
        return new SortMeetingCommand(comparator, isAscending);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
