package com.cherkasov.repositories;

import com.cherkasov.entities.Device;

import java.util.List;

public interface DeviceDAO {
    void insert(Device device);

    void update(Device device);

    Device findByName(String device);

    int deleteByName(String device);

    void insertAll(List<Device> devices);

    boolean dropCollectionIfExist();

}
