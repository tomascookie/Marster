package ru.brainix.ept.marster.data.getting;

import java.net.InetAddress;


public class CheckNetwork{

    private final String LOG_TAG = "CheckNet ";

    //Запускаем поток
    public boolean isInternetAvailable() {

        CheckIntenret check = new CheckIntenret();

        Thread thread = new Thread(check);
        thread.start();

            try { thread.join(); }

            catch (InterruptedException e) { e.printStackTrace(); }


        return check.getState();


    }


     //Поток для проверки соединения
     private class CheckIntenret implements Runnable {

        private Boolean state;

        public Boolean getState() {
            return state;
        }



        public void run() {

            state = isInternet();



        }


    }

    //Чекаем соедение
    private synchronized boolean isInternet() {

        try {

            InetAddress ipAdr = InetAddress.getByName("google.com");
            //You can replace it with your name

            return !ipAdr.equals("");

        } catch (Exception e) {

            return false;
        }
    }


}


