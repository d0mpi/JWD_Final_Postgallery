package by.bsu.d0mpi.UP_PostGallery.service;

import lombok.Getter;

public class MyPair<U, V> {

    @Getter
    private final U first;
    @Getter
    private final V second;

    public MyPair(U first, V second) {

        this.first = first;
        this.second = second;
    }
}
