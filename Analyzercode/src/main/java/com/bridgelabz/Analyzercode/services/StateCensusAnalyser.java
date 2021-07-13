package com.bridgelabz.Analyzercode.services;

import com.bridgelabz.statecensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.statecensusanalyser.models.CensusDAO;
import com.bridgelabz.statecensusanalyser.models.IndiaCensusCSV;
import com.bridgelabz.statecensusanalyser.models.StateCSV;
import com.bridgelabz.statecensusanalyser.models.UsCensusData;
import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSVBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser {

    List<CensusDAO> censusDaoList;

    public StateCensusAnalyser() {
        this.censusDaoList = new ArrayList<>();
    }

    /**
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                this.censusDaoList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.censusDaoList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /**
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public int loadStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCSV.class);
            while (csvFileIterator.hasNext()) {
                this.censusDaoList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.censusDaoList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /**
     * @return
     * @throws CensusAnalyserException
     */
    public int loadUsData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<UsCensusData> csvFileIterator = csvBuilder.getCSVFileIterator(reader, UsCensusData.class);
            while (csvFileIterator.hasNext()) {
                this.censusDaoList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.censusDaoList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        censusDaoList.sort(((Comparator <CensusDAO>)
                (census1, census2) -> census2.state.compareTo(census1.state)).reversed());
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        return sortedStateCensusJson;
    }


    public String getStateCodeWiseSortedStateCode() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        censusDaoList.sort(((Comparator <CensusDAO>)
                (census1, census2) -> census2.StateCode.compareTo(census1.StateCode)).reversed());
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        return sortedStateCensusJson;
    }


    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        Sort.sort(censusComparator, censusDaoList);
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        return sortedStateCensusJson;
    }


    public String getPopulatedDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        Sort.sort(censusComparator, censusDaoList);
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        JsonWrite.writeJson("./src/test/resources/IndiaStateCensusDataJson.json",censusDaoList);
        return sortedStateCensusJson;
    }

    public String getLargestStateByArea() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        Sort.sort(censusComparator, censusDaoList);
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        JsonWrite.writeJson("./src/test/resources/IndiaLargestStateByArea.json", censusDaoList);
        return sortedStateCensusJson;
    }



    public String getPopulatedDensityWiseSortedUsCensusData() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.usPopulation);
        Sort.sort(censusComparator, censusDaoList);
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        JsonWrite.writeJson("./src/test/resources/UsStateCensusDataDescending.json", censusDaoList);
        return sortedStateCensusJson;
    }


    public String getPopulatedAreaWiseSortedUsCensusData() throws CensusAnalyserException {
        if (censusDaoList == null || censusDaoList.size() == 0) {
            throw new CensusAnalyserException("No data", CensusAnalyserException.ExceptionType.NO_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.totalArea);
        Sort.sort(censusComparator, censusDaoList);
        String sortedStateCensusJson = new Gson().toJson(censusDaoList);
        JsonWrite.writeJson("./src/test/resources/UsStateCensusDataAreaWise.json", censusDaoList);
        return sortedStateCensusJson;
    }
}