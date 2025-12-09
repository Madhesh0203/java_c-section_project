package com.example.predictapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.DecimalFormat;

@Controller
public class PredictController {

  @GetMapping("/")
  public String home() {
    return "index"; // Loads index.html
  }

  @PostMapping("/predict")
  public String predict(
      @RequestParam int fetal_distress,
      @RequestParam int maternal_age,
      @RequestParam int gestational_age,
      @RequestParam int previous_c_section,
      @RequestParam int blood_pressure,
      @RequestParam double bmi,
      Model model) {

    StringBuilder conditions = new StringBuilder();
    String prediction = "NO C - SECTION";
    String instructions = "";

    // --- Logic (identical to your Java CMD version) ---
    if (fetal_distress == 1) {
      conditions.append("Fetal distress detected, which may require immediate medical intervention.\n");
    }
    if (maternal_age > 45) {
      conditions.append("Maternal age above 45 increases delivery risks, and a C-section may be necessary.\n");
    } else if (maternal_age < 14) {
      conditions
          .append("Maternal age below 14 is highly risky for pregnancy and requires immediate medical attention.\n");
    }
    if (gestational_age > 42) {
      conditions.append("Gestational age exceeding 42 weeks may lead to complications, requiring a C-section.\n");
    } else if (gestational_age < 22) {
      conditions.append("Gestational age below 22 weeks is critically low and may indicate severe complications.\n");
    }
    if (blood_pressure > 140) {
      conditions.append("High blood pressure can cause complications, making a C-section the safer option.\n");
    } else if (blood_pressure < 80) {
      conditions.append("Low blood pressure can cause complications, requiring close monitoring.\n");
    }
    if (bmi > 35) {
      conditions.append("High BMI may lead to delivery complications, increasing the likelihood of a C-section.\n");
    } else if (bmi < 18) {
      conditions.append("Low BMI may lead to delivery complications, requiring special care.\n");
    }

    if (conditions.length() > 0) {
      prediction = "C - SECTION";
      instructions = conditions.toString() +
          "Please consult your doctor for further evaluation and they will guide you through the process to ensure the best outcome for you and your baby😊";
    } else {
      double predicted_prob = Math.random();
      if (predicted_prob > 0.7) {
        prediction = "C - SECTION";
        instructions = "Based on the analysis, a C-section is the safest option for a healthy delivery. Your doctor will guide you through the process to ensure the best outcome for you and your baby😊";
      } else {
        prediction = "NO C - SECTION";
        instructions = "Follow prenatal care guidelines to ensure a healthy and natural delivery😊";
      }
    }

    // Add all data to the model (like Flask render_template)
    model.addAttribute("prediction", prediction);
    model.addAttribute("instructions", instructions);
    model.addAttribute("fetal_distress", fetal_distress);
    model.addAttribute("maternal_age", maternal_age);
    model.addAttribute("gestational_age", gestational_age);
    model.addAttribute("previous_c_section", previous_c_section);
    model.addAttribute("blood_pressure", blood_pressure);
    model.addAttribute("bmi", bmi);

    return "result"; // Loads result.html
  }
}
