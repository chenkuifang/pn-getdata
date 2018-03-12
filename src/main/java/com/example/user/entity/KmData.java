package com.example.user.entity;

/**
 * 康美数据
 *
 * @author QuiFar
 * @version V1.0
 **/
public class KmData {

    private Integer id;
    private String number;
    private String name;
    private String spec;
    private String source;
    private String productionUnit;
    private String documentNumber;
    private String type;
    private String isPrescription;
    private String isMedical;
    private String isSpecial;
    private String agentType;
    private String explain;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProductionUnit() {
        return productionUnit;
    }

    public void setProductionUnit(String productionUnit) {
        this.productionUnit = productionUnit;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsPrescription() {
        return isPrescription;
    }

    public void setIsPrescription(String isPrescription) {
        this.isPrescription = isPrescription;
    }

    public String getIsMedical() {
        return isMedical;
    }

    public void setIsMedical(String isMedical) {
        this.isMedical = isMedical;
    }

    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "KmData{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", spec='" + spec + '\'' +
                ", source='" + source + '\'' +
                ", productionUnit='" + productionUnit + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", type='" + type + '\'' +
                ", isPrescription='" + isPrescription + '\'' +
                ", isMedical='" + isMedical + '\'' +
                ", isSpecial='" + isSpecial + '\'' +
                ", agentType='" + agentType + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}
