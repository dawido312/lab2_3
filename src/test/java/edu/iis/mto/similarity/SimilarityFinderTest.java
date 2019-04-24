package edu.iis.mto.similarity;

import org.junit.Assert;
import org.junit.Test;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.search.SequenceSearcher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SimilarityFinderTest {

    SimilarityFinder similarityFinder;
    double result;

    class SequenceSearcherDubler implements SequenceSearcher {

        private int counter = 0;

        @Override public SearchResult search(int key, int[] seq) {
            counter++;
            return SearchResult.builder().build();
        }

        int getCounter() {
            return counter;
        }
    }

    @Test public void calculateJackardSimilarityWithTwoEmptySequences() {
        similarityFinder = new SimilarityFinder();
        int[] seq = {};
        int[] seq2 = {};
        result = 1.0d;

        Assert.assertThat("should be 1.0d", result, is(equalTo(similarityFinder.calculateJackardSimilarity(seq, seq2))));
    }

    @Test public void calculateJackardSimilarityWithTwoSameSequences() {
        int[] seq = {1, 2, 3};
        int[] seq2 = {3, 2, 1};
        result = 1.0d;
        similarityFinder = new SimilarityFinder((key, seq1) -> {
            if (key == seq1[0] || key == seq1[1] || key == seq1[2] || key == seq1[3] || key == seq1[4])
                return SearchResult.builder().withFound(true).build();
            else
                return SearchResult.builder().withFound(false).build();
        });

        Assert.assertThat("should be 1.0", similarityFinder.calculateJackardSimilarity(seq, seq2), is(equalTo(result)));
    }

    @Test public void calculateJackardSimilarityWithTwoSimilarSequences() {
        int[] seq = {1, 2, 3};
        int[] seq2 = {3, 4, 5};
        result = 0.2d;

        similarityFinder = new SimilarityFinder((key, seq1) -> {
            if (key == seq1[0] || key == seq1[1] || key == seq1[2])
                return SearchResult.builder().withFound(true).build();
            else
                return SearchResult.builder().withFound(false).build();
        });

        Assert.assertThat("should be 0.2d", similarityFinder.calculateJackardSimilarity(seq, seq2), is(equalTo(result)));
    }

    @Test public void calculateJackardSimilarityWithTwoDifferentSequences() {
        int[] seq = {1, 2, 3};
        int[] seq2 = {4, 5, 6};
        result = 0.0d;

        similarityFinder = new SimilarityFinder((key, seq1) -> {
            if (key == seq1[0] || key == seq1[1] || key == seq1[2])
                return SearchResult.builder().withFound(true).build();
            else
                return SearchResult.builder().withFound(false).build();
        });

        Assert.assertThat("should be 0.0d", similarityFinder.calculateJackardSimilarity(seq, seq2), is(equalTo(result)));
    }

    @Test public void calculateJacardSimilarityWithCounterOfCallings() {
        int[] seq = {1, 2, 3};
        int[] seq2 = {4, 5, 6};

        SequenceSearcherDubler ssd = new SequenceSearcherDubler();
        similarityFinder = new SimilarityFinder(ssd);
        similarityFinder.calculateJackardSimilarity(seq, seq2);
        System.out.println(ssd.getCounter());

        Assert.assertThat("should be " + seq.length, ssd.getCounter(), is(equalTo(seq.length)));
    }

    @Test(expected = NullPointerException.class) public void calculateJackardSimilarityWithTwoNullSequences() {
        similarityFinder = new SimilarityFinder();
        int[] seq = null;
        int[] seq2 = null;
        similarityFinder.calculateJackardSimilarity(seq, seq2);
    }

}
