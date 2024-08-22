package com.organic.contractfarming.farmer;


public class Farmer {

    private String firstName;
    private String lastName;
    private String farmerCode;

    private String wardName;

    private String clusterName;
    private String gender;
    private String maritalStatus;
    private String dateOfBirth;
    private String phoneNumber;
    private String emailAddress;
    private String farmerAddress;
    private String certificationStatus;
    private String productName;
    private String idNumber;
    private String registrationDate;
    private String farmerImage;
    private String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDataLang() {
        return farmerCode;
    }



    public Farmer(String firstName, String lastName, String dataLang, String dataImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.farmerCode = dataLang;

    }
    public Farmer(){

    }

    public Farmer(String firstName, String lastName, String farmerCode, String wardName, String clusterName, String gender, String maritalStatus, String dateOfBirth, String phoneNumber, String farmerAddress, String certificationStatus, String productName, String idNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.farmerCode = farmerCode;
        this.wardName = wardName;
        this.clusterName = clusterName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.farmerAddress = farmerAddress;
        this.certificationStatus = certificationStatus;
        this.productName = productName;
        this.idNumber = idNumber;
        this.registrationDate = registrationDate;
        this.farmerImage = farmerImage;
        this.key = key;
    }

}
