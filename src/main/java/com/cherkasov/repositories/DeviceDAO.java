package com.cherkasov.repositories;

import com.cherkasov.entities.Device;

import java.util.List;

public interface DeviceDAO {
    void insert(Device device, String collection);

    void update(Device device, String collection);

    Device findByName(String device, String collection);

    List<Device> getAll(String collection);

    int deleteByName(String device, String collection);

    void insertAll(List<Device> devices, String collection);

    boolean dropCollectionIfExist(String collection);

}
