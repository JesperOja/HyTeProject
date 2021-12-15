package com.example.edocontrol;

/**
 * Class to define an appointment.
 *
 * @author      Jenni Tynkkynen
 */
public class Appointment {
    public boolean appointment;

    public Appointment(boolean appointment) {
        this.appointment = appointment;
    }

    public int isAppointment() {
        if(appointment)
            return 1;
        else
            return 0;

    }

    public void setAppointment(boolean appointment) {
        this.appointment = appointment;
    }
}
