package com.cherkasov.repositories;

import com.cherkasov.entities.TimeSeriesData;

import java.util.List;

public interface DataDAO {
    void insert(TimeSeriesData data, String collection);

    void insertJson(String json, String collection);

    void insertAll(List<TimeSeriesData> data, String collection);

    List<TimeSeriesData> findAllByDeviceId(String deviceId, String collection);

    TimeSeriesData findLastByDeviceId(String deviceId, String collection);

    int deleteByDeviceId(String deviceId, String collection);

    boolean dropCollectionIfExist(String collection);

}
