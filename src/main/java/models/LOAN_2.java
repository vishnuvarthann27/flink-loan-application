package models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LOAN_2 {
    @JsonProperty("invstr_loan_nbr")
    private String invstr_loan_nbr;

    @JsonProperty("srvcr_loan_nbr")
    private String srvcr_loan_nbr;

    @JsonProperty("upb_amt")
    private String upb_amt;

    @JsonProperty("original_prin_bal")
    private String original_prin_bal;

    @JsonProperty("ddlpi_dt_date")
    private String ddlpi_dt_date;

    @JsonProperty("ddlpi_dt_month")
    private String ddlpi_dt_month;

    @JsonProperty("ddlpi_dt_year")
    private String ddlpi_dt_year;

    @JsonProperty("first_payment_date_date")
    private String first_payment_date_date;

    @JsonProperty("first_payment_date_month")
    private String first_payment_date_month;

    @JsonProperty("first_payment_date_year")
    private String first_payment_date_year;

    @JsonProperty("loan_mtr_dt_date")
    private String loan_mtr_dt_date;

    @JsonProperty("loan_mtr_dt_month")
    private String loan_mtr_dt_month;

    @JsonProperty("loan_mtr_dt_year")
    private String loan_mtr_dt_year;

    @JsonProperty("invstr_cd")
    private String invstr_cd;

    @JsonProperty("issur_nbr")
    private String issur_nbr;

    @JsonCreator
    public LOAN_2() {
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

    public String getDdlpi_dt_date() {
        return ddlpi_dt_date;
    }

    public void setDdlpi_dt_date(String ddlpi_dt_date) {
        this.ddlpi_dt_date = ddlpi_dt_date;
    }

    public String getDdlpi_dt_month() {
        return ddlpi_dt_month;
    }

    public void setDdlpi_dt_month(String ddlpi_dt_month) {
        this.ddlpi_dt_month = ddlpi_dt_month;
    }

    public String getDdlpi_dt_year() {
        return ddlpi_dt_year;
    }

    public void setDdlpi_dt_year(String ddlpi_dt_year) {
        this.ddlpi_dt_year = ddlpi_dt_year;
    }

    public String getFirst_payment_date_date() {
        return first_payment_date_date;
    }

    public void setFirst_payment_date_date(String first_payment_date_date) {
        this.first_payment_date_date = first_payment_date_date;
    }

    public String getFirst_payment_date() {
        return first_payment_date_month;
    }

    public void setFirst_payment_date(String first_payment_date) {
        this.first_payment_date_month = first_payment_date;
    }

    public String getFirst_payment_date_year() {
        return first_payment_date_year;
    }

    public void setFirst_payment_date_year(String first_payment_date_year) {
        this.first_payment_date_year = first_payment_date_year;
    }

    public String getLoan_mtr_dt_date() {
        return loan_mtr_dt_date;
    }

    public void setLoan_mtr_dt_date(String loan_mtr_dt_date) {
        this.loan_mtr_dt_date = loan_mtr_dt_date;
    }

    public String getLoan_mtr_dt_month() {
        return loan_mtr_dt_month;
    }

    public void setLoan_mtr_dt_month(String loan_mtr_dt_month) {
        this.loan_mtr_dt_month = loan_mtr_dt_month;
    }

    public String getLoan_mtr_dt_year() {
        return loan_mtr_dt_year;
    }

    public void setLoan_mtr_dt_year(String loan_mtr_dt_year) {
        this.loan_mtr_dt_year = loan_mtr_dt_year;
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

    public LOAN toLOANFormat() {
        LOAN loan = new LOAN();

        try {
            loan.setInvstr_loan_nbr(invstr_loan_nbr != null ? Integer.parseInt(invstr_loan_nbr) : null);
            loan.setSrvcr_loan_nbr(srvcr_loan_nbr != null ? Integer.parseInt(srvcr_loan_nbr) : null);
            loan.setUpb_amt(upb_amt != null ? Double.parseDouble(upb_amt) : 0.00);
            loan.setOriginal_prin_bal(original_prin_bal != null ? Double.parseDouble(original_prin_bal) : 0.00);
            loan.setDdlpi_dt(parseDate(ddlpi_dt_year, ddlpi_dt_month, ddlpi_dt_date));
            loan.setFirst_payment_date(parseDate(first_payment_date_year, first_payment_date_month, first_payment_date_date));
            loan.setLoan_mtr_dt(parseDate(loan_mtr_dt_year, loan_mtr_dt_month, loan_mtr_dt_date));
            loan.setInvstr_cd(invstr_cd);
            loan.setIssur_nbr(issur_nbr);
        } catch (NumberFormatException e) {
            System.err.println("Error converting number: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }

        return loan;
    }

    private Date parseDate(String year, String month, String day) throws ParseException  {
        if (year == null || month == null || day == null) {
            return null;
        }

        String dateString = year + "-" + month + "-" + day;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(dateString);
    }
}
