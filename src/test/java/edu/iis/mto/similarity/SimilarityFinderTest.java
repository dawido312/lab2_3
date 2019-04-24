package edu.iis.mto.similarity;

import org.junit.Assert;
import org.junit.Test;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.search.SequenceSearcher;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class SimilarityFinderTest {

    SimilarityFinder similarityFinder;

    @Test
    public void calculateJackardSimilarityWithTwoEmptySequences() {
        similarityFinder = new SimilarityFinder();
        int[] seq = {};
        int[] seq2 = {};
        double result = 1.0d;

        Assert.assertThat("should be 1.0d", result, is(equalTo(similarityFinder.calculateJackardSimilarity(seq, seq2))));
    }
}
