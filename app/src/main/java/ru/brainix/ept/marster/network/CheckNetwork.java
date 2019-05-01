package ru.brainix.ept.marster.network;

import java.net.InetAddress;


public class CheckNetwork{

    private final String LOG_TAG = "CheckNet ";


    //Чекаем соедение
    public boolean isInternet() {

        try {

            InetAddress ipAdr = InetAddress.getByName("google.com");
            //You can replace it with your name

            return !ipAdr.equals("");

        } catch (Exception e) {

            return false;
        }
    }


}


