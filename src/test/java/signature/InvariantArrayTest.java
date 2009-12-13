package signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import signature.InvariantArray;

public class InvariantArrayTest {
    
    public List<InvariantArray> makeUnsortedList() {
        int[] a = { 1, 1, 2, 3 };
        int[] b = { 2, 2, 2, 3 };
        int[] c = { 1, 2, 2, 3 };
        int[] d = { 1, 2, 2, 2 };
        int[] e = { 1, 1, 2, 3 };
        List<InvariantArray> list = new ArrayList<InvariantArray>();
        list.add(new InvariantArray(a, 0));
        list.add(new InvariantArray(b, 1));
        list.add(new InvariantArray(c, 2));
        list.add(new InvariantArray(d, 3));
        list.add(new InvariantArray(e, 4));
        return list;
    }
    
    @Test
    public void testSort() {
        List<InvariantArray> list = makeUnsortedList();
        Collections.sort(list);
        Assert.assertEquals(list.get(0).originalIndex, 0);
        Assert.assertEquals(list.get(1).originalIndex, 4);
        Assert.assertEquals(list.get(2).originalIndex, 3);
        Assert.assertEquals(list.get(3).originalIndex, 2);
        Assert.assertEquals(list.get(4).originalIndex, 1);
    }
    
    @Test
    public void testRank() {
        List<InvariantArray> list = makeUnsortedList();
        Collections.sort(list);
        int rank = 1;
        int[] ranks = new int[list.size()];
        ranks[0] = 1;
        for (int i = 1; i < list.size(); i++) {
            InvariantArray a = list.get(i - 1);
            InvariantArray b = list.get(i);
            if (!a.equals(b)) {
                rank++;
            }
            ranks[i] = rank;
        }
        int[] expecteds = { 1, 1, 2, 3, 4 };
        Assert.assertArrayEquals(expecteds, ranks);
    }

}
