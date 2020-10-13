package seedu.address.model.person.seller;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.id.Id;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Seller#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Seller#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniqueSellerList. However, the removal of a person uses Seller#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Seller#isSamePerson(Person)
 */
public class UniqueSellerList implements Iterable<Seller> {

    protected final ObservableList<Seller> internalList = FXCollections.observableArrayList();
    protected final ObservableList<Seller> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Seller toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a seller to the list.
     * The person must not already exist in the list.
     */
    public void add(Seller toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        toAdd.setId(getLatestId());
        internalList.add(toAdd);
    }

    public Id getLatestId() {
        if (internalList.size() == 0) {
            return new Id("S", 1);
        }
        return this.internalList.get(internalList.size() - 1).getId().increment();
    }
    /**
     * Replaces the contents of this list with {@code sellers}.
     * {@code sellers} must not contain duplicate bidders.
     */
    public void setSellers(List<Seller> sellers) {
        requireAllNonNull(sellers);
        if (!sellersAreUnique(sellers)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(sellers);
    }

    public void setSellers(UniqueSellerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedSeller}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedSeller} must not be the same as another existing person in the list.
     */
    public void setSeller(Seller target, Seller editedSeller) {
        requireAllNonNull(target, editedSeller);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedSeller) && contains(editedSeller)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedSeller);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Seller toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Seller> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Seller> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueSellerList // instanceof handles nulls
                && internalList.equals(((UniqueSellerList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean sellersAreUnique(List<Seller> sellers) {
        for (int i = 0; i < sellers.size() - 1; i++) {
            for (int j = i + 1; j < sellers.size(); j++) {
                if (sellers.get(i).isSamePerson(sellers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}