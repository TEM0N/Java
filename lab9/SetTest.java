package lab9;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetTest {
    Set<Integer> firstTestSet = Set.create();
    Set<Integer> secondTestSet = Set.create();

    @Test
    void copy() {
        assertEquals(firstTestSet, Set.copy(firstTestSet));
    }

    @Test
    void size() {
        assertEquals(0, firstTestSet.size());
        firstTestSet.add(2);
        assertEquals(1, firstTestSet.size());
        firstTestSet.add(2);
        assertEquals(1, firstTestSet.size());
        firstTestSet.clear();
    }

    @Test
    void testEquals() {
        assertEquals(firstTestSet, secondTestSet);
        firstTestSet.add(1);
        secondTestSet.add(1);
        assertEquals(firstTestSet, secondTestSet);
        secondTestSet.add(1);
        assertEquals(firstTestSet, secondTestSet);
        firstTestSet.clear();
        secondTestSet.clear();
    }

    @Test
    void add() {
        assertTrue(firstTestSet.isEmpty());
        firstTestSet.add(1);
        assertFalse(firstTestSet.isEmpty());
        firstTestSet.clear();
    }

    @Test
    void remove() {
        firstTestSet.add(1);
        assertTrue(firstTestSet.remove(1));
    }

    @Test
    void addAll() {
        firstTestSet.add(1);
        firstTestSet.add(2);
        firstTestSet.add(3);
        assertTrue(secondTestSet.addAll(firstTestSet));
        // nothing to add
        assertFalse(secondTestSet.addAll(firstTestSet));
        firstTestSet.clear();
        secondTestSet.clear();
    }

    @Test
    void union() {
        firstTestSet.add(1);
        firstTestSet.add(2);
        firstTestSet.add(3);

        secondTestSet.add(2);
        secondTestSet.add(3);
        secondTestSet.add(4);

        Set<Integer> uAnswer = Set.create();
        uAnswer.add(1);
        uAnswer.add(2);
        uAnswer.add(3);
        uAnswer.add(4);
        Set<Integer> uSet = firstTestSet.union(secondTestSet);
        assertEquals(uAnswer, uSet);
    }

    @Test
    void intersection() {
        firstTestSet.add(1);
        firstTestSet.add(2);
        firstTestSet.add(3);

        secondTestSet.add(2);
        secondTestSet.add(3);
        secondTestSet.add(4);

        Set<Integer> uAnswer = Set.create();
        uAnswer.add(2);
        uAnswer.add(3);
        Set<Integer> uSet = firstTestSet.intersection(secondTestSet);
        assertEquals(uAnswer, uSet);
    }

    @Test
    void difference() {
        firstTestSet.add(1);
        firstTestSet.add(2);
        firstTestSet.add(3);

        secondTestSet.add(2);
        secondTestSet.add(3);
        secondTestSet.add(4);

        Set<Integer> uAnswer = Set.create();
        uAnswer.add(1);
        Set<Integer> uSet = firstTestSet.difference(secondTestSet);
        assertEquals(uAnswer, uSet);
    }
}