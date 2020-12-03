package com.example.laba1;

public enum Unit {
    METERS(UnitCategories.DISTANCE, 1.0),
    MILES(UnitCategories.DISTANCE, 0.000621371),
    FOOT(UnitCategories.DISTANCE, 3.28084),
    BYN(UnitCategories.CURRENCY, 1.0),
    USD(UnitCategories.CURRENCY, 0.384),
    EUR(UnitCategories.CURRENCY, 0.3294),
    KG(UnitCategories.WEIGHT, 1.0),
    OUNCE(UnitCategories.WEIGHT, 35.274),
    GRAMM(UnitCategories.WEIGHT, 1000.0);
    private UnitCategories category;
    private double coefficient;
    Unit(UnitCategories category, double coefficient){
        this.category = category;
        this.coefficient = coefficient;
    }

    public UnitCategories getCategory(){
        return category;
    }

    public double getCoefficient(){
        return coefficient;
    }

}
