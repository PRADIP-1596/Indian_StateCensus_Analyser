package com.bridgelabz.Analyzercode.models;

import com.opencsv.bean.CsvBindByName;

public class StateCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public String tin;


    @CsvBindByName(column = "StateCode", required = true)
    public String StateCode;

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                ", SrNo= '" + srNo +'\'' +
                ", Population='" + stateName + '\'' +
                ", TIN= '" + tin +'\'' +
                ", DensityPerSqKm='" + StateCode + '\'' +
                '}';
    }
}