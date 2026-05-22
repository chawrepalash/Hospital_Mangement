package com.hospital.hospital_management_system;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*") // Allows your frontend to talk to this API safely
public class PatientController {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/hospi_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "flash";

    // ==========================================
    // POST ROUTE: ADMIT PATIENT
    // ==========================================
    @PostMapping("/admit")
    public String admitPatient(@RequestBody PatientRequest request) {
        String sql = "INSERT INTO patients VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, request.getId());
            pst.setString(2, request.getName());
            pst.setString(3, request.getMobile());
            pst.setString(4, request.getDisease());
            pst.setString(5, request.getWard());
            pst.setDate(6, java.sql.Date.valueOf("2026-05-20"));

            pst.executeUpdate();
            return "Success: Patient Saved In Database via Backend Server!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Backend Error: " + e.getMessage();
        }
    }

    // ==========================================
    // GET ROUTE: DISPLAY ALL PATIENTS (FIXED MATCHING YOUR SCHEMA)
    // ==========================================
    @GetMapping("/all")
    public List<Map<String, Object>> getAllPatients() {
        List<Map<String, Object>> patientsList = new ArrayList<>();

        // Updated to match your exact database column names from the image
        String sql = "SELECT patient_id, patient_name, mobile_number, disease, ward, admit_date FROM patients";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> patient = new HashMap<>();
                // Match the exact columns from your query
                patient.put("id", rs.getInt("patient_id"));
                patient.put("name", rs.getString("patient_name"));
                patient.put("mobile", rs.getString("mobile_number"));
                patient.put("disease", rs.getString("disease"));
                patient.put("ward", rs.getString("ward"));
                patient.put("admitDate", rs.getDate("admit_date").toString());
                patientsList.add(patient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientsList;
    }
}

// ==========================================
// DATA MAPPING HELPER CLASS
// ==========================================
class PatientRequest {
    private int id;
    private String name;
    private String mobile;
    private String disease;
    private String ward;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }
}