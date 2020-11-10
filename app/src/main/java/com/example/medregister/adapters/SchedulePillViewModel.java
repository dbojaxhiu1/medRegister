package com.example.medregister.adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.medregister.data.repository.SchedulePillRepository;
import com.example.medregister.models.SchedulePill;

import java.util.List;

public class SchedulePillViewModel extends AndroidViewModel {
    private SchedulePillRepository schedulePillRepository;
    private LiveData<List<SchedulePill>> allScheduledPills;

    public SchedulePillViewModel(@NonNull Application application) {
        super(application);
        schedulePillRepository = new SchedulePillRepository(application);
        allScheduledPills = schedulePillRepository.getAllScheduledPills();
    }

    public void insert(SchedulePill schedulePill) {
        schedulePillRepository.insert(schedulePill);
    }

    public void update(SchedulePill schedulePill) {
        schedulePillRepository.update(schedulePill);
    }

    public void delete(SchedulePill schedulePill) {
        schedulePillRepository.delete(schedulePill);
    }

    public void deleteAllScheduledPills() {
        schedulePillRepository.deleteAllScheduledPills();
    }

    public LiveData<List<SchedulePill>> getAllScheduledPills() {
        return allScheduledPills;
    }
}
