package com.example.nadav.nutryapp.Models;

public class NutritionRecord {

    String userName, date, description, calories;

    public NutritionRecord (String userName, String description, String calories) {

        this.description = description;
        this.calories = calories;
        this.userName = userName;

    }

    public void setDate(String year, String month, String day) {
        this.date = day+"."+(Integer.parseInt(month)+1)+"."+year;
    }

    // returns -1 for error, 0 for equal dates, 1 if this object dates is more recent, 2 if compare is bigger

    public int isRecentThan (NutritionRecord compare) {

        if (this.date.isEmpty() || compare.getDate().isEmpty())
            return -1;

        String[] dateComponents;

        dateComponents = this.date.split("\\.");
        if (dateComponents.length != 3)
            return -1;
        int thisDay = Integer.valueOf(dateComponents[0]);
        int thisMonth = Integer.valueOf(dateComponents[1]);
        int thisYear = Integer.valueOf(dateComponents[2]);

        dateComponents = compare.date.split("\\.");
        if (dateComponents.length != 3)
            return -1;
        int cDay = Integer.valueOf(dateComponents[0]);
        int cMonth = Integer.valueOf(dateComponents[1]);
        int cYear = Integer.valueOf(dateComponents[2]);

        // compare

        return -1;
    }

    public void setDateSP(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCalories() {
        return calories;
    }

}
