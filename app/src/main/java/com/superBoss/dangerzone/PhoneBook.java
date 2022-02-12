package com.superBoss.dangerzone;

import java.util.HashMap;
import java.util.List;

public class PhoneBook {
    HashMap<String,String> rolodex;

    public PhoneBook( List<String> names, List<String> numbers){

        rolodex = new HashMap<>();

        for(int i = 0; i < numbers.size(); i++){
           rolodex.put(names.get(i), numbers.get(i));
        }

    }

    public HashMap<String, String> getRolodex(){
        return rolodex;
    }

    public String toString()
    {
        String res = "";
        for(String s: rolodex.keySet()){
            res+=(s+"|");
        }
        return res;
    }


}
