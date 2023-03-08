package peaksoft.service;

import peaksoft.exceptions.MyException;
import peaksoft.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment save(Long id,Appointment appointment) throws MyException;
    List<Appointment> getAll(Long id);
    void deleteById(Long hId,Long id);
    Appointment getById(Long id);
    void update (Long id, Appointment newAppointment) throws MyException;
}