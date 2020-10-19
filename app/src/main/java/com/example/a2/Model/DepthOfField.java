package com.example.a2.Model;

import android.widget.TextView;

/**
 * This class used to calculate the hyper focal length
 * near focal, far focal and DOF
 */


public class DepthOfField {

    private Lens lens_type;
    private double dis_sub;
    private double aperture;
    private double coc = 0.029;

    public DepthOfField(Lens inp_lens, double input_distance_subject, double input_aperture) {

        if (inp_lens == null) {
            throw new IllegalArgumentException(" Lens input is invalid !!");

        } else {
            lens_type = inp_lens;

        }
        if (input_distance_subject < 0) {
            throw new IllegalArgumentException(" Subject distance is invalid (negative) !!");
        } else {
            dis_sub = input_distance_subject * 1000;
        }
        if (input_aperture < 0) {
            throw new IllegalArgumentException(" Aperture is invalid (negative) !!");
        } else if (input_aperture < lens_type.getMax_aperture()) {
            throw new IllegalArgumentException(" Aperture is invalid (limit exceed) !!");
        } else {
            aperture = input_aperture;
        }
    }


    public double getAperture() {
        return aperture;
    }

    public double getCoc() {
        return coc;
    }

    public Lens getLens_type() {
        return lens_type;
    }

    public double getDis_sub() {
        return this.dis_sub / 1000.0;
    }

    public double Hyperfocal() {
        double result = (this.lens_type.getLens_focal() * this.lens_type.getLens_focal()) / (this.aperture * this.coc);
        return result;
    }

    public double Nearfocal() {
        double result = (Hyperfocal() * this.dis_sub) / (this.Hyperfocal() + (this.dis_sub - this.lens_type.getLens_focal()));
        return result;
    }

    public double Farfocal() {
        double result;
        double inf = Double.POSITIVE_INFINITY;
        if(this.dis_sub> Hyperfocal()){
            result = inf;
        }
        else {
            result = (Hyperfocal() * this.dis_sub) / (Hyperfocal() - (this.dis_sub - this.lens_type.getLens_focal()));
        }
        return result;
    }

    public double depthoffield() {
        double result = this.Farfocal() - this.Nearfocal();
        return result;
    }
}
