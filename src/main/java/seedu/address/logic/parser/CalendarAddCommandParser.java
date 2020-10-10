package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_BIDDER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.CalendarBidderId;
import seedu.address.model.calendar.CalendarPropertyId;
import seedu.address.model.calendar.CalendarTime;
import seedu.address.model.calendar.CalendarVenue;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
* Parses input arguments and creates a new AddCommand object
*/
public class CalendarAddCommandParser implements Parser<AddCommand> {

    /**
    * Parses the given {@code String} of arguments in the context of the AddCommand
    * and returns an AddCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CALENDAR_BIDDER_ID, PREFIX_CALENDAR_PROPERTY_ID,
                        PREFIX_CALENDAR_VENUE, PREFIX_CALENDAR_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_CALENDAR_BIDDER_ID, PREFIX_CALENDAR_PROPERTY_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        CalendarVenue venue = ParserUtil.parseCalendarVenue(argMultimap.getValue(PREFIX_CALENDAR_VENUE).get());
        CalendarTime time = ParserUtil.parseCalendarTime(argMultimap.getValue(PREFIX_CALENDAR_TIME).get());
        CalendarPropertyId propertyId =
                ParserUtil.parseCalendarPropertyId(argMultimap.getValue(PREFIX_CALENDAR_PROPERTY_ID).get());
        CalendarBidderId bidderId =
                ParserUtil.parseCalendarBidderId(argMultimap.getValue(PREFIX_CALENDAR_BIDDER_ID).get());


        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Person person = new Person(name, phone, tagList);

        return new AddCommand(person);
    }

    /**
    * Returns true if none of the prefixes contains empty {@code Optional} values in the given
    * {@code ArgumentMultimap}.
    */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}