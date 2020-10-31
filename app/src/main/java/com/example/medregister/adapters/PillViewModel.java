package com.example.medregister.adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.medregister.data.PillRepository;
import com.example.medregister.models.Pill;

import java.util.List;

public class PillViewModel extends AndroidViewModel {
    private PillRepository pillRepository;
    private LiveData<List<Pill>> allPills;

    public PillViewModel(@NonNull Application application) {
        super(application);
        pillRepository = new PillRepository(application);
        allPills = pillRepository.getAllPills();
    }

    public void insert(Pill pill) {
        pillRepository.insert(pill);
    }

    public void update(Pill pill) {
        pillRepository.update(pill);
    }

    public void delete(Pill pill) {
        pillRepository.delete(pill);
    }

    public void deleteAllPills() {
        pillRepository.deleteAllPills();
    }

    public LiveData<List<Pill>> getAllPills() {
        return allPills;
    }
}
