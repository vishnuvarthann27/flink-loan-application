package models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LOAN_1 {

    @JsonProperty("invstr_loan_nbr")
    private String invstr_loan_nbr;

    @JsonProperty("srvcr_loan_nbr")
    private String srvcr_loan_nbr;

    @JsonProperty("upb_amt")
    private String upb_amt;

    @JsonProperty("original_prin_bal")
    private String original_prin_bal;

    @JsonProperty("ddlpi_dt")
    private String ddlpi_dt;

    @JsonProperty("first_payment_date")
    private String first_payment_date;

    @JsonProperty("loan_mtr_dt")
    private String loan_mtr_dt;

    @JsonProperty("invstr_cd")
    private String invstr_cd;

    @JsonProperty("issur_nbr")
    private String issur_nbr;

    @JsonCreator
    public LOAN_1() {
    }

    public String getInvstr_loan_nbr() {
        return invstr_loan_nbr;
    }

    public void setInvstr_loan_nbr(String invstr_loan_nbr) {
        this.invstr_loan_nbr = invstr_loan_nbr;
    }

    public String getSrvcr_loan_nbr() {
        return srvcr_loan_nbr;
    }

    public void setSrvcr_loan_nbr(String srvcr_loan_nbr) {
        this.srvcr_loan_nbr = srvcr_loan_nbr;
    }

    public String getUpb_amt() {
        return upb_amt;
    }

    public void setUpb_amt(String upb_amt) {
        this.upb_amt = upb_amt;
    }

    public String getOriginal_prin_bal() {
        return original_prin_bal;
    }

    public void setOriginal_prin_bal(String original_prin_bal) {
        this.original_prin_bal = original_prin_bal;
    }

    public String getDdlpi_dt() {
        return ddlpi_dt;
    }

    public void setDdlpi_dt(String ddlpi_dt) {
        this.ddlpi_dt = ddlpi_dt;
    }

    public String getFirst_payment_date() {
        return first_payment_date;
    }

    public void setFirst_payment_date(String first_payment_date) {
        this.first_payment_date = first_payment_date;
    }

    public String getLoan_mtr_dt() {
        return loan_mtr_dt;
    }

    public void setLoan_mtr_dt(String loan_mtr_dt) {
        this.loan_mtr_dt = loan_mtr_dt;
    }

    public String getInvstr_cd() {
        return invstr_cd;
    }

    public void setInvstr_cd(String invstr_cd) {
        this.invstr_cd = invstr_cd;
    }

    public String getIssur_nbr() {
        return issur_nbr;
    }

    public void setIssur_nbr(String issur_nbr) {
        this.issur_nbr = issur_nbr;
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public LOAN toLOANFormat(){
        LOAN loan = new LOAN();

        try {
            loan.setInvstr_loan_nbr(invstr_loan_nbr != null ? Integer.parseInt(invstr_loan_nbr) : null);
            loan.setSrvcr_loan_nbr(srvcr_loan_nbr != null ? Integer.parseInt(srvcr_loan_nbr) : null);
            loan.setUpb_amt(upb_amt != null ? Double.parseDouble(upb_amt) : 0.00);
            loan.setOriginal_prin_bal(original_prin_bal != null ? Double.parseDouble(original_prin_bal) : 0.00);
            loan.setDdlpi_dt(parseDate(ddlpi_dt));
            loan.setFirst_payment_date(parseDate(first_payment_date));
            loan.setLoan_mtr_dt(parseDate(loan_mtr_dt));
            loan.setInvstr_cd(invstr_cd);
            loan.setIssur_nbr(issur_nbr);
        } catch (NumberFormatException e) {
            System.err.println("Error converting number: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return  loan;
    }

    private Date parseDate(String dateStr) throws ParseException {
        return (dateStr != null && !dateStr.isEmpty()) ? DATE_FORMAT.parse(dateStr) : null;
    }
}
