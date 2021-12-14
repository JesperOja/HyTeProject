package com.example.edocontrol;

public class Appointment {
    public boolean appointment;

    public Appointment(boolean appointment) {
        this.appointment = appointment;
    }

    public boolean isAppointment() {
        return appointment;
    }

    public void setAppointment(boolean appointment) {
        this.appointment = appointment;
    }
}
