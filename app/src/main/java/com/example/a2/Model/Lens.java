package com.example.a2.Model;

/**
 * This class gives the specs for the lens- make, maximum
 * aperture, focal length
 */

public class Lens {

    private String make_lens;
    private double max_aperture;
    private int lens_focal;

    public Lens(String inp_lens, double input_aperture, int input_lens_focal) {
        if (inp_lens.length() > 0) {
            make_lens = inp_lens;
        }
        if (input_aperture >= 0) {
            max_aperture = input_aperture;
        }
        if (input_lens_focal >= 0) {
            lens_focal = input_lens_focal;
        }
    }

    public String getMake_lens() {
        return this.make_lens;
    }

    public void setMake_lens(String make_lens_input) {
        if (make_lens_input.length() <= 0) {
            throw new IllegalArgumentException("Input for the Model.lens is invalid!!");
        }
        this.make_lens = make_lens_input;
    }

    public double getMax_aperture() {
        return this.max_aperture;
    }

    public void setMax_aperture(double Max_aperture_input) {
        if (Max_aperture_input < 0) {
            throw new IllegalArgumentException("Input for Aperture is invalid!!");
        }
        this.max_aperture = Max_aperture_input;
    }

    public int getLens_focal() {
        return this.lens_focal;
    }

    public void setLens_focal(int Lens_focal_input) {
        if (Lens_focal_input < 0) {
            throw new IllegalArgumentException("Input for focal length is invalid");
        }
        this.lens_focal = Lens_focal_input;
    }
    public String toString() {
        return (this.getMake_lens() + ' ' + this.getLens_focal() + "mm F" + this.getMax_aperture());
    }

}
