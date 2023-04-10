package com.backstreetbrogrammer.ch03_buildingStream;

import java.util.regex.Pattern;

public class StreamFromRegEx {

    public static void main(final String[] args) {
        final String sentence = "Life is like riding a bicycle. To keep your balance, you must keep moving.";
        final Pattern pattern = Pattern.compile("\\s");
        final long count = pattern.splitAsStream(sentence).count();
        System.out.printf("Count = %d%n", count);
    }

}
