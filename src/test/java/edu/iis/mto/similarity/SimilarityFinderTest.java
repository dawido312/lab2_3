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
    double result;

    @Test
    public void calculateJackardSimilarityWithTwoEmptySequences() {
        similarityFinder = new SimilarityFinder();
        int[] seq = {};
        int[] seq2 = {};
        result = 1.0d;

        Assert.assertThat("should be 1.0d", result, is(equalTo(similarityFinder.calculateJackardSimilarity(seq, seq2))));
    }

    @Test
    public void calculateJackardSimilarityWithTwoSameSequences()
    {
        similarityFinder = new SimilarityFinder();
        int[] seq = {1, 2, 3};
        int[] seq2 = {3, 2,1};
        result = 1.0d;
        similarityFinder = new SimilarityFinder((key, seq1) -> {
            if (key == seq1[0] || key == seq1[1] || key == seq1[2] || key == seq1[3] || key == seq1[4])
                return SearchResult.builder().withFound(true).build();
            else return SearchResult.builder().withFound(false).build();
        });

        Assert.assertThat("should be 1.0", similarityFinder.calculateJackardSimilarity(seq, seq2), is(equalTo(result)));
    }


}
