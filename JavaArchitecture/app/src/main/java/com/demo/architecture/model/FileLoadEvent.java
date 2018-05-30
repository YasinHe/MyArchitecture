package com.demo.architecture.model;

/**
 * Created by HeYingXin on 2018/3/22.
 */
public class FileLoadEvent {
    long total;
    long bytesLoaded;

    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadEvent(long total, long bytesLoaded) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }
}
