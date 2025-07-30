package com.example.taskmanager.service.label;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.taskmanager.model.Label;

@Service
public interface LabelService {
    public List<Label> getAll();
}
