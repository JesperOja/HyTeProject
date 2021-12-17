package com.example.edocontrol;

/**
 * Class to define an appointment.
 *
 * @author Jenni Tynkkynen
 */
public class Appointment {
    public boolean appointment;

    /**
     * Constructor for the class
     *
     * @param appointment boolean, defines if there's an appointment
     */
    public Appointment(boolean appointment) {
        this.appointment = appointment;
    }

    /**
     * Getter for appointment status
     *
     * @return returns an integer as an appointment status
     */
    public int isAppointment() {
        if (appointment)
            return 1;
        else
            return 0;
    }
}
