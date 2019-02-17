package com.example.nadav.nutryapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.nadav.nutryapp.Models.NutritionRecord;
import com.example.nadav.nutryapp.Models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SPHelper {

    /* utils */

    Context context;

    /*  TABLES  */
    public static final String USERS_TABLE = "users";
    public static final String RECORDS_TABLE = "records";
    public static final String INFO_TABLE = "userInfo";
    public static final String ACTIVE_USER = "activeUser";


    /**
     * @info must get context activity
     */
    public SPHelper(Context context) {
        this.context = context;
    }

    /**
     * @return ArrayList<String> object with the data ass tagged with SP_TAG_NAME in Context </>
     */
    public ArrayList<String> SP_GET(String SP_TAG_NAME) {

        // init array list to return and SP object
        ArrayList<String> list = null;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);

        // get string rep & into arraylist
        String list_as_string = sp.getString(SP_TAG_NAME, "");
        String[] temp = list_as_string.split(",");

        // return arraylist
        list = new ArrayList<String>(Arrays.asList(temp));
        return list;

    }

    /**
     * @info put ArrayList in SP of Context using SP_TAG_NAME </>
     */
    public void SP_PUT(String SP_TAG_NAME, ArrayList<String> arraylist) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);

        // turn into string representation

        String str_array = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arraylist.size(); i++)
            sb.append(arraylist.get(i)).append(",");
        str_array = sb.toString();
        sp.edit().putString(SP_TAG_NAME, str_array).commit();

    }

    /*  ----------------------------- */
    /*   USERS SP FORMAT: <user-name, user-password>                  */
    /*  ----------------------------- */

    /*  get User objects of all Users in an ArrayList   */
    public ArrayList<User> getUsers() {

        ArrayList<User> ret = new ArrayList<User>();

        ArrayList<String> users = SP_GET(USERS_TABLE);
        users.remove(0);

        for (int i = 0; i < users.size() - 1; i += 2) {
            String name = users.get(i);
            String pass = users.get(i + 1);
            ret.add(new User(name, pass));
        }

        return ret;

    }

    /*  add user to SP database:    [username,password] */
    public void addUser(User user) {

        ArrayList<String> users = SP_GET(USERS_TABLE);

        users.add(user.getUserName());
        users.add(user.getUserPassword());

        SP_PUT(USERS_TABLE, users);

    }

    /*  returns true if user exist  */
    public boolean validateUser(User u) {

        ArrayList<String> users = SP_GET(USERS_TABLE);
        users.remove(0);

        for (int i = 0; i < users.size() - 1; i += 2) {
            String name = users.get(i);
            String pass = users.get(i + 1);
            if (u.getUserName().equals(name) && u.getUserPassword().equals(pass))
                return true;
        }

        return false;
    }

    public void setActiveUser(String userName) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);

        sp.edit().remove(ACTIVE_USER).commit();
        sp.edit().putString(ACTIVE_USER, userName).commit();

    }

    public String getActiveUser() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);

        if (!sp.getString(ACTIVE_USER, null).toString().isEmpty())
            return sp.getString(ACTIVE_USER, "");

        return null;

    }

    public boolean isUserExist(User user) {
        ArrayList<User> users = getUsers();
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getUserName().equals(user.getUserName()))
                return true;
        return false;
    }

    /*  -----------------------------   */
    /*  USER INFO   SP FORMAT: <user-name, age, weight, height>            */
    /*  -----------------------------   */

    /*  add if user info does not exist yet
    *   updates user info if already exist
    * */
    public void setUserInfo(User userToAdd) {

        ArrayList<User> allInfo = getUsersInfoTable();

        boolean userExist = false;
        for (int i=0;i<allInfo.size();i++) {
            if (allInfo.get(i).getUserName().equals(userToAdd.getUserName())) {
                allInfo.set(i,userToAdd);
                userExist = true;
                break;
            }
        }
        if (!userExist) {
            allInfo.add(userToAdd);
        }

        setNewUsersInfoTable(allInfo);
    }

    public void setNewUsersInfoTable (ArrayList<User> usersInfo) {

        ArrayList<String> usersInfoFormatted = new ArrayList<>();
        for (int i=0;i<usersInfo.size();i++) {
            usersInfoFormatted.add(usersInfo.get(i).getUserName());
            usersInfoFormatted.add(String.valueOf(usersInfo.get(i).getUserAge()));
            usersInfoFormatted.add(String.valueOf(usersInfo.get(i).getUserWeight()));
            usersInfoFormatted.add(String.valueOf(usersInfo.get(i).getUserHeight()));
            usersInfoFormatted.add(String.valueOf(usersInfo.get(i).getUserGender()));
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);
        sp.edit().remove(INFO_TABLE).commit();

        SP_PUT(INFO_TABLE,usersInfoFormatted);

    }

    public ArrayList<User> getUsersInfoTable() {

        ArrayList<String> spData = SP_GET(INFO_TABLE);
        //spData.remove(0);

        ArrayList<User> infoTable = new ArrayList<>();

        // get all users data into array
        for (int i = 0; i < spData.size() - 4; i += 5) {

            User u = new User(spData.get(i), "");
            u.setUserAge(Float.valueOf(spData.get(i + 1)));
            u.setUserWeight(Float.valueOf(spData.get(i + 2)));
            u.setUserHeight(Float.valueOf(spData.get(i + 3)));
            u.setUserGender(Integer.valueOf(spData.get(i + 4)));
            infoTable.add(u);
        }

        return infoTable;

    }


    /*  -----------------------------   */
    /*   NUTRITION RECORDS  SP FORMAT: <user-name, date, description, calories>       */
    /*  -----------------------------   */

    /*  add new record in SP for a nutrition record:    [username, date, description, calories] */
    public boolean addNutritionRecord(NutritionRecord record) {

        ArrayList<String> records = SP_GET(RECORDS_TABLE);

        records.add(record.getUserName());
        records.add(record.getDate());
        records.add(record.getDescription());
        records.add(record.getCalories());

        SP_PUT(RECORDS_TABLE, records);

        return true;
    }

    /*   get nutrition records for a user   */
    public ArrayList<NutritionRecord> getNutritionRecords(String userName) {

        ArrayList<NutritionRecord> userRecords = new ArrayList<>();
        ArrayList<String> allRecords = SP_GET(RECORDS_TABLE);
        allRecords.remove(0);


        for (int i = 0; i < allRecords.size() - 3; i += 4) {
            if (allRecords.get(i).equals(userName)) {
                NutritionRecord nr = new NutritionRecord(userName, allRecords.get(i + 2), allRecords.get(i + 3));
                nr.setDateSP(allRecords.get(i + 1));
                userRecords.add(nr);
            }
        }

        return userRecords;

    }

    /*   get nutrition records for a user @ a specific date  */
    public ArrayList<NutritionRecord> getNutritionRecords(String userName, String recDate) {

        ArrayList<NutritionRecord> userRecords = new ArrayList<>();
        ArrayList<String> allRecords = SP_GET(RECORDS_TABLE);
        allRecords.remove(0);

        for (int i = 0; i < allRecords.size() - 3; i += 4) {
            if (allRecords.get(i).equals(userName) && allRecords.get(i + 1).equals(recDate)) {
                NutritionRecord nr = new NutritionRecord(userName, allRecords.get(i + 2), allRecords.get(i + 3));
                nr.setDateSP(allRecords.get(i + 1));
                userRecords.add(nr);
            }
        }

        return userRecords;

    }

    public void removeUserNutritionRecordsOnDate(String userName, String date) {

        // GET ALL RECORDS FROM SP
        ArrayList<NutritionRecord> records = getNutritionRecords(userName);

        // REMOVE THOSE OF USER @ DATE
        for (int i = 0; i < records.size(); i++)
            if (records.get(i).getDate().equals(date))
                records.remove(i);

        // REMOVE PREVIOUS SP TABLE
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.context);
        sp.edit().remove(RECORDS_TABLE).commit();

        // ADD BACK ONE BY ONE TO SP
        for (int i = 0; i < records.size(); i++)
            addNutritionRecord(records.get(i));


    }


}