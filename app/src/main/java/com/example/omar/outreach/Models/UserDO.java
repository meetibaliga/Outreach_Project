package com.example.omar.outreach.Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "outreach-mobilehub-787670546-User")

public class UserDO {
    private String _userId;
    private String _firstName;
    private Boolean _airFresh;
    private Boolean _asthmaDiagnosed;
    private String _averageIncome;
    private String _connectedToCommunity;
    private String _creationDate;
    private String _employed;
    private String _exhaustFans;
    private String _filterationSystem;
    private String _furnaceSystem;
    private String _helpless;
    private String _highestDegree;
    private String _homeBuiltYear;
    private String _homeType;
    private String _howHappy;
    private Boolean _kerosenUse;
    private String _lastName;
    private String _lifeSatisfaction;
    private String _martialStatus;
    private String _monthBirth;
    private String _numPets;
    private String _occupantsLived;
    private String _pollutionImpact;
    private String _race;
    private Boolean _radonMitigation;
    private String _relationToHousehold;
    private String _sex;
    private String _standardSatisfaction;
    private String _stories;
    private String _ventilationPracticeSummer;
    private String _ventilationPracticeWinter;
    private String _ventilationType;
    private String _ventilationWindow;
    private String _windowsOpen;
    private String _yearBirth;
    private String _homeArea;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "firstName")
    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(final String _firstName) {
        this._firstName = _firstName;
    }
    @DynamoDBAttribute(attributeName = "airFresh")
    public Boolean getAirFresh() {
        return _airFresh;
    }

    public void setAirFresh(final Boolean _airFresh) {
        this._airFresh = _airFresh;
    }
    @DynamoDBAttribute(attributeName = "asthmaDiagnosed")
    public Boolean getAsthmaDiagnosed() {
        return _asthmaDiagnosed;
    }

    public void setAsthmaDiagnosed(final Boolean _asthmaDiagnosed) {
        this._asthmaDiagnosed = _asthmaDiagnosed;
    }
    @DynamoDBAttribute(attributeName = "averageIncome")
    public String getAverageIncome() {
        return _averageIncome;
    }

    public void setAverageIncome(final String _averageIncome) {
        this._averageIncome = _averageIncome;
    }
    @DynamoDBAttribute(attributeName = "connectedToCommunity")
    public String getConnectedToCommunity() {
        return _connectedToCommunity;
    }

    public void setConnectedToCommunity(final String _connectedToCommunity) {
        this._connectedToCommunity = _connectedToCommunity;
    }
    @DynamoDBAttribute(attributeName = "creationDate")
    public String getCreationDate() {
        return _creationDate;
    }

    public void setCreationDate(final String _creationDate) {
        this._creationDate = _creationDate;
    }
    @DynamoDBAttribute(attributeName = "employed")
    public String getEmployed() {
        return _employed;
    }

    public void setEmployed(final String _employed) {
        this._employed = _employed;
    }
    @DynamoDBAttribute(attributeName = "exhaustFans")
    public String getExhaustFans() {
        return _exhaustFans;
    }

    public void setExhaustFans(final String _exhaustFans) {
        this._exhaustFans = _exhaustFans;
    }
    @DynamoDBAttribute(attributeName = "filterationSystem")
    public String getFilterationSystem() {
        return _filterationSystem;
    }

    public void setFilterationSystem(final String _filterationSystem) {
        this._filterationSystem = _filterationSystem;
    }
    @DynamoDBAttribute(attributeName = "furnaceSystem")
    public String getFurnaceSystem() {
        return _furnaceSystem;
    }

    public void setFurnaceSystem(final String _furnaceSystem) {
        this._furnaceSystem = _furnaceSystem;
    }
    @DynamoDBAttribute(attributeName = "helpless")
    public String getHelpless() {
        return _helpless;
    }

    public void setHelpless(final String _helpless) {
        this._helpless = _helpless;
    }
    @DynamoDBAttribute(attributeName = "highestDegree")
    public String getHighestDegree() {
        return _highestDegree;
    }

    public void setHighestDegree(final String _highestDegree) {
        this._highestDegree = _highestDegree;
    }
    @DynamoDBAttribute(attributeName = "homeBuiltYear")
    public String getHomeBuiltYear() {
        return _homeBuiltYear;
    }

    public void setHomeBuiltYear(final String _homeBuiltYear) {
        this._homeBuiltYear = _homeBuiltYear;
    }
    @DynamoDBAttribute(attributeName = "homeType")
    public String getHomeType() {
        return _homeType;
    }

    public void setHomeType(final String _homeType) {
        this._homeType = _homeType;
    }
    @DynamoDBAttribute(attributeName = "howHappy")
    public String getHowHappy() {
        return _howHappy;
    }

    public void setHowHappy(final String _howHappy) {
        this._howHappy = _howHappy;
    }
    @DynamoDBAttribute(attributeName = "kerosenUse")
    public Boolean getKerosenUse() {
        return _kerosenUse;
    }

    public void setKerosenUse(final Boolean _kerosenUse) {
        this._kerosenUse = _kerosenUse;
    }
    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return _lastName;
    }

    public void setLastName(final String _lastName) {
        this._lastName = _lastName;
    }
    @DynamoDBAttribute(attributeName = "lifeSatisfaction")
    public String getLifeSatisfaction() {
        return _lifeSatisfaction;
    }

    public void setLifeSatisfaction(final String _lifeSatisfaction) {
        this._lifeSatisfaction = _lifeSatisfaction;
    }
    @DynamoDBAttribute(attributeName = "martialStatus")
    public String getMartialStatus() {
        return _martialStatus;
    }

    public void setMartialStatus(final String _martialStatus) {
        this._martialStatus = _martialStatus;
    }
    @DynamoDBAttribute(attributeName = "monthBirth")
    public String getMonthBirth() {
        return _monthBirth;
    }

    public void setMonthBirth(final String _monthBirth) {
        this._monthBirth = _monthBirth;
    }
    @DynamoDBAttribute(attributeName = "numPets")
    public String getNumPets() {
        return _numPets;
    }

    public void setNumPets(final String _numPets) {
        this._numPets = _numPets;
    }
    @DynamoDBAttribute(attributeName = "occupantsLived")
    public String getOccupantsLived() {
        return _occupantsLived;
    }

    public void setOccupantsLived(final String _occupantsLived) {
        this._occupantsLived = _occupantsLived;
    }
    @DynamoDBAttribute(attributeName = "pollutionImpact")
    public String getPollutionImpact() {
        return _pollutionImpact;
    }

    public void setPollutionImpact(final String _pollutionImpact) {
        this._pollutionImpact = _pollutionImpact;
    }
    @DynamoDBAttribute(attributeName = "race")
    public String getRace() {
        return _race;
    }

    public void setRace(final String _race) {
        this._race = _race;
    }
    @DynamoDBAttribute(attributeName = "radonMitigation")
    public Boolean getRadonMitigation() {
        return _radonMitigation;
    }

    public void setRadonMitigation(final Boolean _radonMitigation) {
        this._radonMitigation = _radonMitigation;
    }
    @DynamoDBAttribute(attributeName = "relationToHousehold")
    public String getRelationToHousehold() {
        return _relationToHousehold;
    }

    public void setRelationToHousehold(final String _relationToHousehold) {
        this._relationToHousehold = _relationToHousehold;
    }
    @DynamoDBAttribute(attributeName = "sex")
    public String getSex() {
        return _sex;
    }

    public void setSex(final String _sex) {
        this._sex = _sex;
    }
    @DynamoDBAttribute(attributeName = "standardSatisfaction")
    public String getStandardSatisfaction() {
        return _standardSatisfaction;
    }

    public void setStandardSatisfaction(final String _standardSatisfaction) {
        this._standardSatisfaction = _standardSatisfaction;
    }
    @DynamoDBAttribute(attributeName = "stories")
    public String getStories() {
        return _stories;
    }

    public void setStories(final String _stories) {
        this._stories = _stories;
    }
    @DynamoDBAttribute(attributeName = "ventilationPracticeSummer")
    public String getVentilationPracticeSummer() {
        return _ventilationPracticeSummer;
    }

    public void setVentilationPracticeSummer(final String _ventilationPracticeSummer) {
        this._ventilationPracticeSummer = _ventilationPracticeSummer;
    }
    @DynamoDBAttribute(attributeName = "ventilationPracticeWinter")
    public String getVentilationPracticeWinter() {
        return _ventilationPracticeWinter;
    }

    public void setVentilationPracticeWinter(final String _ventilationPracticeWinter) {
        this._ventilationPracticeWinter = _ventilationPracticeWinter;
    }
    @DynamoDBAttribute(attributeName = "ventilationType")
    public String getVentilationType() {
        return _ventilationType;
    }

    public void setVentilationType(final String _ventilationType) {
        this._ventilationType = _ventilationType;
    }
    @DynamoDBAttribute(attributeName = "ventilationWindow")
    public String getVentilationWindow() {
        return _ventilationWindow;
    }

    public void setVentilationWindow(final String _ventilationWindow) {
        this._ventilationWindow = _ventilationWindow;
    }
    @DynamoDBAttribute(attributeName = "windowsOpen")
    public String getWindowsOpen() {
        return _windowsOpen;
    }

    public void setWindowsOpen(final String _windowsOpen) {
        this._windowsOpen = _windowsOpen;
    }

    @DynamoDBAttribute(attributeName = "yearBirth")
    public String getYearBirth() {
        return _yearBirth;
    }

    public void setYearBirth(final String _yearBirth) {
        this._yearBirth = _yearBirth;
    }

    @DynamoDBAttribute(attributeName = "homeArea")
    public String getHomeArea() {
        return _homeArea;
    }

    public void setHomeArea(final String _homeArea) {
        this._homeArea = _yearBirth;
    }

    // my methods

    public boolean hasAsthma(){
        return _asthmaDiagnosed;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "_userId='" + _userId + '\'' +
                ", _firstName='" + _firstName + '\'' +
                ", _airFresh=" + _airFresh +
                ", _asthmaDiagnosed=" + _asthmaDiagnosed +
                ", _averageIncome='" + _averageIncome + '\'' +
                ", _connectedToCommunity='" + _connectedToCommunity + '\'' +
                ", _creationDate='" + _creationDate + '\'' +
                ", _employed='" + _employed + '\'' +
                ", _exhaustFans='" + _exhaustFans + '\'' +
                ", _filterationSystem='" + _filterationSystem + '\'' +
                ", _furnaceSystem='" + _furnaceSystem + '\'' +
                ", _helpless='" + _helpless + '\'' +
                ", _highestDegree='" + _highestDegree + '\'' +
                ", _homeBuiltYear='" + _homeBuiltYear + '\'' +
                ", _homeType='" + _homeType + '\'' +
                ", _howHappy='" + _howHappy + '\'' +
                ", _kerosenUse=" + _kerosenUse +
                ", _lastName='" + _lastName + '\'' +
                ", _lifeSatisfaction='" + _lifeSatisfaction + '\'' +
                ", _martialStatus='" + _martialStatus + '\'' +
                ", _monthBirth='" + _monthBirth + '\'' +
                ", _numPets='" + _numPets + '\'' +
                ", _occupantsLived='" + _occupantsLived + '\'' +
                ", _pollutionImpact='" + _pollutionImpact + '\'' +
                ", _race='" + _race + '\'' +
                ", _radonMitigation=" + _radonMitigation +
                ", _relationToHousehold='" + _relationToHousehold + '\'' +
                ", _sex='" + _sex + '\'' +
                ", _standardSatisfaction='" + _standardSatisfaction + '\'' +
                ", _stories='" + _stories + '\'' +
                ", _ventilationPracticeSummer='" + _ventilationPracticeSummer + '\'' +
                ", _ventilationPracticeWinter='" + _ventilationPracticeWinter + '\'' +
                ", _ventilationType='" + _ventilationType + '\'' +
                ", _ventilationWindow='" + _ventilationWindow + '\'' +
                ", _windowsOpen='" + _windowsOpen + '\'' +
                ", _yearBirth='" + _yearBirth + '\'' +
                ", _homeArea='" + _homeArea + '\'' +
                '}';
    }
}
