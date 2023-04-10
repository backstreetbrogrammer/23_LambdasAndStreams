package com.backstreetbrogrammer.ch03_buildingStream;

public class StreamFromString {

    public static void main(final String[] args) {
        final String sentence = "Life is like riding a bicycle. To keep your balance, you must keep moving.";

        sentence.chars()
                .mapToObj(Character::toString)
                .filter(letter -> !letter.equals(" "))
                .distinct()
                .sorted()
                .forEach(System.out::print);
    }

}
